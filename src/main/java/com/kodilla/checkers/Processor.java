package com.kodilla.checkers;

import com.kodilla.checkers.move.Move;
import com.kodilla.checkers.move.Strike;
import com.kodilla.checkers.piece.Piece;
import com.kodilla.checkers.piece.WhitePiece;
import com.kodilla.checkers.tile.Tile;

import java.util.Arrays;

public class Processor {
    private final AIPLayer ai = new AIPLayer();

    private Board board;
    private Piece highlightedPiece;
    private boolean isHumanPlayerTurn;
    private boolean gameEnded;
    private boolean playerWon;

    public Board createNewBoard() {
        this.gameEnded = false;
        this.playerWon = false;
        this.isHumanPlayerTurn = true;
        this.board = new Board();
        return this.board;
    }

    public Menu createMenu() {
        return new Menu();
    }

    public void handleClickAction(int clickedRow, int clickedColumn) {
        if (!this.gameEnded) {
            if (this.isHumanPlayerTurn) {
                this.clickDetailsMsg(clickedRow, clickedColumn);

                boolean needToStrike = checkForMandatoryStrikes();

                if (this.isAnyPieceHighlighted()) {
                    if (this.tileHasPiece(clickedRow, clickedColumn)) {
                        if (!this.checkForSamePiece(clickedRow, clickedColumn)) {
                            this.cannotMoveMarkedPieceHereMsg("Collision");
                            this.removeHighlightedPiece();
                        }
                    } else {
                        if (this.clickedTileIsDark(clickedRow, clickedColumn)) {
                            int typeOfMovement = this.determineTypeOfMovement(clickedRow, clickedColumn);

                            if (typeOfMovement == 0 && !needToStrike) {
                                this.movePiece(clickedRow, clickedColumn, null);
                                this.isHumanPlayerTurn = false;
                                this.executeAITurn();
                            } else if (typeOfMovement == 1 && needToStrike) {
                                this.strikePiece(clickedRow, clickedColumn, null);
                                this.isHumanPlayerTurn = false;
                                this.executeAITurn();
                            } else {
                                this.cannotMoveMarkedPieceHereMsg("Not allowed movement.");
                                this.removeHighlightedPiece();
                            }
                        } else {
                            this.cannotMoveMarkedPieceHereMsg("Cannot move the piece to a white tile.");
                            this.removeHighlightedPiece();
                        }
                    }
                } else {
                    if (this.tileHasPiece(clickedRow, clickedColumn)) {
                        if (this.pieceIsWhite(clickedRow, clickedColumn)) {
                            this.highlightNewPiece(clickedRow, clickedColumn);
                            this.highlightedNewPieceMsg();
                        } else {
                            this.wrongPieceClickedMsg();
                        }
                    } else {
                        this.noPieceHasBeenSelectedMsg();
                    }
                }
            } else {
                this.waitForYourTurnMsg();
            }
            this.board.updateBoardsVisual();
        }
    }

    //
    // Boolean checks
    //

    /**
     * Returns 1 when considered movement is a strike.
     * Returns 0 when considered movement is just a move.
     * Returns -1 when considered movement is impossible.
     **/
    private int determineTypeOfMovement(int clickedRow, int clickedColumn) {
        int rowMoveDistance = this.highlightedPiece.getRow() - clickedRow;
        int columnMoveDistance = this.highlightedPiece.getColumn() - clickedColumn;

        int typeOfMovement = -1;

        //for white pieces
        if (highlightedPiece.getClass().equals(WhitePiece.class)) {

            //normal move
            if (rowMoveDistance == 1 && Math.abs(columnMoveDistance) == 1) {
                typeOfMovement = 0;
            }
            //striking
            else if (rowMoveDistance == 2 && Math.abs(columnMoveDistance) == 2) {
                boolean isTherePieceToStrike = checkForOtherPiece(clickedRow, clickedColumn);

                if (isTherePieceToStrike) {
                    typeOfMovement = 1;
                }
            }
        } else {
            //normal move
            if (rowMoveDistance == -1 && Math.abs(columnMoveDistance) == 1) {
                typeOfMovement = 0;
            }
            //striking
            else if (rowMoveDistance == -2 && Math.abs(columnMoveDistance) == 2) {
                boolean isTherePieceToStrike = checkForOtherPiece(clickedRow, clickedColumn);

                if (isTherePieceToStrike) {
                    typeOfMovement = 1;
                }
            }
        }

//        checkDistance(clickedRow, clickedColumn);

        return typeOfMovement;
    }

    private void highlightNewPiece(int row, int column) {
        this.highlightedPiece = this.board.getTiles()[row][column].getPiece();
        this.highlightedPiece.setHighlight(true);
        this.board.getTiles()[row][column].addImage();
    }

