package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.*;

import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {

    public Pawn(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getAllMoves() {
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

            Field target = board.getField(fieldX, targetY);
            if (!target.isOccupied()) {
                if (targetY == promotionRow) {
                    moves.add(new PawnPromotion(field, target));
                } else {
                    moves.add(new NormalMove(field, target));
                }

                // Moves two fields forward
                int twoTargetY = fieldY + 2 * direction;
                if (fieldY == startRow && twoTargetY >= 1 && twoTargetY <= 8) {
                    Field target2 = board.getField(fieldX, twoTargetY);

                    if (!target2.isOccupied()) {
                        moves.add(new PawnJump(field, target2));
                    }
                }
            }
        }

        // Hits diagonally to the left
        if (board.inside(fieldX - 1, targetY)) {
            Field target = board.getField(fieldX - 1, targetY);
            if (board.isOccupiedBySide(target, side.opposite())) {
                moves.add(new NormalMove(field, target));
            }
        }

        // Hits diagonally to the right
        if (board.inside(fieldX + 1, targetY)) {
            Field target = board.getField(fieldX + 1, targetY);
            if (board.isOccupiedBySide(target, side.opposite())) {
                moves.add(new NormalMove(field, target));
            }
        }

        // En Passant
        Move lastMove = board.getGame().getLastMove();
        if (lastMove instanceof PawnJump pawnJump) {
            int enPassantX = pawnJump.getTarget().getX();
            int enPassantY = pawnJump.getTarget().getY();

            if (fieldY == enPassantY && Math.abs(enPassantX - fieldX) == 1) {
                Field target = board.getField(enPassantX, targetY);
                moves.add(new EnPassant(field, target, pawnJump.getTarget()));
            }
        }

        return moves;
    }

    @Override
    public String getDisplayName() {
        return "Pawn";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'P' : 'p';
    }
}