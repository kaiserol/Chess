package kaiserol.logic.moves;

import kaiserol.chessboard.Field;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    protected final Field start;
    protected final Field target;

    public Move(Field start, Field target) {
        this.start = start;
        this.target = target;
    }

    public Field getStart() {
        return start;
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