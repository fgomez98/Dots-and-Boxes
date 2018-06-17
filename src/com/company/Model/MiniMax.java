package com.company.Model;

public class MiniMax implements Heuristica {

    @Override
    public int evaluate(Board board, int player) {
        return 0;
    }

    public int minimax(Tree current, int depth, boolean maximaizer, int player) {
        if (depth == 0 || current.board.boardComplete()) {
            return evaluate(current.board, player);
        }
        if (maximaizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            current.generateChildren();
            for (Tree child : current.children) {
                int auxEvalution = minimax(child, depth-1, !maximaizer, -player); {
                    maxEvaluation = Math.max(maxEvaluation, auxEvalution);
                }
            }
            return maxEvaluation;
        } else { //MINIMIZER
            int minEvaluation = Integer.MAX_VALUE;
            current.generateChildren();
            for (Tree child : current.children) {
                int auxEvaluaation = minimax(child, depth-1, !maximaizer, -player);
                minEvaluation = Math.min(minEvaluation, auxEvaluaation);
            }
            return minEvaluation;
        }
    }

}
