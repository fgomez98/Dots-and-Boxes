package com.company.Model;

import org.omg.PortableInterceptor.INACTIVE;

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

    public Board bestMove(Board board, int depth, int player) {
        Tree tree = new Tree(board);
        Board bestBoard = null;
        tree.generateChildren();
        int bestBoardEvaluation = Integer.MIN_VALUE;
        int auxEvaluation = 0;
        for (Tree child : tree.children) {
            if ((auxEvaluation = minimax(child, depth-1, false, -player, Integer.MIN_VALUE, Integer.MAX_VALUE)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child.board;
            }
        }
        return bestBoard;
    }

}
