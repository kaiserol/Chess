package kaiserol.logic.moves;

import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Piece;

public final class NormalMove extends Move {
    private final Piece attackingPiece;
    private final Piece capturedPiece;

    public NormalMove(Field pieceStart, Field pieceTarget) {
        super(pieceStart, pieceTarget);
        this.attackingPiece = pieceStart.getPiece();
        this.capturedPiece = pieceTarget.getPiece();
    }

    @Override
    public void execute() {
        // Removes the captured piece and moves the attacking piece
        unlink(target, capturedPiece);
        unlink(start, attackingPiece);
        link(target, attackingPiece);

        // Increases the moves
        attackingPiece.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the attacking and captured piece back
        unlink(target, attackingPiece);
        link(start, attackingPiece);
        link(target, capturedPiece);

        // Decreases the moves
        attackingPiece.decreaseMoveCount();
    }
}
