package com.company.Model;

public class Opponent extends Player {

    public Opponent(int score, int id) {
        super(score, id);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    public Opponent clone() {
        return new Opponent(this.getScore(), this.getId());
    }
}
