package kaiserol.logic.moves;

import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Pawn;

public final class EnPassant extends Move {
    private final Field capturedPawnStart;

    private final Pawn pawn;
    private final Pawn capturedPawn;

    public EnPassant(Field pawnStart, Field pawnTarget, Field capturedPawnField) {
        super(pawnStart, pawnTarget);
        this.capturedPawnStart = capturedPawnField;

        this.pawn = (Pawn) pawnStart.getPiece();
        this.capturedPawn = (Pawn) capturedPawnField.getPiece();
    }

    @Override
    public void execute() {
        // Moves the pawn diagonally
        start.removePiece();
        target.setPiece(pawn);
        capturedPawnStart.removePiece();

        // Updates the fields
        pawn.setField(target);
        capturedPawn.setField(null);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the pawn back diagonally
        target.removePiece();
        start.setPiece(pawn);
        capturedPawnStart.setPiece(capturedPawn);

        // Updates the fields
        pawn.setField(start);
        capturedPawn.setField(capturedPawnStart);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}
