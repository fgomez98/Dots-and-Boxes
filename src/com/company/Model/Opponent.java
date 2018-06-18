package com.company.Model;

public class Opponent extends Player {

    public Opponent() {
        super();
    }

    public Opponent(int score) {
        super(score);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    public Opponent clone() {
        return new Opponent(this.getScore());
    }
}
