package com.company.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Tree {

    private static long IDUnique = 0;
    private long id;
    private int puntaje;
    private boolean elegido;
    private boolean podado;
    private Board board;
    private Set<Tree> children;
    private Set<Arc> arcs;

    public Tree(Board board) {
        IDUnique = (IDUnique+1) % Long.MAX_VALUE;
        this.id = IDUnique;
        this.elegido = false;
        this.podado = true;
        this.puntaje = 0;
        this.board = board;
        this.children = new HashSet<>();
        this.arcs = new HashSet<>();
    }

    public Tree(Board board, Set<Arc> arcs) {
        this.elegido = false;
        this.podado = true;
        this.puntaje = 0;
        IDUnique = (IDUnique+1) % Long.MAX_VALUE;
        this.id = IDUnique;
        this.board = board;
        this.arcs = arcs;
        this.children = new HashSet<>();
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void visited() {
        this.podado = false;
    }

    public void choosen() {
        this.elegido = true;
    }
    private void putSons(List<Arc> posibleMoves, Board board, int index, int prevScore, LinkedList<Arc> arcsAdded) {
        if (prevScore == board.scoresCheck() || board.boardComplete()) { //los scores siguen iguales no puede realizar mas movimientos
            children.add(new Tree(board.clone(), new HashSet<>(arcsAdded)));
            return;
        }
        int currentScore = board.scoresCheck();
        for (int i = index; i < posibleMoves.size(); i++) {
            swap(posibleMoves, i, index);
            board.addArc(posibleMoves.get(index));
            arcsAdded.addLast(posibleMoves.get(index));
            putSons(posibleMoves, board, index+1, currentScore, arcsAdded);
            board.removeArc(posibleMoves.get(index));
            arcsAdded.removeLast();
            swap(posibleMoves, i, index);
        }
    }

    public void swap(List<Arc> list, int i, int j) {
        Arc aux = list.get(i);
        list.set(i, list.get(j));
        list.set(j, aux);
    }

    public void generateChildren() {
        putSons(board.getPosibleMoves(), board, 0, -1, new LinkedList<Arc>());
    }

    public Board getBoard() {
        return board;
    }

    public Set<Tree> getChildren() {
        return children;
    }

    public Set<Arc> getArcs() {
        return arcs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        return board != null ? board.equals(tree.board) : tree.board == null;
    }

    @Override
    public int hashCode() {
        return board != null ? board.hashCode() : 0;
    }

    public void export() {
        File fpw = new File("/Users/fermingomez/Desktop/graph.dot");
        try {
            FileWriter fw = new FileWriter(fpw);
            fw.write("digraph {\n");
            fw.write(this.id + "[label=\"" +"Start " + "puntaje: " + this.puntaje + "\"");
            if (this.elegido) {
                fw.write(", style=filled, fillcolor=red");
            } else if (this.podado) {
                fw.write(", style=filled, fillcolor=grey");
            }
            fw.write("];\n");
            for (Tree child : this.children) {
                export(child, fw);
            }
            for (Tree child: this.children) {
                fw.write(this.id+" -> "+child.id+"\n");
            }
            fw.write("}\n");
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void export(Tree current, FileWriter fw) {
        try {
            fw.write(current.id + " [label=\"");
            for (Arc arc : current.arcs) {
                fw.write("(" + arc.getX()+","+arc.getY()+")" );
            }
            fw.write(" puntaje: " + current.puntaje);
            fw.write("\"");
            if (current.elegido) {
                fw.write(", style=filled, fillcolor=red");
            } else if (current.podado) {
                fw.write(", style=filled, fillcolor=grey");
            }
            fw.write("];\n");
            for (Tree child : current.children) {
                export(child, fw);
            }
            for (Tree child: current.children) {
                    fw.write(current.id+" -> "+child.id+"\n");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
