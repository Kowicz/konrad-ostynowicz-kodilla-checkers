package com.kodilla.checkers;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Menu {
    private final GridPane gridPane = new GridPane();
    private final Button newGameButton = new Button();
    private final Button quitGameButton = new Button();
    private final Image menu = new Image("file:src/main/resources/menu/menu_background.png");
    private final Image newGameButtonImage = new Image("file:src/main/resources/menu/new_game_button.png");
    private final Image quitGameButtonImage = new Image("file:src/main/resources/menu/quit_button.png");


    public Menu() {
        this.prepareBackground();
        this.prepareGrid();
        this.prepareButtons();
        this.addButtons();
    }

    private void prepareBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(300,
                300, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(menu, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.gridPane.setBackground(background);
    }

    private void prepareGrid() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(50);
    }

    private void prepareButtons() {
        this.prepareNewGameButton();
        this.prepareQuitButton();
    }

    private void addButtons() {
        this.addNewGameButton();
        this.addQuitButton();
    }

    private void prepareNewGameButton() {
        this.newGameButton.setGraphic(new ImageView(this.newGameButtonImage));
    }

    private void prepareQuitButton() {
        this.quitGameButton.setGraphic(new ImageView(this.quitGameButtonImage));
    }

    private void addNewGameButton() {
        this.gridPane.add(this.newGameButton, 0, 0, 1, 1);
    }

    private void addQuitButton() {
        this.gridPane.add(this.quitGameButton, 0, 1, 1, 1);
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    public Button getNewGameButton() {
        return this.newGameButton;
    }

    public Button getQuitGameButton() {
        return this.quitGameButton;
    }
}
