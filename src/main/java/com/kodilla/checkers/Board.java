package com.kodilla.checkers;

import com.kodilla.checkers.piece.DarkPiece;
import com.kodilla.checkers.piece.WhitePiece;
import com.kodilla.checkers.tile.Tile;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Board {
    private final Image board = new Image("file:src/main/resources/board/board.png");
    private final int numberOfColumns = 10;
    private final int numberOfRows = 10;
    private final int columnWidth = 60;
    private final int rowHeight = 60;

    private final GridPane gridPane = new GridPane();
    private final Tile[][] tiles = new Tile[numberOfRows][numberOfColumns];

    public Board() {
        this.prepareBackground();
        this.prepareGridPane();
        this.prepareTiles();
        this.addTilesToGridPane();
        this.addPiecesToTiles();
        this.updateBoardsVisual();
    }

    private void prepareBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(this.numberOfColumns * this.columnWidth,
                numberOfRows * rowHeight,false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        gridPane.setBackground(background);


    }
    private void prepareGridPane() {
        this.addColumns();
        this.addRows();
    }

    private void addColumns() {
        for (int i = 0; i < this.numberOfColumns; i++) {
            ColumnConstraints column = new ColumnConstraints(this.columnWidth);
            this.gridPane.getColumnConstraints().add(column);
        }
    }

    private void addRows() {
        for (int i = 0; i < this.numberOfRows; i++) {
            RowConstraints row = new RowConstraints(this.rowHeight);
            this.gridPane.getRowConstraints().add(row);
        }
    }

    private void prepareTiles() {
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if ((row + column) % 2 == 0) {
                    this.tiles[row][column] = new Tile(true);
                    configureTile(this.tiles[row][column]);
                } else {
                    this.tiles[row][column] = new Tile(false);
                    configureTile(this.tiles[row][column]);
                }
            }
        }
    }

    private void addPiecesToTiles() {
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if (row < 4) {
                    DarkPiece tempDarkPiece = new DarkPiece(row, column);
                    this.tiles[row][column].addPiece(tempDarkPiece);
                } else if (row > 5) {
                    WhitePiece tempWhitePiece = new WhitePiece(row, column);
                    this.tiles[row][column].addPiece(tempWhitePiece);
                }
            }
        }
    }

    private void addTilesToGridPane() {
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                this.gridPane.add(this.tiles[row][column], column, row, 1 ,1);
            }
        }
    }

    public void updateBoardsVisual() {
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                this.tiles[row][column].addImage();
            }
        }
    }

    private void configureTile(Tile tile) {
        tile.setPrefColumns(1);
        tile.setPrefRows(1);
        tile.setMinSize(this.columnWidth, this.rowHeight);
        tile.setMaxSize(this.columnWidth, this.rowHeight);
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }
}
