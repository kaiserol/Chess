package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.pieces.*;

public final class PawnPromotion extends Move {
    private final Pawn attackingPawn;
    private final Piece dyingPiece;
    private Piece promotingPiece;

    public PawnPromotion(ChessBoard board, ChessField startField, ChessField targetField) {
        super(board, startField, targetField);
        this.attackingPawn = (Pawn) startField.getPiece();
        this.dyingPiece = targetField.getPiece();
    }

    public PawnPromotion(ChessBoard board, ChessField startField, ChessField targetField, Choice promotionChoice) {
        this(board, startField, targetField);
        this.promotingPiece = createPromotingPiece(promotionChoice);
    }

    @Override
    public void execute() {
        // Removes the dying piece and moves the attacking pawn one field forward
        board.unlink(targetField, dyingPiece);
        board.unlink(startField, attackingPawn);
        board.link(targetField, attackingPawn);

        // Set the promoting piece, if not already set
        setPromotingPiece();

        // Removes the attacking pawn and promotes the pawn
        board.unlink(targetField, attackingPawn);
        board.link(targetField, promotingPiece);
    }

    @Override
    public void undo() {
        // Removes the promoting piece
        board.unlink(targetField, promotingPiece);

        // Puts the attacking pawn and dying piece back
        board.link(startField, attackingPawn);
        board.link(targetField, dyingPiece);
    }

    @Override
    public boolean isLegal() {
        Piece temp = promotingPiece;
        if (promotingPiece == null) {
            promotingPiece = createPromotingPiece(Choice.QUEEN);
        }

        boolean legal = super.isLegal();
        promotingPiece = temp;
        return legal;
    }

    public Piece getPromotingPiece() {
        return promotingPiece;
    }

    private void setPromotingPiece() {
        if (promotingPiece != null) return;

        // Get the provider from the board
        PawnPromotionProvider provider = board.getPromotionProvider();

        // Wait for the promotion choice from the user
        Choice choice = (provider != null) ? provider.getPromotionChoice() : Choice.QUEEN;
        promotingPiece = createPromotingPiece(choice);
    }

    private Piece createPromotingPiece(Choice promotionChoice) {
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