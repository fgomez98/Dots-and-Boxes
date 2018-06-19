package com.company.Controller;


import com.company.Main;
import com.company.Model.GameState;
import com.company.Model.Player;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class PaneController {

    private static final Paint ARROW_SELECTED = Color.NAVY;
    private static final Paint ARROW_UNSELECTED = Color.WHEAT;
    private static final Paint SQUARE_FULL = Color.RED;
    private static final Paint SQUARE_UNFULL = Color.WHITE;
    private static final Paint CIRCLE_COLOR = Color.GREEN;
    private GameState gameState;
    private Main main;
    private int cols;
    private static final double PADDING = 2.0;

    @FXML
    private TextField player1Score, player2Score;
    @FXML
    private AnchorPane pane;

    private GridPane grid;

    public void initModel(GameState gameState) {
        setUpGState(gameState);
        grid = new GridPane();
        setUpGrid();
        resetRectangles();
        resetScore();
        pane.getChildren().add(grid);
    }

    private void setUpGrid() {
        grid.setPadding(new Insets(0, 10, 0, 10));

        int n = gameState.size();
        this.cols = n + n - 1;
        double maxHeight = pane.getHeight();
        double maxWidth = pane.getWidth();
        double max = (maxHeight < maxWidth) ? maxHeight : maxWidth;

        double radius = max / (2 * (n + 3 * (n - 1)) + PADDING);
        double length = 6 * radius;
        double tall = radius * 2;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                if (i % 2 == 0 && j % 2 == 0)
                    grid.add(new Circle(radius, CIRCLE_COLOR), i, j); // column i, row j
                else if (i % 2 == 1 && j % 2 == 0) {
                    Rectangle rect = new Rectangle(length, tall, ARROW_UNSELECTED);
                    rect.setOnMouseClicked(new RectangleEvent());
                    grid.add(rect, i, j);
                } else if (i % 2 == 0 && j % 2 == 1) {
                    Rectangle rect = new Rectangle(tall, length, ARROW_UNSELECTED);
                    rect.setOnMouseClicked(new RectangleEvent());
                    grid.add(rect, i, j);
                }else { //cuadrados
                    Rectangle rect = new Rectangle(length, length, SQUARE_UNFULL);
                    grid.add(rect,i,j);
                }
            }
        }
    }

    private void setUpGState(GameState gameState) {
        this.gameState = gameState;
    }

    private void resetScore() {
        player1Score.textProperty().setValue(String.valueOf(gameState.getPlayer1Score()));
        player2Score.textProperty().setValue(String.valueOf(gameState.getPlayer2Score()));
    }

    private void resetRectangles() {
        Player[][] boxBoard = gameState.getBBoard();
        Player[][] vLines = gameState.getVBoard();
        Player[][] hLines = gameState.getHBoard();
        ObservableList<Node> childrens = grid.getChildren();
        int i, j;
        for (Node node : childrens) {
            i = GridPane.getColumnIndex(node); // x
            j = GridPane.getRowIndex(node); // y
            if (i % 2 == 1 && j % 2 == 0 ) { // horizontal
                if (hLines[j / 2][i / 2] != null)
                    ((Rectangle) node).setFill(ARROW_SELECTED);
                else
                    ((Rectangle) node).setFill(ARROW_UNSELECTED);
            } else if (i % 2 == 0 && j % 2 == 1 ) { // vertical
                if (vLines[j / 2][i / 2] != null)
                    ((Rectangle) node).setFill(ARROW_SELECTED);
                else
                    ((Rectangle) node).setFill(Color.WHEAT);
            } else if (i % 2 == 1 && j % 2 == 1 ) {
                if (boxBoard[j / 2][i / 2] != null)
                    ((Rectangle) node).setFill(SQUARE_FULL);
                else
                    ((Rectangle) node).setFill(SQUARE_UNFULL);
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private class RectangleEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            Rectangle rect = (Rectangle) source;
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            boolean horizontal = rowIndex % 2 == 0;
            gameState.handleInput(colIndex / 2, rowIndex / 2, horizontal);
            resetRectangles();
            resetScore();
        }
    }

    @FXML
    private void undoEvent() {
        gameState.undo();
        resetRectangles();
        resetScore();
    }

    @FXML
    private void restartEvent() {
        gameState = main.restart();
        initModel(gameState);
    }

    @FXML
    private void handleSaveNExit() {
        main.save();
    }
}
