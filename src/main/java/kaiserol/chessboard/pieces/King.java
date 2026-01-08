package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.logic.ChessDetector;
import kaiserol.logic.moves.Castling;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.ArrayList;
import java.util.List;

public final class King extends Piece {

    public King(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getPseudoLegalMovesHelper() {
        List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        int fieldX = field.getX();
        int fieldY = field.getY();

        // Normal moves
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) continue;

                int targetX = fieldX + x;
                int targetY = fieldY + y;

                if (board.inside(targetX, targetY)) {
                    ChessField targetField = board.getField(targetX, targetY);
                    if (!board.isOccupiedBySide(targetField, side)) {
                        moves.add(new NormalMove(board, field, targetField));
                    }
                }
            }
        }

        // Castling
        if (moveCount == 0) {
            // Long (Queenside)
            checkCastling(moves, 1, true);
            // Short (Kingside)
            checkCastling(moves, 8, false);
        }

        return moves;
    }

    private void checkCastling(List<Move> moves, int rookX, boolean queenside) {
        int fieldX = field.getX();
        int fieldY = field.getY();
        ChessField rookStartField = board.getField(rookX, fieldY);

        if (board.isOccupiedBySide(rookStartField, side) && rookStartField.getPiece() instanceof Rook rook) {
            if (rook.getMoveCount() != 0) return;

            // Checks whether the fields between the king and the rook are empty
            int startX = Math.min(field.getX(), rookX) + 1;
            int endX = Math.max(field.getX(), rookX) - 1;
            for (int tx = startX; tx <= endX; tx++) {
                if (board.getField(tx, fieldY).isOccupied()) return;
            }

            // Checks whether the king is in check or moves over attacked squares
            int direction = queenside ? -1 : 1;
            for (int i = 0; i <= 2; i++) {
                int targetX = fieldX + direction * i;
                if (ChessDetector.isFieldAttacked(board, board.getField(targetX, fieldY), side.opposite())) return;
            }

            // Everything is fine, add castling
            ChessField kingTargetField = board.getField(fieldX + direction * 2, fieldY);
            ChessField rookTargetField = board.getField(fieldX + direction, fieldY);
            moves.add(new Castling(board, field, kingTargetField, rookStartField, rookTargetField));
        }
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'K' : 'k';
    }
}
