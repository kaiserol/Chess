package kaiserol.logic.moves;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Pawn;

public final class PawnJump extends Move {
    private final Pawn pawn;

    public PawnJump(Board board, Field pawnStart, Field pawnTarget) {
        super(board, pawnStart, pawnTarget);
        this.pawn = (Pawn) pawnStart.getPiece();
    }

    @Override
    public void execute() {
        // Moves the pawn two fields forward
        board.unlink(start, pawn);
        board.link(target, pawn);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Moves the pawn two fields back
        board.unlink(target, pawn);
        board.link(start, pawn);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}