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
        // Moves the attacking piece and removes the dying piece
        ChessBoard.occupyField(targetField, attackingPiece);
    }

    @Override
    public void undo() {
        // Puts the attacking and dying piece back
        ChessBoard.occupyField(startField, attackingPiece);
        ChessBoard.occupyField(targetField, dyingPiece);
    }
}
