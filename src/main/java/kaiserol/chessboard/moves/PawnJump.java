package kaiserol.chessboard.moves;

import kaiserol.chessboard.Field;
import kaiserol.pieces.Pawn;

public final class PawnJump extends Move {
    private final Pawn pawn;
    private final Field pawnStart;
    private final Field pawnTarget;

    public PawnJump(Pawn pawn, Field pawnTarget) {
        this.pawn = pawn;
        this.pawnStart = pawn.getField();
        this.pawnTarget = pawnTarget;
    }

    @Override
    public void execute() {
        // Moves the pawn two fields forward
        pawnStart.remove();
        pawnTarget.put(pawn);
        pawn.setField(pawnTarget);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the pawn back
        pawnTarget.remove();
        pawnStart.put(pawn);
        pawn.setField(pawnStart);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}