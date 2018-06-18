package com.company.Model;
import java.util.Set;

public class MiniMaxD extends MiniMax {

    public MiniMaxD(int depthOrTime) {
        super(depthOrTime);
    }

    public int minimax(Tree current, int depth, boolean maximaizer, Player currentPlayer, Player nextPlayer) {
        if (depth == 0 || current.board.boardComplete()) {
            return evaluate(current.board, current.board.getCurrentPlayer(), currentPlayer);
        }
        if (maximaizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            current.generateChildren();
            for (Tree child : current.children) {
                int auxEvalution = minimax(child, depth-1, !maximaizer, nextPlayer, currentPlayer); {
                    maxEvaluation = Math.max(maxEvaluation, auxEvalution);
                }
            }
            return maxEvaluation;
        } else { //MINIMIZER
            int minEvaluation = Integer.MAX_VALUE;
            current.generateChildren();
            for (Tree child : current.children) {
                int auxEvaluation = minimax(child, depth-1, !maximaizer, nextPlayer, currentPlayer);
                minEvaluation = Math.min(minEvaluation, auxEvaluation);
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
        for (Tree child : tree.children) {
            if ((auxEvaluation = minimax(child, this.getDepthOrTime()-1, false, nextPlayer, currentPlayer)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child.board;
            }
        }
        return bestBoard;
    }

   /* @Override
    public Set<Arc> bestMove2(Board board, Player currentPlayer, Player nextPlayer) {
        Tree tree = new Tree(board);
        Board bestBoard = null;
        tree.generateChildren();
        int bestBoardEvaluation = Integer.MIN_VALUE;
        int auxEvaluation = 0;
        for (Tree child : tree.children) {
            if ((auxEvaluation = minimax(child, this.getDepthOrTime()-1, false, nextPlayer, currentPlayer)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child.board;
            }
        }
        return bestBoard;
    }*/
}
