package kaiserol.logic.moves;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.*;

public final class PawnPromotion extends Move {

    private final Pawn pawn;
    private final Piece capturedPiece;
    private Piece promotedPiece;

    public PawnPromotion(Field pawnStart, Field pawnTarget) {
        super(pawnStart, pawnTarget);
        this.pawn = (Pawn) pawnStart.getPiece();
        this.capturedPiece = pawnTarget.getPiece();
        this.promotedPiece = null;
    }

    public Piece setPromotedPiece(Choice promotionChoice) {
        return this.promotedPiece = createPromotedPiece(promotionChoice);
    }

    public Piece createPromotedPiece(Choice promotionChoice) {
        final Side side = pawn.getSide();
        final Board board = pawn.getBoard();

        return switch (promotionChoice) {
            case QUEEN -> new Queen(side, board, null);
            case ROOK -> new Rook(side, board, null);
            case BISHOP -> new Bishop(side, board, null);
            case KNIGHT -> new Knight(side, board, null);
        };
    }

    private Piece waitForPromotionChoice() {
        throw new UnsupportedOperationException("Promotion choice not yet implemented");
    }

    @Override
    public void execute() {
        // Moves the pawn one field forward
        start.removePiece();
        target.setPiece(pawn);

        // Updates the fields
        pawn.setField(target);
        if (capturedPiece != null) {
            capturedPiece.setField(null);
        }

        // Query the promotion piece, if not already set
        if (promotedPiece == null) {
            promotedPiece = waitForPromotionChoice();
        }

        // Promotes the pawn
        target.setPiece(promotedPiece);

        // Updates the target field
        pawn.setField(null);
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
        if (promotedPiece != null) promotedPiece.setField(null);
        if (capturedPiece != null) capturedPiece.setField(target);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }

    public enum Choice {
        QUEEN, ROOK, BISHOP, KNIGHT
    }
}