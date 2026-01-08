package kaiserol.logic.moves;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    protected final Board board;
    protected final Field start;
    protected final Field target;

    public Move(Board board, Field start, Field target) {
        this.board = board;
        this.start = start;
        this.target = target;
    }

    public Field getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return target.toString();
    }

    public abstract void execute();

    public abstract void undo();
}