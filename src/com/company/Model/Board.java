package com.company.Model;

public class Board {
    private Integer[][] boxBoard;
    private Integer[][] boxHorizontal;
    private Integer[][] boxVertical;
    private int player1Score, player2Score;

    public Board(int N) {
        boxBoard = new Integer[N-1][N-1];
        boxHorizontal = new Integer[N][N-1];
        boxVertical = new Integer[N-1][N];
        player1Score = 0;
        player2Score = 0;
    }

    public boolean setVEdge(int x, int y, int player){
        return false;
    }

    public boolean setHEdge(int x, int y, int player){
        return false;
    }
}
