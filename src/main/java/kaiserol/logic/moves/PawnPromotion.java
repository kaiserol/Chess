package kaiserol.logic.moves;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.*;

public final class PawnPromotion extends Move {
    private final Pawn attackingPawn;
    private final Piece capturedPiece;
    private Piece promotedPiece;

    public PawnPromotion(Board board, Field pawnStart, Field pawnTarget) {
        super(board, pawnStart, pawnTarget);
        this.attackingPawn = (Pawn) pawnStart.getPiece();
        this.capturedPiece = pawnTarget.getPiece();
        this.promotedPiece = null;
    }

    @Override
    public void execute() {
        // Removes the captured pawn and moves the attacking pawn one field forward
        board.unlink(target, capturedPiece);
        board.unlink(start, attackingPawn);
        board.link(target, attackingPawn);

        // Query the promotion piece, if not already set
        if (promotedPiece == null) {
            promotedPiece = waitForPromotionChoice();
        }

        // Removes the attacking pawn and promotes the pawn
        board.unlink(target, attackingPawn);
        board.link(target, promotedPiece);

        // Increases the moves
        attackingPawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Removes the promoted piece
        board.unlink(target, promotedPiece);

        // Puts the attacking pawn and captured piece back
        board.link(start, attackingPawn);
        board.link(target, capturedPiece);

        // Decreases the moves
        attackingPawn.decreaseMoveCount();
    }

    private Piece waitForPromotionChoice() {
        throw new UnsupportedOperationException("Promotion choice not yet implemented");
    }

    public Piece choosePromotedPiece(Choice promotionChoice) {
        return this.promotedPiece = createPromotedPiece(promotionChoice);
    }

    public Piece createPromotedPiece(Choice promotionChoice) {
        final Side side = attackingPawn.getSide();
        final Board board = attackingPawn.getBoard();

        return switch (promotionChoice) {
            case QUEEN -> new Queen(board, side);
            case ROOK -> new Rook(board, side);
            case BISHOP -> new Bishop(board, side);
            case KNIGHT -> new Knight(board, side);
        };
    }

    public enum Choice {
        QUEEN, ROOK, BISHOP, KNIGHT
    }
}