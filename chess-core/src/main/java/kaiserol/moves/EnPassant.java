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
        // Removes the dying pawn and moves the attacking pawn diagonally forward
        board.unlink(capturePawnField, dyingPawn);
        board.unlink(startField, attackingPawn);
        board.link(targetField, attackingPawn);
    }

    @Override
    public void undo() {
        // Puts the attacking and dying pawn back
        board.unlink(targetField, attackingPawn);
        board.link(startField, attackingPawn);
        board.link(capturePawnField, dyingPawn);
    }
}
