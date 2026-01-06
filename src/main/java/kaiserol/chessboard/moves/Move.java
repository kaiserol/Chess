package kaiserol.chessboard.moves;

public abstract sealed class Move permits NormalMove, CastlingMove, EnPassantMove, PawnJump, PawnExchange {
    public abstract void execute();

    public abstract void undo();
}
