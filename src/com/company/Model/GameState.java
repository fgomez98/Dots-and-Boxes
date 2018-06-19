package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class GameState implements Serializable{
    private Board board;
    private MiniMax miniMax;
    private int scores;
    private boolean timeMode;
    private LinkedList<Arc> moves;

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

    public GameState(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        loadObject(ois);
    }

    public void handleInput(int x, int y, boolean horizontal) { // le retorna al View el tablero que tiene que imprimir en pantalla
        if (board.getCurrentPlayer().isHuman()) {
            Arc arc = new Arc(board.getCurrentPlayer(), x,y, horizontal);
            if (board.addArc(arc)) { // los arcos pueden ser horizontales o veritcales
                moves.addLast(arc);
                if (scores == board.scoresCheck()) { // si es distinto el humano completo un casillero, gana un turno
                    board.nextTurn();
                    if (!getCurrentPlayer().isHuman()) {
                        handleInput(0,0, true);
                    }
                }
                scores = board.scoresCheck();
            }
        } else { //computadora
            Tree nextMove = miniMax.bestMove2(board, board.getCurrentPlayer(), board.getNextPlayer());
            board = nextMove.getBoard();
            moves.addAll(nextMove.getArcs()); //modificar orden de agregado
            scores = board.scoresCheck();
            board.nextTurn();
        }
    }

    public void undo() {
        if (moves.size() == 0) {
            return;
        }
        Arc arc = moves.removeLast();
        System.out.println(arc);
        board.removeArc(arc);
        Player curPlayer = board.getCurrentPlayer();
        if (arc.getPlayer().isHuman()) {
            if (curPlayer.equals(arc.getPlayer())) {
                scores = board.scoresCheck();
            } else {
                board.nextTurn();
                scores = board.scoresCheck();
            }
        } else {
            while (moves.size() != 0 && !(arc = moves.removeLast()).getPlayer().isHuman()){
                System.out.println(arc);
                board.removeArc(arc);
            }
            moves.addLast(arc);
            board.addArc(arc);
            board.nextTurn();
            scores = board.scoresCheck();
        }
    }

    public int checkScores() {
        return board.scoresCheck();
    }

    public boolean isWinner() {
        return board.boardComplete();
    }

    public Player getWinner() {
        return board.getWinner();
    }

    public int getPlayerId() {
        return board.getCurrentPlayer().getId();
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

    public Player getCurrentPlayer(){return board.getCurrentPlayer();}

    public Player getPlayer1(){return board.getPlayer1();}

    public Player getPlayer2(){return board.getPlayer2();}

    public void saveObject(ObjectOutputStream out) throws IOException {
        out.writeObject(board);
        out.writeObject(miniMax);
        out.writeObject(scores);
        out.writeObject(timeMode);
        out.writeObject(moves);
        out.close();
    }

    public void loadObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        board = (Board) ois.readObject();
        miniMax = (MiniMax) ois.readObject();
        scores = (int) ois.readObject();
        timeMode = (boolean) ois.readObject();
        moves = (LinkedList<Arc>) ois.readObject();
        ois.close();
    }
}
