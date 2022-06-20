package com.kodilla.checkers.tile;

import com.kodilla.checkers.piece.Piece;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class Tile extends TilePane {
    private Piece piece;
    private boolean isWhite;
    private ImageView pieceImageView;

    public Tile(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void addPiece(Piece piece) {
        if (!this.isWhite) {
            this.piece = piece;
        }
    }

    public void removePiece() {
        this.piece = null;
    }

    public boolean hasPiece() {
        return this.piece != null;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public void addImage() {
        if (piece != null) {
            this.getChildren().remove(pieceImageView);
            pieceImageView = new ImageView(piece.getImage());
            this.getChildren().add(pieceImageView);
        }
    }

    public void removeImage() {
        if (piece != null) {
            this.getChildren().remove(pieceImageView);
            pieceImageView = null;
        }
    }

//    public void addHighlightedImage() {
//        if (piece != null) {
//            pieceImageView = new ImageView(piece.getImage());
//            this.getChildren().add(pieceImageView);
//        }
//    }
}
