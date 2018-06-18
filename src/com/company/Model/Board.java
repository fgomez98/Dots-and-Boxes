package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Board implements Serializable {
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
        /*
        boxBoard = new Player[N-1][N-1];
        boxHorizontal = new Player[N-1][N];
        boxVertical = new Player[N][N-1];
        initBoards(boxBoard, N-1, N-1);
        initBoards(boxHorizontal, N-1, N);
        initBoards(boxVertical, N, N-1);
        */
        boxBoard = new Player[N-1][N-1];
        boxHorizontal = new Player[N][N-1];
        boxVertical = new Player[N-1][N];
        initBoards(boxBoard, N-1, N-1);
        initBoards(boxHorizontal, N, N-1);
        initBoards(boxVertical, N-1, N);
    }

    public Board(int n, boolean vsHuman) {
        this(n);
        player1 = new Player(0,1);
        if (vsHuman) {
            player2 = new Player(0,-1);
        } else {
            player2 = new Opponent(0,-1);
        }
    }

    public Board(int n, Player player1, Player player2) {
        this(n);
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player[][] getBoxBoard() {
        return boxBoard;
    }

    public Player[][] getBoxHorizontal() {
        return boxHorizontal;
    }

    public Player[][] getBoxVertical() {
        return boxVertical;
    }

    /*
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
    */
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

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private void fillMoves(List<Arc> moves, Player[][] board, int fils, int cols, boolean horizontal) {
        for (int i = 0; i < fils ; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == null) {
                    moves.add(new Arc(getCurrentPlayer(), j, i, horizontal));
                }
            }
        }
    }

    public int getPlayerScore(int player){
        return (player == 1)?player1.getScore():player2.getScore();
    }

    public List<Arc> getPosibleMoves() {
        List<Arc> moves = new LinkedList<>();
        fillMoves(moves, boxHorizontal, N, N-1, true);
        fillMoves(moves, boxVertical, N-1, N, false);
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
        fillBoard(aux.boxHorizontal, this.boxHorizontal, N,N-1);
        fillBoard(aux.boxVertical, this.boxVertical,N-1,N);
        fillBoard(aux.boxBoard, this.boxBoard, N-1, N-1);
        aux.turn = this.turn;
        return aux;
    }
/*
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
*/
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
/*
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
*/

    public boolean setHEdge(int x, int y, Player player){
        if (boxHorizontal[y][x] == null){
            boxHorizontal[y][x] = player;
            if(y < N-1){
                if(boxHorizontal[y+1][x] != null && boxVertical[y][x] != null && boxVertical[y][x+1] != null){
                    player.incScore(1);
                    boxBoard[y][x] = player;
                }
            }
            if(y >= 1){
                if(x < N-1 && boxHorizontal[y-1][x] != null && boxVertical[y-1][x] != null && boxVertical[y-1][x+1] != null){
                    player.incScore(1);
                    boxBoard[y-1][x] = player;
                }
            }
            return true;

        }
        return false;
    }

    public boolean setVEdge(int x, int y, Player player) {
        if (boxVertical[y][x] == null) {
            boxVertical[y][x] = player;
            if (x < N-1 && y < N-1 && boxVertical[y][x + 1] != null && boxHorizontal[y][x] != null && boxHorizontal[y + 1][x] != null) {
                player.incScore(1);
                boxBoard[y][x] = player;
            }
            if (x > 0 && y < N-1 && boxVertical[y][x - 1] != null && boxHorizontal[y + 1][x - 1] != null && boxHorizontal[y][x - 1] != null) {
                player.incScore(1);
                boxBoard[y][x - 1] = player;

            }
            return true;
        }
        return false;
    }

    public boolean addArc(Arc arc) {
        if (arc.isHorizontal()) {
            return setHEdge(arc.getX(),arc.getY(), arc.getPlayer());
        } else {
            return setVEdge(arc.getX(), arc.getY(), arc.getPlayer());
        }
    }

    public boolean removeArc(Arc arc) {
        int x = arc.getX();
        int y = arc.getY();
        if(arc.isHorizontal()){
            if(boxHorizontal[y][x] == null){
                return false;
            } else {
                if(y < N-1 && boxHorizontal[y+1][x] != null && boxVertical[y][x] != null && boxVertical[y][x+1] != null){
                    boxBoard[y][x].incScore(-1);
                    boxBoard[y][x] = null;
                }
                if(y >= 1 && boxHorizontal[y-1][x] != null && boxVertical[y-1][x] != null && boxVertical[y-1][x+1] != null){
                    boxBoard[y-1][x].incScore(-1);
                    boxBoard[y-1][x] = null;
                }
                boxHorizontal[y][x] = null;
                return true;
            }
        } else {//if(!arc.isHorizontal() && y < N-1){
            if (boxVertical[y][x] == null){
                return false;
            } else{
                if(x < N-1 && (boxVertical[y][x+1] != null && boxHorizontal[y][x] != null && boxHorizontal[y+1][x] != null)){
                    boxBoard[y][x].incScore(-1);
                    boxBoard[y][x] = null;
                }
                if (x >= 1 && (boxVertical[y][x-1] != null && boxHorizontal[y + 1][x - 1] != null && boxHorizontal[y][x-1] != null)){
                    boxBoard[y][x-1].incScore(-1);
                    boxBoard[y][x-1] = null;
                }
                boxVertical[y][x] = null;
                return true;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < N; i++) {
            //imprimo horizontal
            for (int j = 0; j < N; j++) {
                System.out.print("o"); // los o son puntos en el tablero
                if (j < N-1 && boxHorizontal[i][j] != null) {//horizontal[n][n-1]
                    System.out.print("-");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            //imprimo vertical
            for (int j = 0; j <= N-1 ; j++) {
                if (i < N-1 && boxVertical[i][j] != null) {//vertical[n-1][n]
                    System.out.print("| ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public int checkBoxesOf(int n){
        if (n > 4 || n < 0){
            return -1;
        }
        int counter = 0;
        for(int i = 0;i<N-1;i++){
            int linesPerBox = 0;
            for (int j=0;j<N-1;j++){
                linesPerBox += (boxHorizontal[i][j] != null)?1:0;
                linesPerBox += (boxHorizontal[i+1][j] != null)?1:0;
                linesPerBox += (boxVertical[i][j] != null)?1:0;
                linesPerBox += (boxVertical[i][j+1] != null)?1:0;
                if(linesPerBox == n){
                    counter++;
                }
            }
        }
        return counter;
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(boxBoard);
        out.writeObject(boxVertical);
        out.writeObject(boxHorizontal);
        out.writeObject(player1);
        out.writeObject(player2);
        out.writeObject(N);
        out.writeObject(turn);
    }

    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        boxBoard = (Player[][]) ois.readObject();
        boxVertical = (Player[][]) ois.readObject();
        boxHorizontal = (Player[][]) ois.readObject();
        player1 = (Player) ois.readObject();
        player2 = (Player) ois.readObject();
        N = (int) ois.readObject();
        turn = (int) ois.readObject();
    }
}
