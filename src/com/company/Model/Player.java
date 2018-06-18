package com.company.Model;

public class Player implements Cloneable{
    private int score;
    private int id;
   // private String name;
    //color

    public Player(int id) {
        this.score = 0;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public Player(int id, int score) {
        this(id);
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

/* public String getName() {
        return name;
    }
    */

}
