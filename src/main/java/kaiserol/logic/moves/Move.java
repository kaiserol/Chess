package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    protected final ChessBoard board;
    protected final ChessField start;
    protected final ChessField target;

    public Move(ChessBoard board, ChessField start, ChessField target) {
        this.board = board;
        this.start = start;
        this.target = target;
    }

    public ChessField getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return target.toString();
    }

    public abstract void execute();

    public abstract void undo();
}