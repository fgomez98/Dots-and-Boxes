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

    public abstract Tree bestMove2(Board board, Player currentPlayer, Player nextPlayer);

    public int heuristica(Board board, Player actualPlayer, Player lastPlayer) {
        int ret;
        if (actualPlayer.equals(board.getPlayer2())){
            ret = 20 * (board.getPlayer1().getScore()-board.getPlayer2().getScore());//fijo un peso 10 para la diferencia de puntos entre jugadores en la posicion especificada
        } else{
            ret = 20 * (board.getPlayer2().getScore()-board.getPlayer1().getScore());//fijo un peso 10 para la diferencia de puntos entre jugadores en la posicion especificada
        }
        if(actualPlayer.equals(lastPlayer)){
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
