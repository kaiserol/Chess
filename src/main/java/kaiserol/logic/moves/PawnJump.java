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
        unlink(start, pawn);
        link(target, pawn);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the pawn two fields back
        unlink(target, pawn);
        link(start, pawn);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}