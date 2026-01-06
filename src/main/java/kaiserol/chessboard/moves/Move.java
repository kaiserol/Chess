package kaiserol.chessboard.moves;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnExchange {
    public abstract void execute();

    public abstract void undo();
}
