package com.company;

import com.company.Model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {

    static Set<Board> children = new HashSet<>();

    public static void main(String[] args) {
        GameState game = new GameState(3, false, 1, true, true);
        game.printTerminal();
        int i = 10;
        Random rand = new Random();
        while ((i--) > 0) {
            boolean horizontal = true;
            if (rand.nextInt(1) == 0) {
                horizontal = false;
            }
            int x = rand.nextInt(2);
            int y = rand.nextInt(2);
            System.out.println("horizontal: " + horizontal + " x: " + x + "y: " +y);
            game.handelInput(x, y, horizontal);
            game.printTerminal();
        }
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
