package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Piece;

public final class NormalMove extends Move {
    private final Piece attackingPiece;
    private final Piece capturedPiece;

    public NormalMove(ChessBoard board, ChessField startField, ChessField targetField) {
        super(board, startField, targetField);
        this.attackingPiece = startField.getPiece();
        this.capturedPiece = targetField.getPiece();
    }

    @Override
    public void execute() {
        // Removes the captured piece and moves the attacking piece
        board.unlink(targetField, capturedPiece);
        board.unlink(startField, attackingPiece);
        board.link(targetField, attackingPiece);

        // Increases the moves
        attackingPiece.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the attacking and captured piece back
        board.unlink(targetField, attackingPiece);
        board.link(startField, attackingPiece);
        board.link(targetField, capturedPiece);

        // Decreases the moves
        attackingPiece.decreaseMoveCount();
    }
}
