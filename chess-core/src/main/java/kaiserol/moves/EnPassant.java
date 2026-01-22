package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.pieces.Pawn;

public final class EnPassant extends Move {
    private final ChessField capturePawnField;
    private final Pawn attackingPawn;
    private final Pawn dyingPawn;

    public EnPassant(ChessBoard board, ChessField startField, ChessField targetField, ChessField capturePawnField) {
        super(board, startField, targetField);
        this.capturePawnField = capturePawnField;
        this.attackingPawn = (Pawn) startField.getPiece();
        this.dyingPawn = (Pawn) capturePawnField.getPiece();
    }

    @Override
    public void execute() {
        // Moves the attacking pawn diagonally forward and removes the dying pawn
        ChessBoard.occupyField(targetField, attackingPawn);
        ChessBoard.clearField(capturePawnField);
    }

    @Override
    public void undo() {
        // Puts the attacking and dying pawn back
        ChessBoard.occupyField(startField, attackingPawn);
        ChessBoard.occupyField(capturePawnField, dyingPawn);
    }
}