    public void executeAITurn() {
        Move aiMove = ai.makeMove(this.board.getTiles());
        if (aiMove == null) {
            this.endGame(true);
        } else {
            this.executeMove(aiMove);
            checkForPossibleMoves();
            checkForMandatoryStrikes();
            this.isHumanPlayerTurn = true;
        }
    }

    private void checkForPossibleMoves() {
        boolean areTherePossibleMoves = false;

        for (int row = 0; row < this.board.getNumberOfRows(); row++) {
            for (int column = 0; column < this.board.getNumberOfColumns(); column++) {
                if (this.tileHasPiece(row, column) && this.pieceIsWhite(row, column)) {

                    int targetRow = row - 1;
                    int targetColumn = column + 1;

                    boolean inBounds = this.targetInBounds(targetRow, targetColumn);

                    if (inBounds) {
                        if (!this.tileHasPiece(targetRow, targetColumn)) {
                            areTherePossibleMoves = true;
                        } else if (!this.pieceIsWhite(targetRow, targetColumn)) {
                            targetRow--;
                            targetColumn++;
                            inBounds = this.targetInBounds(targetRow, targetColumn);
                            if (inBounds && !this.tileHasPiece(targetRow, targetColumn)) {
                                areTherePossibleMoves = true;
                            }
                        }
                    }

                    targetRow = row - 1;
                    targetColumn = column - 1;

                    inBounds = this.targetInBounds(targetRow, targetColumn);

                    if (inBounds) {
                        if (!tileHasPiece(targetRow, targetColumn)) {
                            areTherePossibleMoves = true;
                        } else if (!this.pieceIsWhite(targetRow, targetColumn)) {
                            targetRow--;
                            targetColumn--;
                            inBounds = this.targetInBounds(targetRow, targetColumn);
                            if (inBounds && !this.tileHasPiece(targetRow, targetColumn)) {
                                areTherePossibleMoves = true;
                            }
                        }
                    }
                }
            }
        }
        if (!areTherePossibleMoves) {
            this.endGame(false);
        }
    }


    private void movePiece(int targetRow, int targetColumn, Piece piece) {
        if (piece == null) {
            piece = highlightedPiece;
        }

        int highlightedRow = piece.getRow();
        int highlightedColumn = piece.getColumn();

        Piece pieceToBeMoved = this.board.getTiles()[highlightedRow][highlightedColumn].getPiece();

        pieceToBeMoved.setRow(targetRow);
        pieceToBeMoved.setColumn(targetColumn);

        this.board.getTiles()[highlightedRow][highlightedColumn].removeImage();
        this.board.getTiles()[highlightedRow][highlightedColumn].removePiece();
        this.removeHighlightedPiece();

        this.board.getTiles()[targetRow][targetColumn].addPiece(pieceToBeMoved);
        this.board.getTiles()[targetRow][targetColumn].addImage();

        this.pieceMovedMsg(highlightedRow, highlightedColumn, targetRow, targetColumn);
    }

    private void strikePiece(int targetRow, int targetColumn, Piece piece) {
        if (piece == null) {
            piece = highlightedPiece;
        }
        int rowToStrike = (piece.getRow() + targetRow) / 2;
        int columnToStrike = (piece.getColumn() + targetColumn) / 2;

        this.board.getTiles()[rowToStrike][columnToStrike].removeImage();
        this.board.getTiles()[rowToStrike][columnToStrike].removePiece();

        this.pieceStruckMsg(rowToStrike, columnToStrike);
        this.movePiece(targetRow, targetColumn, piece);

        resetHighlightForMandatoryStrikes();
    }

    private void resetHighlightForMandatoryStrikes() {
        Arrays.stream(this.board.getTiles())
                .flatMap(Arrays::stream)
                .filter(Tile::hasPiece)
                .filter(tile -> tile.getPiece().isMandatoryStrike())
                .forEach(tile -> tile.getPiece().setMandatoryStrike(false));
    }

    private void removeHighlightedPiece() {
        if (this.highlightedPiece != null) {
            this.highlightedPiece.setHighlight(false);
//            this.highlightedPiece.setMandatoryStrike(false);
            this.board.getTiles()[this.highlightedPiece.getRow()][this.highlightedPiece.getColumn()].addImage();
            this.highlightedPiece = null;
        }
    }

    private void executeMove(Move move) {
        if (move.getClass().equals(Strike.class)) {
            this.strikePiece(move.getTargetRow(), move.getTargetColumn(), move.getPieceToBeMoved());
        } else {
            this.movePiece(move.getTargetRow(), move.getTargetColumn(), move.getPieceToBeMoved());
        }
    }

    private void endGame(boolean playerWon) {
        this.gameEnded = true;
        this.isHumanPlayerTurn = false;

        if (playerWon) {
            this.playerWonMsg();
            this.playerWon = true;
        } else {
            this.playerWon = false;
        }
    }

    //
    // Boolean checks
    //

    private boolean pieceIsWhite(int row, int column) {
        return this.board.getTiles()[row][column].getPiece().getClass().equals(WhitePiece.class);
    }

