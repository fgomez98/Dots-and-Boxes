package com.company.Model;

import java.util.*;

public class Board {
    private final static int ARC = 1;
    private final static int NOARC = 0;
    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = -1;
    private Player[][] boxBoard;
    private Player[][] boxHorizontal;
    private Player[][] boxVertical;
    private Player player1, player2;
    private int N;
    private int turn;

    public Board(int n) {
        turn = 0;
        N = n;
        boxBoard = new Player[N-1][N-1];
        boxHorizontal = new Player[N-1][N];
        boxVertical = new Player[N][N-1];
        initBoards(boxBoard, N-1, N-1);
        initBoards(boxHorizontal, N-1, N);
        initBoards(boxVertical, N, N-1);
    }

    public Board(int n, boolean vsHuman) {
        this(n);
        player1 = new Player(0);
        if (vsHuman) {
            player2 = new Player(0);
        } else {
            player2 = new Opponent(0);
        }
    }

    public Board(int n, Player player1, Player player2) {
        this(n);
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean setHEdge(int x, int y, Player player){
        if (boxHorizontal[x][y] == null){
            boxHorizontal[x][y] = player;
            if(y < (N-1) && boxHorizontal[x][y+1] != null && boxVertical[y][x] != null && boxVertical[x+1][y] != null){
                player.incScore(1);
                boxBoard[x][y] = player;
            }
            if(y > 0 && x < (N-1)&& boxHorizontal[x][y-1] != null && boxVertical[x][y-1] != null && boxVertical[x+1][y-1] != null){
               player.incScore(1);
                boxBoard[x][y-1] = player;
            }
            return true;

        }
        return false;
    }

    public boolean setVEdge(int x, int y, Player player) {
        if (boxVertical[x][y] == null) {
            boxVertical[x][y] = player;
            if (x < (N-1) && (boxVertical[x+1][y] != null && boxHorizontal[x][y] != null && boxHorizontal[x][y+1] != null)) {
                player.incScore(1);
                boxBoard[x][y] = player;
            }
            if (x > 0 && (boxVertical[x-1][y] != null && boxHorizontal[x- 1][y + 1] != null && boxHorizontal[x-1][y] != null)) {
                player.incScore(1);
                boxBoard[x-1][y] = player;

            }
            return true;
        }
        return false;
    }

    private void initBoards(Player[][] board, int fils, int cols) {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = null;
            }
        }
    }

    private void fillBoard(Player[][] dest, Player[][] source, int fils, int cols) {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    private void fillMoves(List<Arc> moves, Player[][] board, int fils, int cols, boolean horizontal) {
        for (int i = 0; i < fils ; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == null) {
                    moves.add(new Arc(getCurrentPlayer(), i, j, horizontal));
                }
            }
        }
    }

    public int getPlayerScore(int player){
        return (player == 1)?player1.getScore():player2.getScore();
    }

    public List<Arc> getPosibleMoves() {
        List<Arc> moves = new LinkedList<>();
        fillMoves(moves, boxHorizontal, N-1, N, true);
        fillMoves(moves, boxVertical, N, N-1, false);
        return moves;
    }

    public int getN() {
        return N;
    }

    public boolean boardComplete() {
        return (player1.getScore()+ player2.getScore()) == Math.pow(N-1, 2);
    }

    public Player getWinner() {
        if (player1.getScore() > player2.getScore()) {
            return player1;
        } else if (player2.getScore() > player1.getScore()) {
            return player2;
        } else {
            return null;
        }
    }

    public Board clone() {
        Board aux = new Board(N, player1.clone(), player2.clone());
        fillBoard(aux.boxHorizontal, this.boxHorizontal, N-1,N);
        fillBoard(aux.boxVertical, this.boxVertical,N,N-1);
        fillBoard(aux.boxBoard, this.boxBoard, N-1, N-1);
        aux.turn = this.turn;
        return aux;
    }

    public boolean addArc(Arc arc) {
        if(arc.isHorizontal()){
            return setHEdge(arc.getX(),arc.getY(), arc.getPlayer());
        }else{
            return setVEdge(arc.getX(),arc.getY(),arc.getPlayer());
        }
    }

    public boolean removeArc(Arc arc) {
        int x = arc.getX();
        int y = arc.getY();
        if(arc.isHorizontal()){
            if(boxHorizontal[x][y] == null){
                return false;
            } else {
                if(y < (N-1) && boxHorizontal[x][y+1] != null && boxVertical[y][x] != null && boxVertical[x+1][y] != null){
                    boxBoard[x][y].incScore(-1);
                    boxBoard[x][y] = null;
                }
                if(y > 0 && x > 0 && boxHorizontal[x][y-1] != null && boxVertical[x][y-1] != null && boxVertical[x+1][y-1] != null){
                    boxBoard[x][y-1].incScore(-1);
                    boxBoard[x][y-1] = null;
                }
                boxHorizontal[x][y] = null;
                return true;
            }
        } else {
            if (boxVertical[x][y] == null){
                return false;
            } else{
                if(x < (N-1) && (boxVertical[x+1][y] != null && boxHorizontal[x][y] != null && boxHorizontal[x][y+1] != null)){
                    boxBoard[x][y].incScore(-1);
                    boxBoard[x][y] = null;
                }
                if (x > 0 && (boxVertical[x-1][y] != null && boxHorizontal[x- 1][y + 1] != null && boxHorizontal[x-1][y] != null)){
                    boxBoard[x-1][y].incScore(-1);
                    boxBoard[x-1][y] = null;
                }
                boxVertical[x][y] = null;
                return true;
            }
        }
    }

    public int scoresCheck() {
        return player2.getScore() + player1.getScore();
    }

    public void nextTurn() {
        turn = (turn+1)%2;
    }

    public Player getCurrentPlayer() {
        if (turn == 0) {
            return player1;
        } else {
            return player2;
        }
    }

    public Player getNextPlayer() {
        if (turn == 0) {
            return player2;
        } else {
            return player1;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (N != board.N) return false;
        if (turn != board.turn) return false;
        if (!Arrays.deepEquals(boxBoard, board.boxBoard)) return false;
        if (!Arrays.deepEquals(boxHorizontal, board.boxHorizontal)) return false;
        if (!Arrays.deepEquals(boxVertical, board.boxVertical)) return false;
        if (player1 != null ? !player1.equals(board.player1) : board.player1 != null) return false;
        return player2 != null ? player2.equals(board.player2) : board.player2 == null;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(boxBoard);
        result = 31 * result + Arrays.deepHashCode(boxHorizontal);
        result = 31 * result + Arrays.deepHashCode(boxVertical);
        result = 31 * result + (player1 != null ? player1.hashCode() : 0);
        result = 31 * result + (player2 != null ? player2.hashCode() : 0);
        result = 31 * result + N;
        result = 31 * result + turn;
        return result;
    }

    /*

        o-o-o-o-o
        | | | | |
        o-o-o-o-o
        | | | | |
        o-o-o-o-o

         */
    public void printBoard() {
        for (int i = 0; i < N; i++) {
            //imprimo horizontal
            for (int j = 0; j < N; j++) {
                System.out.print("o"); // los o son puntos en el tablero
                if ((i<(N-1)) && boxHorizontal[i][j] != null) {
                    System.out.print("-");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            //imprimo vertical
            for (int j = 0; j < N-1 ; j++) {
                if (boxVertical[i][j] != null) {
                    System.out.print("| ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
