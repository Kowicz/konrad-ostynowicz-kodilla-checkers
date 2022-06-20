package com.kodilla.checkers.piece;

import javafx.scene.image.Image;

public class DarkPiece extends Piece{
    private boolean highlighted;
    private final Image darkPieceImage = new Image("file:src/main/resources/pieces/dark_piece.png");
    private final Image highlightedDarkPieceImage = new Image("file:src/main/resources/pieces/highlighted_dark_piece.png");

    public DarkPiece(int rowIndex, int columnIndex) {
        super(rowIndex, columnIndex);
    }

    @Override
    public Image getImage() {
        if (this.highlighted) {
            return this.highlightedDarkPieceImage;
        } else {
            return this.darkPieceImage;
        }
    }

}
