package com.company.Model;

public class Player implements Cloneable{
    private int score;
   // private String name;
    //color

    public Player(int score) {
        this.score = score;
    }

    public boolean isHuman() {
        return true;
    }

    public int getScore() {
        return score;
    }

    public void incScore(int amount) {
        this.score += amount;
    }

    public Player clone() {
        return new Player(this.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return score == player.score;
    }

    @Override
    public int hashCode() {
        return score;
    }

}
