package kaiserol.chessboard.moves;

import kaiserol.chessboard.Field;
import kaiserol.pieces.Pawn;
import kaiserol.pieces.Piece;

public final class PawnPromotion extends Move {
    private final Pawn pawn;
    private final Piece capturedPiece;
    private final Piece promotedPiece;

    public PawnPromotion(Field pawnStart, Field pawnTarget, Piece promotedPiece) {
        super(pawnStart, pawnTarget);
        this.pawn = (Pawn) pawnStart.getPiece();
        this.capturedPiece = pawnTarget.getPiece();
        this.promotedPiece = promotedPiece;
    }

    @Override
    public void execute() {
        // Moves the pawn and promotes it
        start.removePiece();
        target.setPiece(promotedPiece);

        // Updates the fields
        pawn.setField(null);
        if (capturedPiece != null) capturedPiece.setField(null);
        promotedPiece.setField(target);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the pawn back and removes the promoted piece
        target.setPiece(capturedPiece);
        start.setPiece(pawn);

        // Updates the fields
        pawn.setField(start);
        promotedPiece.setField(null);
        if (capturedPiece != null) capturedPiece.setField(target);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}
