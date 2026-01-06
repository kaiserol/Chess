package kaiserol.chessboard.moves;

import kaiserol.chessboard.Field;
import kaiserol.pieces.Pawn;

public final class EnPassant extends Move {
    private final Pawn pawn;
    private final Field pawnStart;
    private final Field pawnTarget;

    private final Pawn pawnToCapture;
    private final Field pawnToCaptureField;

    public EnPassant(Pawn pawn, Field pawnTarget, Pawn pawnToCapture) {
        this.pawn = pawn;
        this.pawnStart = pawn.getField();
        this.pawnTarget = pawnTarget;

        this.pawnToCapture = pawnToCapture;
        this.pawnToCaptureField = pawnToCapture.getField();
    }

    @Override
    public void execute() {
        // Moves the pawn
        pawnStart.remove();
        pawnTarget.put(pawn);
        pawn.setField(pawnTarget);

        // Removes the pawn to capture
        pawnToCaptureField.remove();
        pawnToCapture.setField(null);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the pawn to capture back
        pawnToCaptureField.put(pawnToCapture);
        pawnToCapture.setField(pawnToCaptureField);

        // Moves the pawn back
        pawnTarget.remove();
        pawnStart.put(pawn);
        pawn.setField(pawnStart);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}
