package com.company.Model;

public class MiniMaxTP extends MiniMax {

    public MiniMaxTP(int depthOrTime) {
        super(depthOrTime);
    }

    public int minimax(Tree current, long timeLimit, boolean maximaizer, Player currentPlayer, Player nextPlayer, int alpha, int beta) {
        if (System.currentTimeMillis() == timeLimit) {
            return heuristica(current.getBoard(), current.getBoard().getCurrentPlayer(), currentPlayer);
        }
        if (maximaizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            current.generateChildren();
            for (Tree child : current.getChildren()) {
                int auxEvalution = minimax(child, timeLimit, !maximaizer, nextPlayer, currentPlayer,alpha, beta); {
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
            for (Tree child : current.getChildren()) {
                int auxEvaluation = minimax(child, timeLimit, !maximaizer, nextPlayer, currentPlayer, alpha, beta);
                minEvaluation = Math.min(minEvaluation, auxEvaluation);
                beta = Math.min(beta, auxEvaluation);
                if (beta <= alpha) {
                    return minEvaluation;
                }
            }
            return minEvaluation;
        }
    }

    @Override
    public Board bestMove(Board board, Player currentPlayer, Player nextPlayer) {
        Tree tree = new Tree(board);
        Board bestBoard = null;
        tree.generateChildren();
        int bestBoardEvaluation = Integer.MIN_VALUE;
        int auxEvaluation = 0;
        for (Tree child : tree.getChildren()) {
            if ((auxEvaluation = minimax(child, this.getDepthOrTime() + System.currentTimeMillis() , false, nextPlayer, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child.getBoard();
            }
        }
        return bestBoard;
    }

    @Override
    public Tree bestMove2(Board board, Player currentPlayer, Player nextPlayer) {
        Tree tree = new Tree(board);
        Tree bestBoard = null;
        tree.generateChildren();
        int bestBoardEvaluation = Integer.MIN_VALUE;
        int auxEvaluation = 0;
        for (Tree child : tree.getChildren()) {
            if ((auxEvaluation = minimax(child, this.getDepthOrTime() + System.currentTimeMillis(), false, nextPlayer, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child;
            }
        }
        return bestBoard;
    }
}
