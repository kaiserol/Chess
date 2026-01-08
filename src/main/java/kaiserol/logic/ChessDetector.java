package kaiserol.logic;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.King;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.logic.moves.Move;

import java.util.List;

public class ChessDetector {

    public static boolean isInCheck(ChessBoard board, Side side) {
        ChessField kingField = findKingField(board, side);
        if (kingField == null) throw new IllegalStateException("King not found!");

        return isFieldAttacked(board, kingField, side.opposite());
    }

    public static boolean isFieldAttacked(ChessBoard board, ChessField field, Side side) {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                ChessField currentField = board.getField(x, y);
                if (board.isOccupiedBySide(currentField, side)) {
                    Piece piece = currentField.getPiece();

                    // Checks pseudolegal moves of the piece
                    List<Move> pseudoLegalMoves = piece.getMoves();
                    for (Move move : pseudoLegalMoves) {
                        if (move.getTargetField().equals(field)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static ChessField findKingField(ChessBoard board, Side side) {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                ChessField field = board.getField(x, y);
                if (board.isOccupiedBySide(field, side)) {
                    Piece piece = field.getPiece();
                    if (piece instanceof King) {
                        return field;
                    }
                }
            }
        }
        return null;
    }
}
