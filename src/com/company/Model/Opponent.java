package com.company.Model;

public class Opponent extends Player {

    public Opponent(int id) {
        super(id);
    }

    public Opponent(int id,int score) {
        super(id, score);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    public Opponent clone() {
        return new Opponent(this.getId(), this.getScore());
    }
}