    private boolean isAnyPieceHighlighted() {
        return this.highlightedPiece != null;
    }

    private boolean tileHasPiece(int row, int column) {
        return this.board.getTiles()[row][column].hasPiece();
    }

    private boolean clickedTileIsDark(int row, int column) {
        return !this.board.getTiles()[row][column].isWhite();
    }

    private boolean checkForOtherPiece(int clickedRow, int clickedColumn) {
        int rowToCheck = (this.highlightedPiece.getRow() + clickedRow) / 2;
        int columnToCheck = (this.highlightedPiece.getColumn() + clickedColumn) / 2;

        return !highlightedPiece.getClass().equals(board.getTiles()[rowToCheck][columnToCheck].getPiece().getClass());
    }

    private boolean checkForSamePiece(int clickedRow, int clickedColumn) {
        if (clickedRow == highlightedPiece.getRow() && clickedColumn == highlightedPiece.getColumn()) {
            removeHighlightedPiece();
            highlightingRemovedMsg();
            return true;
        }
        return false;
    }

    private boolean checkForMandatoryStrikes() {
        boolean areThereMandatoryStrikes = false;
        for (int row = 0; row < board.getNumberOfRows(); row++) {
            for (int column = 0; column < board.getNumberOfColumns(); column++) {

                if (this.tileHasPiece(row, column) && this.pieceIsWhite(row, column)) {

                    int targetRow = row - 1;
                    int targetColumn = column - 1;
                    if (this.targetInBounds(targetRow, targetColumn)) {
                        if (this.tileHasPiece(targetRow, targetColumn) && !this.pieceIsWhite(targetRow, targetColumn)) {
                            targetRow--;
                            targetColumn--;
                            if (this.targetInBounds(targetRow, targetColumn) && !this.tileHasPiece(targetRow, targetColumn)) {
                                this.board.getTiles()[row][column].getPiece().setMandatoryStrike(true);
                                this.board.getTiles()[row][column].addImage();
                                areThereMandatoryStrikes = true;
                            }
                        }
                    }

                    targetRow = row - 1;
                    targetColumn = column + 1;
                    if (this.targetInBounds(targetRow, targetColumn)) {
                        if (this.tileHasPiece(targetRow, targetColumn) && !this.pieceIsWhite(targetRow, targetColumn)) {
                            targetRow--;
                            targetColumn++;
                            if (this.targetInBounds(targetRow, targetColumn) && !this.tileHasPiece(targetRow, targetColumn)) {
                                this.board.getTiles()[row][column].getPiece().setMandatoryStrike(true);
                                this.board.getTiles()[row][column].addImage();
                                areThereMandatoryStrikes = true;
                            }
                        }
                    }
                }
            }
        }
        return areThereMandatoryStrikes;
    }

    private boolean targetInBounds(int targetRow, int targetColumn) {
        return targetRow >= 0 && targetRow <= 9 && targetColumn >= 0 && targetColumn <= 9;
    }


    //
    //  Messages
    //

    private void highlightingRemovedMsg() {
        System.out.println("Removed highlighted piece.");
    }

    private void noPieceHasBeenSelectedMsg() {
        System.out.println("No piece has been selected.");
    }

    private void cannotMoveMarkedPieceHereMsg(String reason) {
        System.out.println("Cannot move marked piece here. Reason: " + reason
                + "\nHighlighted piece: " + this.highlightedPieceDetails());
    }

    private void highlightedNewPieceMsg() {
        System.out.println("New piece has been highlighted: " + highlightedPieceDetails());
    }

    private void pieceMovedMsg(int oldRow, int oldColumn, int row, int column) {
        System.out.println("Moved piece from (" + oldRow + "," + oldColumn + ") to (" + row + "," + column + ").");
    }

    private void clickDetailsMsg(int clickedRow, int clickedColumn) {
        System.out.println("Clicked tile (" + clickedRow + "," + clickedColumn + ").");
    }

    private void pieceStruckMsg(int rowStruck, int columnStruck) {
        System.out.println("Piece struck: (" + rowStruck + "," + columnStruck + ")");
    }

    private void wrongPieceClickedMsg() {
        System.out.println("You're playing with white pieces!");
    }

    private void waitForYourTurnMsg() {
        System.out.println("Wait for your turn.");
    }

    private void playerWonMsg() {
        System.out.println("Congratulations! You won!");
    }

    //
    // Others
    //

    private String highlightedPieceDetails() {
        if (this.highlightedPiece != null) {
            return "<Type: " + highlightedPiece.getClass().getName()
                    + ", Position: (" + highlightedPiece.getRow() + "," + highlightedPiece.getColumn() + ")>";
        }
        return "No highlighted pieces.";
    }

    public boolean isGameEnded() {
        return this.gameEnded;
    }

    public boolean didPlayerWon() {
        return this.playerWon;
    }
}
