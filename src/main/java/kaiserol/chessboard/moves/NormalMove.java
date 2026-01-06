package kaiserol.chessboard.moves;

import kaiserol.chessboard.Field;
import kaiserol.pieces.Piece;

public final class NormalMove extends Move {
    private final Field start;
    private final Field target;
    private final Piece movedPiece;
    private final Piece capturedPiece;

    public NormalMove(Field start, Field target) {
        this.start = start;
        this.target = target;
        this.movedPiece = start.get();
        this.capturedPiece = target.get();
    }

    @Override
    public void execute() {
        // Moves the piece
        start.remove();
        target.put(movedPiece);
        movedPiece.setField(target);

        // Removes the piece to capture
        capturedPiece.setField(null);

        // Increases the moves
        movedPiece.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the piece to capture back
        target.put(capturedPiece);
        capturedPiece.setField(target);

        // Moves the piece back
        start.put(movedPiece);
        movedPiece.setField(start);

        // Decreases the moves
        movedPiece.decreaseMoveCount();
    }
}
