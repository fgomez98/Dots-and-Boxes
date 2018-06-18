package com.company.Model;

public class Arc {

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

}
