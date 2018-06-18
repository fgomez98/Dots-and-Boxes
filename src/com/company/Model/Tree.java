package com.company.Model;

import java.util.*;

public class Tree {
    protected Board board;
    protected Set<Tree> children;
    protected Set<Arc> arcs;

    public Tree(Board board) {
        this.board = board;
        this.children = new HashSet<>();
        this.arcs = new HashSet<>();
    }

/*
    public Tree(Board board, Arc arc, int player) {
        int currentScore = board.getPlayerScore(player);
        board.addArc(arc);
        List<Arc> arcSet = board.getPosibleMoves();
        putSons(arcSet, board.clone(), 0);
    }
*/

    private void putSons(List<Arc> arcList, Board board, int index, int prevScore) {
        if (prevScore == board.scoresCheck()) { //los scores siguen iguales no puede realizar mas movimientos
            children.add(new Tree(board.clone()));
            return;
        }
        int currentScore = board.scoresCheck();
        for (int i = index; i < arcList.size(); i++) {
            swap(arcList, i, index);
            board.addArc(arcList.get(index));
            putSons(arcList, board, index+1, currentScore);
            board.removeArc(arcList.get(index));
            swap(arcList, i, index);
        }
        return;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        return board != null ? board.equals(tree.board) : tree.board == null;
    }

    @Override
    public int hashCode() {
        return board != null ? board.hashCode() : 0;
    }
}
