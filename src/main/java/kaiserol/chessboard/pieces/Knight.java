package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {

    public Knight(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
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
                Field target = chessBoard.getField(targetX, targetY);
                if (chessBoard.isOccupiedBySide(target, side)) continue;
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
