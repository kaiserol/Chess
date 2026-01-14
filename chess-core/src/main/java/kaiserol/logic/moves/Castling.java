package kaiserol.logic.moves;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.pieces.King;
import kaiserol.logic.pieces.Rook;

public final class Castling extends Move {
    private final ChessField rookStartField;
    private final ChessField rookTargetField;
    private final King king;
    private final Rook rook;

    public Castling(ChessBoard board, ChessField startField, ChessField targetField, ChessField rookStartField, ChessField rookTargetField) {
        super(board, startField, targetField);
        this.rookStartField = rookStartField;
        this.rookTargetField = rookTargetField;
        this.king = (King) startField.getPiece();
        this.rook = (Rook) rookStartField.getPiece();
    }

    @Override
    public void execute() {
        // Castles the king with the rook
        board.unlink(startField, king);
        board.unlink(rookStartField, rook);
        board.link(targetField, king);
        board.link(rookTargetField, rook);

        // Increases the moves
        king.increaseMoveCount();
        rook.increaseMoveCount();
    }

    @Override
    public void undo() {
        // Puts the rook and king back
        board.unlink(rookTargetField, rook);
        board.unlink(targetField, king);
        board.link(rookStartField, rook);
        board.link(startField, king);

        // Decreases the moves
        king.decreaseMoveCount();
        rook.decreaseMoveCount();
    }
}
