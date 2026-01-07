package kaiserol.logic.moves;

import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Piece;

public final class NormalMove extends Move {
    private final Piece movedPiece;
    private final Piece capturedPiece;

    public NormalMove(Field pieceStart, Field pieceTarget) {
        super(pieceStart, pieceTarget);
        this.movedPiece = pieceStart.getPiece();
        this.capturedPiece = pieceTarget.getPiece();
    }

    @Override
    public void execute() {
        // Moves the piece
        start.removePiece();
        target.setPiece(movedPiece);

        // Updates the fields
        movedPiece.setField(target);
        if (capturedPiece != null) capturedPiece.setField(null);

        // Increases the moves
        movedPiece.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the piece back
        target.setPiece(capturedPiece);
        start.setPiece(movedPiece);

        // Updates the fields
        movedPiece.setField(start);
        if (capturedPiece != null) capturedPiece.setField(target);

        // Decreases the moves
        movedPiece.decreaseMoveCount();
    }
}
