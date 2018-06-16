package com.company.Model;

public class Arc {

    private Colour colour;
    private int x;
    private int y;
    private boolean horizontal;

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return !horizontal;
    }

    public Arc(Colour colour, int x, int y) {
        this.colour = colour;
        this.x = x;
        this.y = y;
    }

    public Colour getColour() {
        return colour;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
