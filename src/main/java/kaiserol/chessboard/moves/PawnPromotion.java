package kaiserol.chessboard.moves;

import kaiserol.chessboard.Field;
import kaiserol.pieces.Pawn;
import kaiserol.pieces.Piece;

public final class PawnPromotion extends Move {
    private final Field start;
    private final Field target;
    private final Pawn pawn;
    private final Piece promotedPiece;
    private final Piece capturedPiece;

    public PawnPromotion(Pawn pawn, Field target, Piece promotedPiece) {
        this.start = pawn.getField();
        this.target = target;

        this.pawn = pawn;
        this.promotedPiece = promotedPiece;
        this.capturedPiece = target.getPiece();
    }

    @Override
    public void execute() {
        // Moves the pawn and promotes it
        start.removePiece();
        target.setPiece(promotedPiece);

        pawn.setField(null);
        promotedPiece.setField(target);

        // Removes the piece to capture
        if (capturedPiece != null) {
            capturedPiece.setField(null);
        }

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the piece to capture back
        if (capturedPiece != null) {
            target.setPiece(capturedPiece);
            capturedPiece.setField(target);
        } else {
            target.removePiece();
        }

        // Moves the pawn back and removes the promoted piece
        start.setPiece(pawn);
        pawn.setField(start);
        promotedPiece.setField(null);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}
