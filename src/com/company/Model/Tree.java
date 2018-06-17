package com.company.Model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Tree {
    Board board;
    List<Tree> children;

    public Tree(Board board) {
        this.board = board;
        this.children = new LinkedList<>();
    }
/*
    public Tree(Board board, Arc arc, int player) {
        int currentScore = board.getPlayerScore(player);
        board.addArc(arc);
        List<Arc> arcSet = board.getPosibleMoves();
        putSons(arcSet, board.clone(), 0);
    }
*/
    private void putSons(List<Arc> arcList, Board board, int index, int currentScores) {
        if (currentScores == board.scoresCheck()) { //los scores siguen iguales no puede realizar mas movimientos
            children.add(new Tree(board.clone()));
        }
        for (int i = index; i < arcList.size(); i++) {
            swap(arcList, i, index);
            board.addArc(arcList.get(index));
            putSons(arcList, board, index+1, currentScores);
            board.removeArc(arcList.get(index));
            swap(arcList, i, index);
        }
    }

    public void swap(List<Arc> list, int i, int j) {
        Arc aux = list.get(i);
        list.set(i, list.get(j));
        list.set(j, aux);
    }

    public void add(Board board) {
        children.add(new Tree(board));
    }

    public void generateChildren() {
        putSons(board.getPosibleMoves(), board, 0, -1);
    }

}
