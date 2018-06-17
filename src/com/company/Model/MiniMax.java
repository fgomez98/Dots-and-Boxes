package com.company.Model;

public class MiniMax implements Heuristica {

    @Override
    public int evaluate(Board board, int player) {
        return 0;
    }

    public int minimax(Tree current, int depth, boolean maximaizer, int player, int alpha, int beta) {
        if (depth == 0 || current.board.boardComplete()) {
            return evaluate(current.board, player);
        }
        if (maximaizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            current.generateChildren();
            for (Tree child : current.children) {
                int auxEvalution = minimax(child, depth-1, !maximaizer, -player, alpha, beta); {
                    maxEvaluation = Math.max(maxEvaluation, auxEvalution);
                    alpha = Math.max(alpha, auxEvalution);
                    if (beta <=  alpha) {
                        return maxEvaluation;
                    }
                }
            }
            return maxEvaluation;
        } else { //MINIMIZER
            int minEvaluation = Integer.MAX_VALUE;
            current.generateChildren();
            for (Tree child : current.children) {
                int auxEvaluaation = minimax(child, depth-1, !maximaizer, -player, alpha, beta);
                minEvaluation = Math.min(minEvaluation, auxEvaluaation);
                beta = Math.min(beta, auxEvaluaation);
                if (beta <= alpha) {
                    return minEvaluation;
                }
            }
            return minEvaluation;
        }
    }

}
