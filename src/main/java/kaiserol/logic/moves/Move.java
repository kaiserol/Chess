package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.logic.ChessDetector;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    protected final ChessBoard board;
    protected final ChessField startField;
    protected final ChessField targetField;

    public Move(ChessBoard board, ChessField startField, ChessField targetField) {
        this.board = board;
        this.startField = startField;
        this.targetField = targetField;
    }

    public ChessField getTargetField() {
        return targetField;
    }

    @Override
    public String toString() {
        return targetField.toString();
    }

    public abstract void execute();

    public abstract void undo();

    public boolean isLegal() {
        Piece startingPiece = startField.getPiece();

        execute();
        boolean legal = !ChessDetector.isInCheck(startingPiece.getBoard(), startingPiece.getSide());
        undo();

        return legal;
    }
}