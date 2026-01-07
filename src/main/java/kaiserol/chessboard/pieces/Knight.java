package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {

    public Knight(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        final List<Move> moves = new ArrayList<>();
        final int startX = field.getX();
        final int startY = field.getY();

        // All possible knight move offsets
        final int[][] offsets = {
                {-1, 2}, {1, 2},   // North
                {2, 1}, {2, -1},   // East
                {1, -2}, {-1, -2}, // South
                {-2, -1}, {-2, 1}  // West
        };

        for (int[] offset : offsets) {
            int targetX = startX + offset[0];
            int targetY = startY + offset[1];

            if (targetX >= 1 && targetX <= 8 && targetY >= 1 && targetY <= 8) {
                Field target = board.getField(targetX, targetY);
                if (board.isOccupiedBySide(target, side)) continue;
                moves.add(new NormalMove(field, target));
            }
        }

        return moves;
    }

    @Override
    public String getDisplayName() {
        return "Knight";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'N' : 'n';
    }
}
