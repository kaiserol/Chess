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
    protected List<Move> getMovesHelper() {
        List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        int fieldX = field.getX();
        int fieldY = field.getY();

        // Normal moves
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
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
        if (moveCount == 0 && !ChessDetector.isInCheck(board, side)) {
            // Long (Queenside)
            checkCastling(moves, 1, fieldX - 1, fieldX - 2);
            // Short (Kingside)
            checkCastling(moves, 8, fieldX + 1, fieldX + 2);
        }

        return moves;
    }

    private void checkCastling(List<Move> moves, int rookX, int... kingStepX) {
        int fieldY = field.getY();
        ChessField rookStartField = board.getField(rookX, fieldY);

        if (board.isOccupiedBySide(rookStartField, side) && rookStartField.getPiece() instanceof Rook rook) {
            if (rook.getMoveCount() == 0) return;

            // Check whether the fields between the king and the rook are empty
            int startX = Math.min(field.getX(), rookX) + 1;
            int endX = Math.max(field.getX(), rookX) - 1;
            for (int tx = startX; tx <= endX; tx++) {
                if (board.getField(tx, fieldY).isOccupied()) return;
            }

            // Check whether the king moves over attacked squares
            if (kingStepX == null || kingStepX.length != 2) return;
            for (int kx : kingStepX) {
                if (ChessDetector.isFieldAttacked(board, board.getField(kx, fieldY), side.opposite())) return;
            }

            // Everything is fine, add castling
            ChessField kingTargetField = board.getField(kingStepX[1], fieldY);
            ChessField rookTargetField = board.getField(kingStepX[0], fieldY);
            moves.add(new Castling(board, field, kingTargetField, rookStartField, rookTargetField));
        }
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'K' : 'k';
    }
}
