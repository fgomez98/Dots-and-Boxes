package com.company.Model;

public class Board {

    private final static int ARC = 1;
    private final static int NOARC = 0;
    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;
    private Integer[][] boxBoard;
    private Integer[][] boxHorizontal;
    private Integer[][] boxVertical;
    private int player1Score, player2Score;
    private int N;

    public Board(int n) {
        N = n;
        boxBoard = new Integer[N-1][N-1];
        boxHorizontal = new Integer[N-1][N];
        boxVertical = new Integer[N][N-1];
        player1Score = 0;
        player2Score = 0;
    }

    public boolean setHEdge(int x, int y, int player){
        if (boxHorizontal[y][x] == NOARC){
            boxHorizontal[y][x] = player;
            if(y < N && boxHorizontal[y+1][x] == ARC && boxVertical[y][x] == ARC && boxVertical[y][x+1] == ARC){
                if(player == 1){
                    player1Score++;
                }else{
                    player2Score++;
                }
                boxBoard[y][x] = player;
            }
            if(y > 0 && boxHorizontal[y-1][x] == ARC && boxVertical[y-1][x] == ARC && boxVertical[y-1][x+1] == ARC){
                if(player == 1){
                    player1Score++;
                }else{
                    player2Score++;
                }
                boxBoard[][] = player;
            }
            return true;
        }
        return false;
    }

    public boolean setVEdge(int x, int y, int player){
        if(boxVertical[y][x] == NOARC){
            boxVertical[y][x] = player;
            if( x < N && boxVertical[y][x+1] == ARC && boxHorizontal[y][x]== ARC && boxHorizontal[y-1][x] == ARC){
                if(player == 1){
                    player1Score++;
                }else{
                    player2Score++;
                }
                boxBoard[y][x] = player;
            }if(x > 0 && boxVertical[y][x-1] == ARC && boxHorizontal[y-1][x-1] == ARC && boxHorizontal[y][x-1] == ARC){
                if(player == 1){
                    player1Score++;
                }else{
                    player2Score++;
                }
                boxBoard[y][x-1] = player;
            return true;
        }
        return false;
    }

    public boolean fillBoards(int n) {
        return false;
    }
}
