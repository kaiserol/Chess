package kaiserol.logic.moves;

import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.King;
import kaiserol.chessboard.pieces.Rook;

public final class Castling extends Move {
    private final Field rookStart;
    private final Field rookTarget;

    private final King king;
    private final Rook rook;

    public Castling(Field kingStart, Field kingTarget, Field rookStart, Field rookTarget) {
        super(kingStart, kingTarget);
        this.rookStart = rookStart;
        this.rookTarget = rookTarget;

        this.king = (King) kingStart.getPiece();
        this.rook = (Rook) rookStart.getPiece();
    }

    @Override
    public void execute() {
        // Castles the king with the rook
        unlink(start, king);
        unlink(rookStart, rook);
        link(target, king);
        link(rookTarget, rook);

        // Increases the moves
        king.increaseMoveCount();
        rook.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the rook and king back
        unlink(rookTarget, rook);
        unlink(target, king);
        link(rookStart, rook);
        link(start, king);

        // Decreases the moves
        king.decreaseMoveCount();
        rook.decreaseMoveCount();
    }
}
