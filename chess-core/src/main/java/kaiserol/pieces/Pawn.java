package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.*;

import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {
    private static final int START_ROW_WHITE = 2;
    private static final int START_ROW_BLACK = 7;
    private static final int PROMOTION_ROW_WHITE = 8;
    private static final int PROMOTION_ROW_BLACK = 1;

    public Pawn(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> generatePseudoLegalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();
        final int direction = side.isWhite() ? 1 : -1;

        int targetY = fieldY + direction;
        if (targetY < 1 || targetY > 8) return moves;

        // Move one field forward
        ChessField targetField = board.getField(fieldX, targetY);
        if (!targetField.isOccupied()) {
            addMove(moves, targetField);

            // Move two fields forward
            int jumpFieldY = fieldY + 2 * direction;
            if (fieldY == getStartRow(side)) {
                ChessField jumpField = board.getField(fieldX, jumpFieldY);

                if (!jumpField.isOccupied()) {
                    moves.add(new PawnJump(board, field, jumpField));
                }
            }
        }

        // Hit diagonally to the left
        if (ChessBoard.inside(fieldX - 1, targetY)) {
            ChessField captureField = board.getField(fieldX - 1, targetY);
            if (ChessBoard.isOccupiedBySide(captureField, side.opposite())) {
                addMove(moves, captureField);
            }
        }

        // Hit diagonally to the right
        if (ChessBoard.inside(fieldX + 1, targetY)) {
            ChessField captureField = board.getField(fieldX + 1, targetY);
            if (ChessBoard.isOccupiedBySide(captureField, side.opposite())) {
                addMove(moves, captureField);
            }
        }

        // En Passant Target
        ChessField enPassantTarget = board.getEnPassantTarget();
        if (enPassantTarget != null) {
            int enPassantX = enPassantTarget.getX();
            int enPassantY = enPassantTarget.getY();

            if (Math.abs(enPassantX - fieldX) == 1 && targetY == enPassantY) {
                ChessField capturePawnField = board.getField(enPassantX, fieldY);
                moves.add(new EnPassant(board, field, enPassantTarget, capturePawnField));
            }
        }
        return moves;
    }

    private void addMove(List<Move> moves, ChessField targetField) {
        if (targetField.getY() == getPromotionRow(side)) {
            moves.add(new PawnPromotion(board, field, targetField));
        } else {
            moves.add(new NormalMove(board, field, targetField));
        }
    }

    public static int getStartRow(Side side) {
        return side.isWhite() ? START_ROW_WHITE : START_ROW_BLACK;
    }

    public static int getPromotionRow(Side side) {
        return side.isWhite() ? PROMOTION_ROW_WHITE : PROMOTION_ROW_BLACK;
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'P' : 'p';
    }

    @Override
    public char getWhiteSymbol() {
        return '♟';
    }

    @Override
    public char getBlackSymbol() {
        return '♙';
    }
}