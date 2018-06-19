package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Arc implements Serializable{

    private Player player;
    private int x;
    private int y;
    private boolean horizontal;

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return !horizontal;
    }

    public Arc(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public Arc(Player player, int x, int y, boolean horizontal) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
    }

    public Player getPlayer() {
        return player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arc arc = (Arc) o;

        if (x != arc.x) return false;
        if (y != arc.y) return false;
        if (horizontal != arc.horizontal) return false;
        return player != null ? player.equals(arc.player) : arc.player == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + (horizontal ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "player=" + player +
                ", x=" + x +
                ", y=" + y +
                ", horizontal=" + horizontal +
                '}';
    }

    public void saveObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(player);
        out.writeObject(x);
        out.writeObject(y);
        out.writeObject(horizontal);
    }

    public void loadObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        player = (Player) ois.readObject();
        x = (int) ois.readObject();
        y = (int) ois.readObject();
        horizontal = (boolean) ois.readObject();
    }
}
