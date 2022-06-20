package com.kodilla.checkers.move;

import com.kodilla.checkers.piece.Piece;

public class Strike extends Move{
    public Strike(Piece pieceToBeMoved, int targetRow, int targetColumn) {
        super(pieceToBeMoved, targetRow, targetColumn);
    }
}
