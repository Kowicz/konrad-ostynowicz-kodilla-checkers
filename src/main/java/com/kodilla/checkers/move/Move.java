package com.kodilla.checkers.move;

import com.kodilla.checkers.piece.Piece;

public class Move {
    private Piece pieceToBeMoved;
    private int targetRow;
    private int targetColumn;

    public Move(Piece pieceToBeMoved, int targetRow, int targetColumn) {
        this.pieceToBeMoved = pieceToBeMoved;
        this.targetRow = targetRow;
        this.targetColumn = targetColumn;
    }

    public Piece getPieceToBeMoved() {
        return pieceToBeMoved;
    }

    public void setPieceToBeMoved(Piece pieceToBeMoved) {
        this.pieceToBeMoved = pieceToBeMoved;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(int targetColumn) {
        this.targetColumn = targetColumn;
    }
}
