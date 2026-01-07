package kaiserol.logic.moves;

import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Pawn;

public final class PawnJump extends Move {
    private final Pawn pawn;

    public PawnJump(Field pawnStart, Field pawnTarget) {
        super(pawnStart, pawnTarget);
        this.pawn = (Pawn) pawnStart.getPiece();
    }

    @Override
    public void execute() {
        // Moves the pawn two fields forward
        start.removePiece();
        target.setPiece(pawn);

        // Updates the field
        pawn.setField(target);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the pawn back
        target.removePiece();
        start.setPiece(pawn);

        // Updates the field
        pawn.setField(start);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}