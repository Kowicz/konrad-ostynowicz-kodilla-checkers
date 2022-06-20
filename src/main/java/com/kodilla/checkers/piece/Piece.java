package com.kodilla.checkers.piece;

import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

public class Piece extends TilePane {
    private boolean highlighted;
    private boolean mandatoryStrike;
    private int row = 0;
    private int column = 0;

    public Piece(int rowIndex, int columnIndex) {
        this.row = rowIndex;
        this.column = columnIndex;
    }

    public void setHighlight(boolean state) {
        this.highlighted = state;
    }

    public boolean isHighlighted() {
        return this.highlighted;
    }

    public void setMandatoryStrike(boolean state) {
        this.mandatoryStrike = state;
    }

    public boolean isMandatoryStrike() {
        return this.mandatoryStrike;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Image getImage() {
        return null;
    }
}
