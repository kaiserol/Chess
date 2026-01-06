package kaiserol.chessboard.moves;

import kaiserol.chessboard.Field;
import kaiserol.pieces.King;
import kaiserol.pieces.Rook;

public final class Castling extends Move {
    private final King king;
    private final Field kingStart;
    private final Field kingTarget;

    private final Rook rook;
    private final Field rookStart;
    private final Field rookTarget;

    public Castling(King king, Field kingTarget, Rook rook, Field rookTarget) {
        this.king = king;
        this.kingStart = king.getField();
        this.kingTarget = kingTarget;

        this.rook = rook;
        this.rookStart = rook.getField();
        this.rookTarget = rookTarget;
    }

    @Override
    public void execute() {
        // Moves the king
        kingStart.removePiece();
        kingTarget.setPiece(king);
        king.setField(kingTarget);

        // Moves the rook
        rookStart.removePiece();
        rookTarget.setPiece(rook);
        rook.setField(rookTarget);

        // Increases the moves
        king.increaseMoveCount();
        rook.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the king back
        rookTarget.removePiece();
        kingStart.setPiece(king);
        king.setField(kingStart);

        // Moves the rook back
        rookTarget.removePiece();
        rookStart.setPiece(rook);
        king.setField(rookStart);

        // Decreases the moves
        king.decreaseMoveCount();
        rook.decreaseMoveCount();
    }
}
