package com.company.Model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class MiniMaxDP extends MiniMax {

    public MiniMaxDP(int depthOrTime) {
        super(depthOrTime);
    }

    public int minimax(Tree current, int depth, boolean maximizer, Player currentPlayer, Player nextPlayer, int alpha, int beta) {
        if (depth == 0 || current.getBoard().boardComplete()) {
            return heuristica(current.getBoard(), current.getBoard().getCurrentPlayer(), currentPlayer);
        }
        if (maximizer) { //MAXIMIZER
            int maxEvaluation = Integer.MIN_VALUE;
            current.generateChildren();
            for (Tree child : current.getChildren()) {
                int auxEvalution = minimax(child, depth-1, !maximizer, nextPlayer, currentPlayer,alpha, beta); {
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
                int auxEvaluation = minimax(child, depth-1, !maximizer, nextPlayer, currentPlayer, alpha, beta);
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
            if ((auxEvaluation = minimax(child, this.getDepthOrTime()-1, false, nextPlayer, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE)) > bestBoardEvaluation) {
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
            if ((auxEvaluation = minimax(child, this.getDepthOrTime()-1, false, nextPlayer, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE)) > bestBoardEvaluation) {
                bestBoardEvaluation = auxEvaluation;
                bestBoard = child;
            }
        }
        if (bestBoard == null) {
            System.out.println("tablero feo");
            board.printBoard();
        }
        return bestBoard;
    }
}