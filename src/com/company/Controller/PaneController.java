package com.company.Controller;


import com.company.Main;
import com.company.Model.GameState;
import com.company.Model.Player;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.*;


public class PaneController {

    private GameState gameState;
    private Main main;
    private Player[][] vLines;
    private Player[][] hLines;
    private int cols ;

    @FXML
    private TextField player1Score, player2Score;
    @FXML
    private AnchorPane pane;

    private GridPane grid;

    public void initModel(GameState gameState) {
        setUp(gameState);
        grid = new GridPane();
        grid.setPadding(new Insets(0, 10, 0, 10));

        int n = gameState.size();
        this.cols = n + n-1;
        double maxHeight = pane.getPrefHeight();
        double maxWidth = pane.getPrefWidth();
        double max = (maxHeight < maxWidth)? maxHeight: maxWidth;

        double radius = max / (2* (n+ 3*(n-1) ) );
        double length = 6*radius;
        double tall = radius*2;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                if (i%2 == 0 && j%2 == 0)
                    grid.add(new Circle(radius, Color.GREEN), i, j); // column i, row j
                else if (i%2 == 1 && j%2 == 0){
                    Rectangle rect = new Rectangle(length, tall, Color.WHEAT);
                    rect.setOnMouseClicked(new RectangleEvent());
                    grid.add(rect,i,j);
                }
                else if(i%2 == 0 && j%2 == 1){
                    Rectangle rect = new Rectangle(tall, length, Color.WHEAT);
                    rect.setOnMouseClicked(new RectangleEvent());
                    grid.add(rect,i,j);
                }
            }
        }
        resetScore();
        pane.getChildren().add(grid);
    }

    private void setUp(GameState gameState){
        this.gameState = gameState;
        getArcs();
    }
    private void getArcs(){
        vLines = gameState.getVBoard();
        hLines = gameState.getHBoard();
    }

    private void resetScore() {
        player1Score.textProperty().setValue(String.valueOf(gameState.getPlayer1Score()));
        player2Score.textProperty().setValue(String.valueOf(gameState.getPlayer2Score()));
    }

    private void resetRectangles(){
        ObservableList<Node> childrens = grid.getChildren();
        int i,j;
        for (Node node : childrens) {
            i = GridPane.getColumnIndex(node); // x
            j = GridPane.getRowIndex(node); // y
            if (i%2 == 1 && j%2 == 0 && hLines[j/2][i/2] != null){ // horizontal
                ((Rectangle)node).setFill(Color.NAVY);
            }
            else if(i%2 == 0 && j%2 == 1 && vLines[j/2][i/2] != null){ // vertical
                ((Rectangle)node).setFill(Color.NAVY);
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private class RectangleEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node)event.getSource() ;
            Rectangle rect = (Rectangle) source;
            //rect.setFill(Color.NAVY);
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            boolean horizontal = rowIndex%2 == 0;
            gameState.handleInput(colIndex/2,rowIndex/2, horizontal);
            getArcs();
            resetRectangles();
            resetScore();
        }
    }
    @FXML
    private void undoEvent()
    {
        System.out.println("puto el que lee");
        gameState.undo();
        getArcs();
        resetRectangles();

    }
    @FXML
    private void restartEvent()
    {
        gameState = main.restart();
        initModel(gameState);
    }

    private void saveData(File file) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        gameState.saveObject(out);
    }

    private void loadData(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        gameState = new GameState(ois);
    }
}
