package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.*;

import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {

    public Pawn(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getMovesHelper() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();
        final int direction = side.isWhite() ? 1 : -1;

        final int startRow = side.isWhite() ? 2 : 7;
        final int promotionRow = side.isWhite() ? 8 : 1;

        // Moves one field forward
        int targetY = fieldY + direction;
        if (targetY >= 1 && targetY <= 8) {

            ChessField targetField = board.getField(fieldX, targetY);
            if (!targetField.isOccupied()) {
                if (targetY == promotionRow) {
                    moves.add(new PawnPromotion(board, field, targetField));
                } else {
                    moves.add(new NormalMove(board, field, targetField));
                }

                // Moves two fields forward
                int twoTargetY = fieldY + 2 * direction;
                if (fieldY == startRow && twoTargetY >= 1 && twoTargetY <= 8) {
                    ChessField jumpField = board.getField(fieldX, twoTargetY);

                    if (!jumpField.isOccupied()) {
                        moves.add(new PawnJump(board, field, jumpField));
                    }
                }
            }
        }

        // Hits diagonally to the left
        if (board.inside(fieldX - 1, targetY)) {
            ChessField targetField = board.getField(fieldX - 1, targetY);
            if (board.isOccupiedBySide(targetField, side.opposite())) {
                moves.add(new NormalMove(board, field, targetField));
            }
        }

        // Hits diagonally to the right
        if (board.inside(fieldX + 1, targetY)) {
            ChessField targetField = board.getField(fieldX + 1, targetY);
            if (board.isOccupiedBySide(targetField, side.opposite())) {
                moves.add(new NormalMove(board, field, targetField));
            }
        }

        // En Passant
        Move lastMove = board.getGame().getLastMove();
        if (lastMove instanceof PawnJump pawnJump) {
            int lastPawnX = pawnJump.getTargetField().getX();
            int lastPawnY = pawnJump.getTargetField().getY();

            if (fieldY == lastPawnY && Math.abs(lastPawnX - fieldX) == 1) {
                ChessField targetField = board.getField(lastPawnX, targetY);
                moves.add(new EnPassant(board, field, targetField, pawnJump.getTargetField()));
            }
        }

        return moves;
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'P' : 'p';
    }
}