package com.company.Model;

public class Arc {

    private int player;
    private int x;
    private int y;
    private boolean horizontal;

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return !horizontal;
    }

    public Arc(int player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public int getColour() {
        return player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
