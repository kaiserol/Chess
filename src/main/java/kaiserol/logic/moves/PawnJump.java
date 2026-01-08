package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Pawn;

public final class PawnJump extends Move {
    private final Pawn pawn;

    public PawnJump(ChessBoard board, ChessField startField, ChessField targetField) {
        super(board, startField, targetField);
        this.pawn = (Pawn) startField.getPiece();
    }

    @Override
    public void execute() {
        // Moves the pawn two fields forward
        board.unlink(startField, pawn);
        board.link(targetField, pawn);

        // Increases the moves
        pawn.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the pawn back
        board.unlink(targetField, pawn);
        board.link(startField, pawn);

        // Decreases the moves
        pawn.decreaseMoveCount();
    }
}