package com.company.Model;

public class Board {

    private final static int ARC = 1;
    private final static int NOARC = 2;
    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;
    private Integer[][] boxBoard;
    private Integer[][] boxHorizontal;
    private Integer[][] boxVertical;
    private int player1Score, player2Score;
    private int N;

    public Board(int N) {
        boxBoard = new Integer[N-1][N-1];
        boxHorizontal = new Integer[N][N-1];
        boxVertical = new Integer[N-1][N];
        player1Score = 0;
        player2Score = 0;


    public boolean setVEdge(int x, int y, int player){
        return false;
    }

    public boolean setHEdge(int x, int y, int player){
        return false;
    }

    public boolean fillBoards(int n) {

    }
}
