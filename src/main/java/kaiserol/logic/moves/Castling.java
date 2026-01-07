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
        // Moves the king and the rook
        start.removePiece();
        target.setPiece(king);
        rookStart.removePiece();
        rookTarget.setPiece(rook);

        // Updates the fields
        king.setField(target);
        rook.setField(rookTarget);

        // Increases the moves
        king.increaseMoveCount();
        rook.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the king and the rook back
        target.removePiece();
        start.setPiece(king);
        rookTarget.removePiece();
        rookStart.setPiece(rook);

        // Updates the fields
        king.setField(start);
        rook.setField(rookStart);

        // Decreases the moves
        king.decreaseMoveCount();
        rook.decreaseMoveCount();
    }
}
