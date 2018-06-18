package com.company.Model;

public class Player implements Cloneable{
    private int score;
   // private String name;
    //color

    public Player() {
        //this.name = name;
        this.score = 0;
    }

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


   /* public String getName() {
        return name;
    }
    */

}
