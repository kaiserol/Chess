package kaiserol.logic.moves;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.*;

public final class PawnPromotion extends Move {

    private final Pawn attackingPawn;
    private final Piece capturedPiece;
    private Piece promotedPiece;

    public PawnPromotion(Field pawnStart, Field pawnTarget) {
        super(pawnStart, pawnTarget);
        this.attackingPawn = (Pawn) pawnStart.getPiece();
        this.capturedPiece = pawnTarget.getPiece();
        this.promotedPiece = null;
    }

    @Override
    public void execute() {
        // Removes the captured pawn and moves the attacking pawn one field forward
        unlink(target, capturedPiece);
        unlink(start, attackingPawn);
        link(target, attackingPawn);

        // Query the promotion piece, if not already set
        if (promotedPiece == null) {
            promotedPiece = waitForPromotionChoice();
        }

        // Removes the attacking pawn and promotes the pawn
        unlink(target, attackingPawn);
        link(target, promotedPiece);

        // Increases the moves
        attackingPawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Removes the promoted piece
        unlink(target, promotedPiece);

        // Puts the attacking pawn and captured piece back
        link(start, attackingPawn);
        link(target, capturedPiece);

        // Decreases the moves
        attackingPawn.decreaseMoveCount();
    }

    private Piece waitForPromotionChoice() {
        throw new UnsupportedOperationException("Promotion choice not yet implemented");
    }

    public Piece setPromotedPiece(Choice promotionChoice) {
        return this.promotedPiece = createPromotedPiece(promotionChoice);
    }

    public Piece createPromotedPiece(Choice promotionChoice) {
        final Side side = attackingPawn.getSide();
        final Board board = attackingPawn.getBoard();

        return switch (promotionChoice) {
            case QUEEN -> new Queen(side, board, null);
            case ROOK -> new Rook(side, board, null);
            case BISHOP -> new Bishop(side, board, null);
            case KNIGHT -> new Knight(side, board, null);
        };
    }

    public enum Choice {
        QUEEN, ROOK, BISHOP, KNIGHT
    }
}