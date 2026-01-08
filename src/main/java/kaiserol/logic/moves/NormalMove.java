package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Piece;

public final class NormalMove extends Move {
    private final Piece attackingPiece;
    private final Piece capturedPiece;

    public NormalMove(ChessBoard board, ChessField pieceStart, ChessField pieceTarget) {
        super(board, pieceStart, pieceTarget);
        this.attackingPiece = pieceStart.getPiece();
        this.capturedPiece = pieceTarget.getPiece();
    }

    @Override
    public void execute() {
        // Removes the captured piece and moves the attacking piece
        board.unlink(target, capturedPiece);
        board.unlink(start, attackingPiece);
        board.link(target, attackingPiece);

        // Increases the moves
        attackingPiece.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the attacking and captured piece back
        board.unlink(target, attackingPiece);
        board.link(start, attackingPiece);
        board.link(target, capturedPiece);

        // Decreases the moves
        attackingPiece.decreaseMoveCount();
    }
}
