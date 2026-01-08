package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Pawn;

public final class EnPassant extends Move {
    private final ChessField captureField;
    private final Pawn attackingPawn;
    private final Pawn capturedPawn;

    public EnPassant(ChessBoard board, ChessField startField, ChessField targetField, ChessField captureField) {
        super(board, startField, targetField);
        this.captureField = captureField;
        this.attackingPawn = (Pawn) startField.getPiece();
        this.capturedPawn = (Pawn) captureField.getPiece();
    }

    @Override
    public void execute() {
        // Removes the captured pawn and moves the attacking pawn diagonally forward
        board.unlink(captureField, capturedPawn);
        board.unlink(startField, attackingPawn);
        board.link(targetField, attackingPawn);

        // Increases the moves
        attackingPawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the attacking and captured pawn back
        board.unlink(targetField, attackingPawn);
        board.link(startField, attackingPawn);
        board.link(captureField, capturedPawn);

        // Decreases the moves
        attackingPawn.decreaseMoveCount();
    }
}
