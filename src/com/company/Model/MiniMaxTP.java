package com.company.Model;

public class MiniMaxTP extends MiniMax {

    public MiniMaxTP(int depthOrTime) {
        super(depthOrTime);
    }

    public int minimax(Tree current, long timeLimit, boolean maximizer, Player currentPlayer, Player nextPlayer, int alpha, int beta) {
        current.visited();
        if (System.currentTimeMillis() >= timeLimit) {
            int evaluation = heuristica(current.getBoard(), current.getBoard().getCurrentPlayer(), currentPlayer);
            current.setPuntaje(evaluation);
            return evaluation;
        }
        if (maximizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            Tree max = null;
            int auxEvalution = 0;
            current.generateChildren();
            for (Tree child : current.getChildren()) {
                if ((auxEvalution = minimax(child, timeLimit, !maximizer, nextPlayer, currentPlayer, alpha, beta)) > maxEvaluation) {
                    maxEvaluation = auxEvalution;
                    max = child;
                    alpha = Math.max(alpha, auxEvalution);
                    if (beta <= alpha) {
                        return maxEvaluation;
                    }
                }
            }
            current.setPuntaje(maxEvaluation);
            max.choosen();
            return maxEvaluation;
        } else { //MINIMIZER
            int minEvaluation = Integer.MAX_VALUE;
            int auxEvalution = 0;
            Tree min = null;
            current.generateChildren();
            for (Tree child : current.getChildren()) {
                if((auxEvalution = minimax(child, timeLimit, !maximizer, nextPlayer, currentPlayer, alpha, beta)) < minEvaluation) {
                    minEvaluation = auxEvalution;
                    beta = Math.min(beta, auxEvalution);
                    min = child;
                    if (beta <= alpha) {
                        return minEvaluation;
                    }
                }
            }
            current.setPuntaje(minEvaluation);
            min.choosen();
            return minEvaluation;
        }
    }

    @Override
    public Tree bestMove2(Board board, Player currentPlayer, Player nextPlayer) {
        Tree tree = new Tree(board);
        Tree bestBoard = null;
        tree.generateChildren();
        tree.visited();
        int bestBoardEvaluation = Integer.MIN_VALUE;
        int auxEvaluation = 0;
        Tree max = null;
        long timeLimit = this.getDepthOrTime() + System.currentTimeMillis();
        for (Tree child : tree.getChildren()) {
            if ((auxEvaluation = minimax(child, timeLimit, false, nextPlayer, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child;
                max = child;
            }
        }
        max.choosen();
        tree.setPuntaje(bestBoardEvaluation);
        tree.choosen();
        tree.export();
        return bestBoard;
    }
}
