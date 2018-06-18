package com.company.Model;

import com.company.Model.Board;

public class GameState{

    private Board board;
    Player currentPlayer;


    public GameState(int n, boolean vsHuman) {
        this.board = new Board(n, vsHuman);
    }

    public void addArc(int x, int y) {
        board.addArc(new Arc(currentPlayer, x,y));
    }

    public void handelInput(int x, int y) {

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
