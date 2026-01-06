package kaiserol.chessboard.moves;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    public abstract void execute();

    public abstract void undo();
}
