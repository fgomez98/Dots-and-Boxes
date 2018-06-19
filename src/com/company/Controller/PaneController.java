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
    private static final String TURN = "white";
    private static final String NO_TURN = "red";
    private static final int PADDING = 10;

    private GameState gameState;
    private Main main;
    private Player player1;

    @FXML
    private TextField player1Score, player2Score,player1Name,player2Name;
    @FXML
    private AnchorPane pane;

    private GridPane grid;

    public void initModel(GameState gameState) {
        setUpGState(gameState);
        setUpGrid();
        setUpNames();
        setTurn();
        resetRectangles();
        resetScore();
        pane.getChildren().add(grid);
    }

    private void setUpNames() {
        player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        String aux = player1.isHuman() ? "Player 1": "Ai 1";
        player1Name.textProperty().setValue(aux);
        aux = player2.isHuman() ? "Player 2": "Ai 2";
        player2Name.textProperty().setValue(aux);
    }

    private void setTurn() {
        if (gameState.getCurrentPlayer().equals(player1)){
            player1Name.setStyle("-fx-background-color: "+ TURN +";");
            player2Name.setStyle("-fx-background-color: "+ NO_TURN +";");
        }else {
            player1Name.setStyle("-fx-background-color: "+ NO_TURN +";");
            player2Name.setStyle("-fx-background-color: "+  TURN+";");
        }
    }

    private void setUpGrid() {
        grid = new GridPane();
        grid.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));

        int n = gameState.size();
        int cols = n + n - 1;
        double maxHeight = pane.getHeight();
        double maxWidth = pane.getWidth();
        double max = (maxHeight < maxWidth) ? maxHeight : maxWidth;

        double radius = max / (2 * (n + 3 * (n - 1)));
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
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            boolean horizontal = rowIndex % 2 == 0;
            gameState.handleInput(colIndex / 2, rowIndex / 2, horizontal);
            resetRectangles();
            resetScore();
            setTurn();
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
    private void handleSave() {
        main.save();
    }
}
