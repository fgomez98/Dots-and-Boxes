package com.company.Model;

import java.util.LinkedList;
import java.util.List;

public class Tree {
    Board board;
    List<Tree> children;

    public Tree(Board board) {
        this.board = board;
        this.children = new LinkedList<>();
    }

    public void add(Board board) {
        children.add(new Tree(board));
    }
}
