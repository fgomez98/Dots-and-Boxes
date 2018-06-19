package com.company.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Cloneable, Serializable {
    private int score;
    private int id;
   // private String name;
    //color

    public Player(int score, int id) {
        this.score = score;
        this.id = id;
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
        return new Player(this.score, this.id);
    }

    public int getId() {
        return id;
    }

    public void saveObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(score);
        out.writeObject(id);

    }

    public void loadObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        score = (int) ois.readObject();
        id = (int) ois.readObject();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return id == player.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
