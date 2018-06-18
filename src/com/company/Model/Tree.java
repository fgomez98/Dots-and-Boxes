package com.company.Model;

import java.util.*;

public class Tree {
    private Board board;
    private Set<Tree> children;
    private Set<Arc> arcs;

    public Tree(Board board) {
        this.board = board;
        this.children = new HashSet<>();
        this.arcs = new HashSet<>();
    }

    public Tree(Board board, Set<Arc> arcs) {
        this.board = board;
        this.arcs = arcs;
        this.children = new HashSet<>();
    }

    private void putSons(List<Arc> posibleMoves, Board board, int index, int prevScore, LinkedList<Arc> arcsAdedd) {
        if (prevScore == board.scoresCheck()) { //los scores siguen iguales no puede realizar mas movimientos
            children.add(new Tree(board.clone(), new HashSet<>(arcsAdedd)));
            return;
        }
        int currentScore = board.scoresCheck();
        for (int i = index; i < posibleMoves.size(); i++) {
            swap(posibleMoves, i, index);
            board.addArc(posibleMoves.get(index));
            arcsAdedd.addLast(posibleMoves.get(index));
            putSons(posibleMoves, board, index+1, currentScore, arcsAdedd);
            board.removeArc(posibleMoves.get(index));
            arcsAdedd.removeLast();
            swap(posibleMoves, i, index);
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
        putSons(board.getPosibleMoves(), board, 0, -1, new LinkedList<Arc>());
    }

    public Board getBoard() {
        return board;
    }

    public Set<Tree> getChildren() {
        return children;
    }

    public Set<Arc> getArcs() {
        return arcs;
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
