package com.kodilla.checkers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Checkers extends Application {
    private final Processor processor = new Processor();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.showMenu(primaryStage);
    }

    private void showMenu(Stage primaryStage) {
        Menu menu = this.processor.createMenu();

        Button newGameButton = menu.getNewGameButton();
        Button quitButton = menu.getQuitGameButton();

        newGameButton.setOnAction(event -> this.newGameEvent(primaryStage));

        quitButton.setOnAction(quitGameEvent -> Platform.exit());

        GridPane menuGrid = menu.getGridPane();
        Scene menuScene = new Scene(menuGrid, 300, 300);

        primaryStage.setTitle("Checkers");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void newGameEvent(Stage primaryStage) {
        Board board = this.processor.createNewBoard();

        GridPane grid = board.getGridPane();

        TilePane[][] tiles = board.getTiles();

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                int currentRow = row;
                int currentColumn = column;
                TilePane temp = tiles[row][column];

                temp.setOnMouseClicked(
                        mouseClickedEvent -> {
                            this.processor.handleClickAction(currentRow, currentColumn);
                            if(this.processor.isGameEnded()) {
                                showEndGameScreen(primaryStage, this.processor.didPlayerWon());
                            }
                        }
                );
            }
        }

        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showEndGameScreen(Stage primaryStage, boolean playerWon) {
        EndGameScreen endGameScreen = new EndGameScreen(playerWon);

        endGameScreen.getYesButton().setOnAction(yesClickedEvent -> this.newGameEvent(primaryStage));
        endGameScreen.getNoButton().setOnAction(noClickedEvent -> this.showMenu(primaryStage));

        GridPane endGameGridPane = endGameScreen.getGridPane();

        Scene endGameScene = new Scene(endGameGridPane, 300, 300);
        primaryStage.setScene(endGameScene);
        primaryStage.show();
    }

}
