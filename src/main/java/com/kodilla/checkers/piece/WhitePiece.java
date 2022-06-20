package com.kodilla.checkers.piece;

import javafx.scene.image.Image;

public class WhitePiece extends Piece {
    private final Image whitePieceImage = new Image("file:src/main/resources/pieces/white_piece.png");
    private final Image clickHighlightedWhitePieceImage = new Image("file:src/main/resources/pieces/clicked_highlight_white_piece.png");
    private final Image highlightedForStrikeWhitePieceImage = new Image("file:src/main/resources/pieces/strike_highlight_white_piece.png");

    public WhitePiece(int rowIndex, int columnIndex) {
        super(rowIndex, columnIndex);
    }

    @Override
    public Image getImage() {
         if (this.isHighlighted()) {
            return this.clickHighlightedWhitePieceImage;
        } else if (this.isMandatoryStrike()) {
            return this.highlightedForStrikeWhitePieceImage;
        } else {
            return this.whitePieceImage;
        }
    }


}
