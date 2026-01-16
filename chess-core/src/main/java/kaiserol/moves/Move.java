package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.pieces.Piece;
import kaiserol.state.CheckDetector;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    protected final ChessBoard board;
    protected final ChessField startField;
    protected final ChessField targetField;

    public Move(ChessBoard board, ChessField startField, ChessField targetField) {
        this.board = board;
        this.startField = startField;
        this.targetField = targetField;
    }

    public ChessField getStartField() {
        return startField;
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
        Piece movingPiece = startField.getPiece();

        execute();
        boolean isInCheck = CheckDetector.isInCheck(movingPiece.getBoard(), movingPiece.getSide());
        undo();

        return !isInCheck;
    }
}