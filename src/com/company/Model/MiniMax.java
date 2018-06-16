package com.company.Model;
import java.util.LinkedList;
import java.util.List;

public class MiniMax implements Heuristica{

    @Override
    public int evaluate(Board board) {
        return 0;
    }

    public int minimax(Tree current, int depth, boolean maximaizer, int player) {
        if (depth == 0 || current.board.boardComplete()) {
            return evaluate(current.board);
        }
        for (Arc arc : current.board.getPosibleMoves()) {
            int score = board
        }

    }
}
