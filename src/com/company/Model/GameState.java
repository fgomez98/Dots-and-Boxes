package com.company.Model;

import com.company.Model.Board;

public class GameState {

    private Board board;
    private int mode;

    public GameState(int n, int playMode) {
        this.board = new Board(n);
        if(playMode == 1){
            mode = playMode;
        }
    }


    //Para entrar aca, si o si tengo que verificar si tengo jugadas disponibles
    public boolean setPlayer(Arc arc, int player){
        int points = board.getPlayerScore(player);
        while(arc.isHorizontal()? board.setHEdge(arc.getX(),arc.getY(), player):board.setVEdge(arc.getX(),arc.getY(), player)){
                if(points != board.getPlayerScore(player)){
                    return true;
                }
            }
        return false;
    }

    public Board getBoard(){
        return board;
    }

    public int finished(){
        if (board.boardComplete())
            return board.getWinner();
        return 2;//porque no es ni uno ni -1, o sea, no termino
    }






}
