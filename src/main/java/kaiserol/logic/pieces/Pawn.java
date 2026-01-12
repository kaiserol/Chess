package kaiserol.logic.pieces;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.*;

import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {
    private final int START_ROW;
    private final int PROMOTION_ROW;

    public Pawn(ChessBoard board, Side side) {
        super(board, side);
        this.START_ROW = side.isWhite() ? 2 : 7;
        this.PROMOTION_ROW = side.isWhite() ? 8 : 1;
    }

    @Override
    protected List<Move> getPseudoLegalMovesHelper() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();
        final int direction = side.isWhite() ? 1 : -1;

        // Moves one field forward
        int targetY = fieldY + direction;
        if (targetY >= 1 && targetY <= 8) {
            ChessField targetField = board.getField(fieldX, targetY);
            if (!targetField.isOccupied()) {
                addNormalMove(moves, targetField);

                // Moves two fields forward
                int jumpFieldY = fieldY + 2 * direction;
                if (fieldY == START_ROW) {
                    ChessField jumpField = board.getField(fieldX, jumpFieldY);

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
                addNormalMove(moves, targetField);
            }
        }

        // Hits diagonally to the right
        if (board.inside(fieldX + 1, targetY)) {
            ChessField targetField = board.getField(fieldX + 1, targetY);
            if (board.isOccupiedBySide(targetField, side.opposite())) {
                addNormalMove(moves, targetField);
            }
        }

        // En Passant
        Move lastMove = board.getLastMove();
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

    private void addNormalMove(List<Move> moves, ChessField targetField ) {
        if (targetField.getY() == PROMOTION_ROW) {
            moves.add(new PawnPromotion(board, field, targetField));
        } else {
            moves.add(new NormalMove(board, field, targetField));
        }
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♙' : '♟';
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'P' : 'p';
    }
}