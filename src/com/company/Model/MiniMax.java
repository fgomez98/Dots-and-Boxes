package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class MiniMax implements Serializable {

    private int depthOrTime;

    public MiniMax(int depthOrTime) {
        this.depthOrTime = depthOrTime;
    }

    public abstract Tree bestMove(Board board, Player currentPlayer, Player nextPlayer);

    public int heuristica(Board board, Player callPlayer, Player evalPlayer) {//eval player es a quien le toca
        int ret;
        if (callPlayer.equals(board.getPlayer1())){
            ret = 30 * (callPlayer.getScore()-board.getPlayer2().getScore());//fijo un peso 10 para la diferencia de puntos entre jugadores en la posicion especificada
        } else{
            ret = 30 * callPlayer.getScore()-board.getPlayer1().getScore();//fijo un peso 10 para la diferencia de puntos entre jugadores en la posicion especificada
        }
        if(callPlayer.equals(evalPlayer)){
            ret += 6*board.checkBoxesOf(3) + board.checkBoxesOf(2);
        } else {
            ret -= 6*board.checkBoxesOf(3) + board.checkBoxesOf(2);
        }
        return ret;
    }

    public int getDepthOrTime() {
        return depthOrTime;
    }

    public void saveObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(depthOrTime);

    }

    public void loadObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        depthOrTime = (int) ois.readObject();
    }
}
