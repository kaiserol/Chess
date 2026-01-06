package kaiserol.chessboard.moves;

public abstract sealed class Move permits NormalMove, CastlingMove, EnPassantMove, PawnExchange {
    public abstract void execute();

    public abstract void undo();
}
