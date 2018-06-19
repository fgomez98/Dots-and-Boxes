package com.company.Model;

public class MiniMaxD extends MiniMax {

    public MiniMaxD(int depthOrTime) {
        super(depthOrTime);
    }

    public int minimax(Tree current, int depth, boolean maximizer, Player currentPlayer, Player nextPlayer) {
        current.visited();
        if (depth == 0 || current.getBoard().boardComplete()) {
            int evaluation = heuristica(current.getBoard(), current.getBoard().getCurrentPlayer(), currentPlayer);
            current.setPuntaje(evaluation);
            return evaluation;
        }
        if (maximizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            Tree max = null;
            int auxEvaluation = 0;
            current.generateChildren();
            for (Tree child : current.getChildren()) {
                if ((auxEvaluation = minimax(child, depth-1, !maximizer, nextPlayer, currentPlayer)) > maxEvaluation) {
                    maxEvaluation = auxEvaluation;
                    max = child;
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
                if((auxEvalution = minimax(child, depth-1, !maximizer, nextPlayer, currentPlayer)) < minEvaluation) {
                    minEvaluation = auxEvalution;
                    min = child;
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
        for (Tree child : tree.getChildren()) {
            if ((auxEvaluation = minimax(child, this.getDepthOrTime()-1, false, nextPlayer, currentPlayer)) > bestBoardEvaluation) {
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
