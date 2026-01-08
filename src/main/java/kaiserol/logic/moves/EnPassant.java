package kaiserol.logic.moves;

import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Pawn;

public final class EnPassant extends Move {
    private final Field capturedPawnStart;

    private final Pawn attackingPawn;
    private final Pawn capturedPawn;

    public EnPassant(Field pawnStart, Field pawnTarget, Field capturedPawnField) {
        super(pawnStart, pawnTarget);
        this.capturedPawnStart = capturedPawnField;

        this.attackingPawn = (Pawn) pawnStart.getPiece();
        this.capturedPawn = (Pawn) capturedPawnField.getPiece();
    }

    @Override
    public void execute() {
        // Removes the captured pawn and moves the attacking pawn diagonally
        unlink(capturedPawnStart, capturedPawn);
        unlink(start, attackingPawn);
        link(target, attackingPawn);

        // Increases the moves
        attackingPawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the attacking and captured pawn back
        unlink(target, attackingPawn);
        link(start, attackingPawn);
        link(capturedPawnStart, capturedPawn);

        // Decreases the moves
        attackingPawn.decreaseMoveCount();
    }
}
