package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.pieces.King;
import kaiserol.pieces.Rook;

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
        ChessBoard.occupyField(targetField, king);
        ChessBoard.occupyField(rookTargetField, rook);
    }

    @Override
    public void undo() {
        // Puts the rook and king back
        ChessBoard.occupyField(rookStartField, rook);
        ChessBoard.occupyField(startField, king);
    }
}
