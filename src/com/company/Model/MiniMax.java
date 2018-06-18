package com.company.Model;

import java.util.Set;

public abstract class MiniMax {

    private int depthOrTime;

    public MiniMax(int depthOrTime) {
        this.depthOrTime = depthOrTime;
    }

    public abstract Board bestMove(Board board, Player currentPlayer, Player nextPlayer);

    //public abstract Set<Arc> bestMove2(Board board, Player currentPlayer, Player nextPlayer);

    public int evaluate(Board board, Player actualPlayer, Player lastPlayer) {
        return actualPlayer.getScore() - lastPlayer.getScore();
    }

    public int getDepthOrTime() {
        return depthOrTime;
    }
}
