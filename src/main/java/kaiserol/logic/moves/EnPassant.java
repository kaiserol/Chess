package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Pawn;

public final class EnPassant extends Move {
    private final ChessField capturedPawnStart;
    private final Pawn attackingPawn;
    private final Pawn capturedPawn;

    public EnPassant(ChessBoard board, ChessField pawnStart, ChessField pawnTarget, ChessField capturedPawnField) {
        super(board, pawnStart, pawnTarget);
        this.capturedPawnStart = capturedPawnField;
        this.attackingPawn = (Pawn) pawnStart.getPiece();
        this.capturedPawn = (Pawn) capturedPawnField.getPiece();
    }

    @Override
    public void execute() {
        // Removes the captured pawn and moves the attacking pawn diagonally
        board.unlink(capturedPawnStart, capturedPawn);
        board.unlink(start, attackingPawn);
        board.link(target, attackingPawn);

        // Increases the moves
        attackingPawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the attacking and captured pawn back
        board.unlink(target, attackingPawn);
        board.link(start, attackingPawn);
        board.link(capturedPawnStart, capturedPawn);

        // Decreases the moves
        attackingPawn.decreaseMoveCount();
    }
}
