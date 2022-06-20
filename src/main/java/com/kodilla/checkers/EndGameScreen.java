package com.kodilla.checkers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class EndGameScreen {
    private final GridPane gridPane = new GridPane();
    private final Button yesButton = new Button();
    private final Button noButton = new Button();
    private final Image endGameWinBackgroundImage = new Image("file:src/main/resources/endgame/won_endgame_background.png");
    private final Image endGameLoseBackgroundImage = new Image("file:src/main/resources/endgame/lose_endgame_background.png");
    private final Image noButtonImage = new Image("file:src/main/resources/endgame/no_button.png");
    private final Image yesButtonImage = new Image("file:src/main/resources/endgame/yes_button.png");

    public EndGameScreen(boolean playerWon) {
        this.prepareBackground(playerWon);
        this.prepareGrid();
        this.prepareButtons();
        this.addButtons();
    }

    private void prepareBackground(boolean playerWon) {
        BackgroundSize backgroundSize = new BackgroundSize(300,300, false,
                false, true, false);
        BackgroundImage backgroundImage =
                new BackgroundImage(playerWon ? this.endGameWinBackgroundImage : this.endGameLoseBackgroundImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.gridPane.setBackground(background);
    }

    private void prepareGrid() {
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setVgap(20);
        this.gridPane.setHgap(20);
    }

    private void prepareButtons() {
        this.prepareYesButton();
        this.prepareNoButton();
    }
    private void addButtons() {
        this.addYesButton();
        this.addNoButton();
    }

    private void prepareYesButton() {
        this.yesButton.setGraphic(new ImageView(this.yesButtonImage));
    }

    private void prepareNoButton() {
        this.noButton.setGraphic(new ImageView(this.noButtonImage));
    }

    private void addYesButton() {
        this.gridPane.add(this.yesButton, 0, 1, 1, 1);
    }

    private void addNoButton() {
        this.gridPane.add(this.noButton, 1, 1, 1, 1);
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    public Button getYesButton() {
        return this.yesButton;
    }

    public Button getNoButton() {
        return this.noButton;
    }
}
