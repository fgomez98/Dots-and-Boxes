package com.company.Model;

import java.util.HashSet;
import java.util.Set;

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
        this.N = N;
        initBoards(boxBoard, N-1, N-1);
        initBoards(boxHorizontal, N, N-1);
        initBoards(boxVertical, N-1, N);
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

    public void initBoards(Colour[][] board, int fils, int cols) {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = Colour.ALBINO;
            }
        }
    }

    public void fillBoard(Colour[][] dest, Colour[][] source, int fils, int cols) {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public Set<Arc> getPosibleMove() {
        Set<Arc> moves = new HashSet<Arc>();
        //arcos horizontales
        for (int i = 0; i < N-1 ; i++) {

        }
    }

    public int getN() {
        return N;
    }

    public boolean isWinner() {
        return (player1Score + player2Score) == Math.pow(N-1, 2);
    }

    public Colour getWinner() {
        if (player1Score > player2Score) {
            return PLAYER1;
        } else if (player2Score > player1Score) {
            return PLAYER2;
        } else {
            return Colour.ALBINO;
        }
    }

    protected Board clone() throws CloneNotSupportedException {
        Board aux = new Board(N);
        fillBoard(aux.boxHorizontal, this.boxHorizontal,N,N-1);
        fillBoard(aux.boxVertical, this.boxVertical,N-1,N);
        fillBoard(aux.boxBoard, this.boxBoard, N-1, N-1);
        aux.player1Score = this.player1Score;
        aux.player2Score = this.player2Score;
        return aux;
    }
}
