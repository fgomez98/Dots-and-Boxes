package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameState {
    private Board board;
    private MiniMax miniMax;
    private int scores;
    private boolean timeMode;
    private LinkedList<Set<Arc>> moves;

    public GameState(int n, int mode, int depthOrTime, boolean time, boolean pruning) {
        moves = new LinkedList<>();
        timeMode = time;
        this.board = new Board(n, mode);
        scores = board.scoresCheck();
        if (pruning) {
            if (time) {
                miniMax = new MiniMaxTP(depthOrTime);
            } else {
                miniMax = new MiniMaxDP(depthOrTime);
            }
        } else {
            if (time) {
                miniMax = new MiniMaxT(depthOrTime);
            } else {
                miniMax = new MiniMaxD(depthOrTime);
            }
        }
    }

    public void handelInput(int x, int y, boolean horizontal) { // le retorna al View el tablero que tiene que imprimir en pantalla
        if (board.getCurrentPlayer().isHuman()) {
            System.out.println("humano");
            if (board.addArc(new Arc(board.getCurrentPlayer(), x,y, horizontal))) { // los arcos pueden ser horizontales o veritcales
                if (scores == board.scoresCheck()) { // si es distinto el humano completo un casillero, gana un turno
                    board.nextTurn();
                }
                //imprimo el tablero
            }
        } else { //computadora
            System.out.println("compu");
            Tree nextMove = miniMax.bestMove2(board, board.getCurrentPlayer(),board.getNextPlayer());
            board = nextMove.getBoard();
            moves.addLast(nextMove.getArcs());
            scores = board.scoresCheck();
            board.nextTurn();
            //imprimo el tablero
        }
    }

    public void undo() {
        if (moves.size() == 0) {
            return;
        }
        for (Arc arc : moves.removeLast()) {
            board.removeArc(arc);
        }
        scores = board.scoresCheck();
    }

    public boolean isWinner() {
        return board.boardComplete();
    }

    public Player getWinner() {
        return board.getWinner();
    }

    public void saveBoard(ObjectOutputStream out) throws IOException {
        out.writeObject(board);
        out.close();
    }

    public void loadBoard(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        board = (Board) ois.readObject();
        ois.close();
    }

    public int getPlayer1Score() {
        return board.getPlayer1().getScore();
    }

    public int getPlayer2Score() {
        return board.getPlayer2().getScore();
    }

    public int size() {
        return board.getN();
    }

    public boolean isTimeMode() {
        return timeMode;
    }

    public void printTerminal() {
        board.printBoard();
    }

    public Player[][] getVBoard(){
        return board.getBoxVertical();
    }

    public Player[][] getHBoard(){
        return board.getBoxHorizontal();
    }

    public Player[][] getBBoard(){
        return board.getBoxBoard();
    }


}
