package com.company.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class PaneController {

    private GameState gameState;

    @FXML
    private Button undoButton, restartButton;
    @FXML
    private TextField player1Name,player2Name, player1Score, player2Score;
    @FXML
    private AnchorPane pane;

    public void initModel(GameState gameState) {
        this.gameState = gameState;
        player1Score.addTextListener();
    }
}
