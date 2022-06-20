package com.kodilla.checkers;

import com.kodilla.checkers.move.Move;
import com.kodilla.checkers.move.Strike;
import com.kodilla.checkers.piece.DarkPiece;
import com.kodilla.checkers.piece.Piece;
import com.kodilla.checkers.piece.WhitePiece;
import com.kodilla.checkers.tile.Tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AIPLayer {
    private final Map<Move, Integer> potentialMoves = new HashMap<>();


    public Move makeMove(Tile[][] tiles) {
        this.clearPotentialMoves();
        this.checkForPotentialMoves(tiles);
        return this.determineBestMove();
    }

    private void clearPotentialMoves() {
        this.potentialMoves.clear();
    }

    private Move determineBestMove() {
        if (this.potentialMoves.isEmpty()) {
            return null;
        }

        Iterator<Map.Entry<Move, Integer>> iterator = this.potentialMoves.entrySet().iterator();

        Map.Entry<Move, Integer> bestMove = iterator.next();

        while (iterator.hasNext()) {
            Map.Entry<Move, Integer> tempMove = iterator.next();

            //comparing weights of all possible moves
            if (tempMove.getValue() > bestMove.getValue()) {
                bestMove = tempMove;
            }

        }
        return bestMove.getKey();
    }

    private void checkForPotentialMoves(Tile[][] tiles) {

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                if (tiles[row][column].hasPiece()) {
                    if (tiles[row][column].getPiece().getClass().equals(DarkPiece.class)) {

                        int targetRow = row + 1;
                        int targetColumn1 = column + 1;

                        boolean inBounds = this.checkIfInBounds(targetRow, targetColumn1);

                        if (inBounds) {
                            if (!tiles[targetRow][targetColumn1].hasPiece()) {
                                Move potentialMove = new Move(tiles[row][column].getPiece(), targetRow, targetColumn1);
                                int weight = this.determineWeight(tiles, potentialMove);
                                this.addPotentialMove(potentialMove, weight);
                            } else if (tiles[targetRow][targetColumn1].getPiece().getClass().equals(WhitePiece.class)) {
                                targetRow++;
                                targetColumn1++;
                                inBounds = this.checkIfInBounds(targetRow, targetColumn1);
                                if (inBounds && !tiles[targetRow][targetColumn1].hasPiece()) {
                                    Strike potentialStrike = new Strike(tiles[row][column].getPiece(), targetRow, targetColumn1);
                                    int weight = this.determineWeight(tiles, potentialStrike);
                                    this.addPotentialMove(potentialStrike, weight);
                                }
                            }
                        }

                        targetRow = row + 1;
                        int targetColumn2 = column - 1;

                        inBounds = this.checkIfInBounds(targetRow, targetColumn2);

                        if (inBounds) {
                            if (!tiles[targetRow][targetColumn2].hasPiece()) {
                                Move potentialMove = new Move(tiles[row][column].getPiece(), targetRow, targetColumn2);
                                int weight = determineWeight(tiles, potentialMove);
                                addPotentialMove(potentialMove, weight);
                            } else if (tiles[targetRow][targetColumn2].getPiece().getClass().equals(WhitePiece.class)) {
                                targetRow++;
                                targetColumn2--;
                                inBounds = this.checkIfInBounds(targetRow, targetColumn2);
                                if (inBounds && !tiles[targetRow][targetColumn2].hasPiece()) {
                                    Strike potentialStrike = new Strike(tiles[row][column].getPiece(), targetRow, targetColumn2);
                                    int weight = this.determineWeight(tiles, potentialStrike);
                                    this.addPotentialMove(potentialStrike, weight);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private int determineWeight(Tile[][] tiles, Move potentialMove) {
        if (potentialMove.getClass().equals(Strike.class)) {
            return 100;
        } else {
            return 1;
        }
    }

    private void addPotentialMove(Piece piece, int potentialRow, int potentialColumn, int weight) {
        Move newPotentialMove = new Move(piece, potentialRow, potentialColumn);
        potentialMoves.put(newPotentialMove, weight);
    }

    private void addPotentialMove(Move move, int weight) {
        potentialMoves.put(move, weight);
    }

    private boolean checkIfInBounds(int potentialRow, int potentialColumn) {
        return potentialRow >= 0 && potentialRow <= 9 && potentialColumn >= 0 && potentialColumn <= 9;
    }
}
