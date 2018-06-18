package com.company.Model;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main2 {

    static Set<Board> children = new HashSet<>();

    public static void main(String[] args) {
        int N = 4;
        GameState game = new GameState(N, 1, 4, false, true);
        int i = 50;
        Random rand = new Random();
        game.printTerminal();
        while ((i--) > 0) {
            int x = rand.nextInt(N-1);
            int y = rand.nextInt(N-1);
            boolean horizontal = true;
            if (rand.nextInt(2) != 0) {
                horizontal = false;
            }
            if (horizontal) {
                if (x < N-1) {
                    System.out.println("horizontal: " + horizontal + " x: " + x + "y: " +y);
                    game.handelInput(x, y, horizontal);
                    game.printTerminal();
                }
            } else {
                if (y < N-1) {
                    System.out.println("horizontal: " + horizontal + " x: " + x + "y: " +y);
                    game.handelInput(x, y, horizontal);
                    game.printTerminal();
                }
            }
        }
        System.out.println("Player 1: " + game.getPlayer1Score());
        System.out.println("Player 2: " + game.getPlayer2Score());
        /*
        Board board = new Board(5, new Player(0), new Player(0));
        board.addArc(new Arc(board.getCurrentPlayer(), 0, 1, true));
        board.addArc(new Arc(board.getCurrentPlayer(), 1, 1, true));
        board.addArc(new Arc(board.getCurrentPlayer(), 1, 0, false));
        board.printBoard();
        System.out.println(".......................");
        putSons(board.getPosibleMoves(), board, 0, -1, children);
        System.out.println(children.size());
        for (Board child : children) {
            System.out.println(".......................");
            child.printBoard();
        }*/
    }

    private static void putSons(List<Arc> arcList, Board board, int index, int prevScore, Set<Board> children) {
        if (prevScore == board.scoresCheck()) { //los scores siguen iguales no puede realizar mas movimientos
            children.add(board.clone());
            return;
        }
        int currentScore = board.scoresCheck();
        for (int i = index; i < arcList.size(); i++) {
            swap(arcList, i, index);
            board.addArc(arcList.get(index));
            putSons(arcList, board, index+1, currentScore, children);
            board.removeArc(arcList.get(index));
            swap(arcList, i, index);
        }
        return;
    }

    public static void swap(List<Arc> list, int i, int j) {
        Arc aux = list.get(i);
        list.set(i, list.get(j));
        list.set(j, aux);
    }
}