package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.pieces.*;

public final class PawnPromotion extends Move {
    private final Pawn attackingPawn;
    private final Piece capturedPiece;
    private Piece promotedPiece;

    public PawnPromotion(ChessBoard board, ChessField startField, ChessField targetField) {
        super(board, startField, targetField);
        this.attackingPawn = (Pawn) startField.getPiece();
        this.capturedPiece = targetField.getPiece();
    }

    public PawnPromotion(ChessBoard board, ChessField startField, ChessField targetField, Choice promotionChoice) {
        this(board, startField, targetField);
        this.promotedPiece = createPromotedPiece(promotionChoice);
    }

    public Piece getPromotedPiece() {
        return promotedPiece;
    }

    @Override
    public void execute() {
        // Removes the captured pawn and moves the attacking pawn one field forward
        board.unlink(targetField, capturedPiece);
        board.unlink(startField, attackingPawn);
        board.link(targetField, attackingPawn);

        // Query the promotion piece, if not already set
        choosePromotedPiece();

        // Removes the attacking pawn and promotes the pawn
        board.unlink(targetField, attackingPawn);
        board.link(targetField, promotedPiece);

        // Increases the moves
        attackingPawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Removes the promoted piece
        board.unlink(targetField, promotedPiece);

        // Puts the attacking pawn and captured piece back
        board.link(startField, attackingPawn);
        board.link(targetField, capturedPiece);

        // Decreases the moves
        attackingPawn.decreaseMoveCount();
    }

    @Override
    public boolean isLegal() {
        Piece oldPromotedPiece = this.promotedPiece;
        if (this.promotedPiece == null) {
            this.promotedPiece = createPromotedPiece(Choice.QUEEN);
        }

        boolean legal = super.isLegal();
        this.promotedPiece = oldPromotedPiece;
        return legal;
    }

    private void choosePromotedPiece() {
        if (this.promotedPiece != null) return;

        // Get the provider from the board
        PawnPromotionProvider provider = board.getPromotionProvider();

        // Wait for the promotion choice from the user
        Choice choice = (provider != null) ? provider.getPromotionChoice() : Choice.QUEEN;
        this.promotedPiece = createPromotedPiece(choice);
    }

    private Piece createPromotedPiece(Choice promotionChoice) {
        final Side side = attackingPawn.getSide();
        final ChessBoard board = attackingPawn.getBoard();

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