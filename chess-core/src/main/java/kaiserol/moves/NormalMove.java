package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.pieces.Piece;

public final class NormalMove extends Move {
    private final Piece attackingPiece;
    private final Piece dyingPiece;

    public NormalMove(ChessBoard board, ChessField startField, ChessField targetField) {
        super(board, startField, targetField);
        this.attackingPiece = startField.getPiece();
        this.dyingPiece = targetField.getPiece();
    }

    @Override
    public void execute() {
        // Removes the dying piece and moves the attacking piece
        board.unlink(targetField, dyingPiece);
        board.unlink(startField, attackingPiece);
        board.link(targetField, attackingPiece);
    }

    @Override
    public void undo() {
        // Puts the attacking and dying piece back
        board.unlink(targetField, attackingPiece);
        board.link(startField, attackingPiece);
        board.link(targetField, dyingPiece);
    }
}
