package com.company.Model;

import java.util.HashSet;
import java.util.Set;
public class Board {
    private final static int ARC = 1;
    private final static int NOARC = 0;
    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;
    private int[][] boxBoard;
    private int[][] boxHorizontal;
    private int[][] boxVertical;
    private int player1Score, player2Score;
    private int N;

    public Board(int n) {
        N = n;
        boxBoard = new int[N-1][N-1];
        boxHorizontal = new int[N-1][N];
        boxVertical = new int[N][N-1];
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
                boxBoard[y-1][x] = player;
            }
            return true;

        }
        return false;
    }

    public boolean setVEdge(int x, int y, int player) {
        if (boxVertical[y][x] == NOARC) {
            boxVertical[y][x] = player;
            if (x < N && boxVertical[y][x + 1] == ARC && boxHorizontal[y][x] == ARC && boxHorizontal[y - 1][x] == ARC) {
                if (player == 1) {
                    player1Score++;
                } else {
                    player2Score++;
                }
                boxBoard[y][x] = player;
            }
            if (x > 0 && boxVertical[y][x - 1] == ARC && boxHorizontal[y - 1][x - 1] == ARC && boxHorizontal[y][x - 1] == ARC) {
                if (player == 1) {
                    player1Score++;
                } else {
                    player2Score++;
                }
                boxBoard[y][x - 1] = player;

            }
            return true;
        }
        return false;
    }

    private void initBoards(int[][] board, int fils, int cols) {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = 0;
            }
        }
    }

    private void fillBoard(int[][] dest, int[][] source, int fils, int cols) {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    private void fillMoves(Set<Arc> moves, int[][] board, int fils, int cols) {
        for (int i = 0; i < fils ; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == NOARC) {
                    moves.add(new Arc(0, i, j));
                }
            }
        }
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public Set<Arc> getPosibleMoves() {
        Set<Arc> moves = new HashSet<Arc>();
        fillMoves(moves, boxHorizontal, N-1, N);
        fillMoves(moves, boxVertical, N, N-1);
        return moves;
    }

    public int getN() {
        return N;
    }

    public boolean isWinner() {
        return (player1Score + player2Score) == Math.pow(N-1, 2);
    }

    public int getWinner() {
        if (player1Score > player2Score) {
            return PLAYER1;
        } else if (player2Score > player1Score) {
            return PLAYER2;
        } else {
            return 0;
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
