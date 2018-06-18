package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameState {
    private Board board;
    private MiniMax miniMax;
    private int scores;

    public GameState(int n, boolean vsHuman, int depthOrTime, boolean depth, boolean pruning) {
        this.board = new Board(n, vsHuman);
        scores = board.scoresCheck();
        if (pruning) {
            if (depth) {
                miniMax = new MiniMaxDP(depthOrTime);
            } else {
                miniMax = new MiniMaxTP(depthOrTime);
            }
        } else {
            if (depth) {
                miniMax = new MiniMaxD(depthOrTime);
            } else {
                miniMax = new MiniMaxT(depthOrTime);
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
            board = miniMax.bestMove(board, board.getCurrentPlayer(),board.getNextPlayer());
            scores = board.scoresCheck();
            board.nextTurn();
            //imprimo el tablero
        }
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

    public void printTerminal() {
        board.printBoard();
    }


}
