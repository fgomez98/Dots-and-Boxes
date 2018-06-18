package com.company.Controller;

import com.company.Model.Arc;
import com.company.Model.Board;
import com.company.Model.MiniMax;
import com.company.Model.Player;

public class GameState {

    private Board board;
    private Player currentPlayer;
    private int difficulty;
    public boolean vsHuman;


    public GameState(int n, boolean vsHuman, int difficulty) {
        this.vsHuman = vsHuman;
        this.board = new Board(n, vsHuman);
        currentPlayer = board.getCurrentPlayer();
        this.difficulty = difficulty;
    }

    public void handelInput(int x, int y) {
        if (currentPlayer.isHuman()) {
            if (board.addArc(new Arc(currentPlayer, x,y))) {
                board.nextTurn();
                //imprimo el tablero
            }
        } else { //computadora
            board = MiniMax.bestMove(board, difficulty, currentPlayer);
            board.nextTurn();
            //imprimo el tablero
        }
    }

    //Para entrar aca, si o si tengo que verificar si tengo jugadas disponibles
    public boolean setPlayer(Arc arc, Player player){
        int points = player.getScore();
        while(arc.isHorizontal()? board.setHEdge(arc.getX(),arc.getY(), player):board.setVEdge(arc.getX(),arc.getY(), player)){
                if(points != player.getScore()){
                    return true;
                }
            }
        return false;
    }


/*
    public int finished(){
        if (board.boardComplete())
            return board.getWinner();
        return 2;//porque no es ni uno ni -1, o sea, no termino
    }
    */
}
