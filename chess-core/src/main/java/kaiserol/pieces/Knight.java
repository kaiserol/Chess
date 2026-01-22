package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.moves.NormalMove;

import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {

    public Knight(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> generatePseudoLegalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        // All possible knight move offsets
        final int[][] offsets = {
                {-1, 2}, {1, 2},   // North
                {2, 1}, {2, -1},   // East
                {1, -2}, {-1, -2}, // South
                {-2, -1}, {-2, 1}  // West
        };

        for (int[] offset : offsets) {
            int targetX = fieldX + offset[0];
            int targetY = fieldY + offset[1];

            if (ChessBoard.inside(targetX, targetY)) {
                ChessField targetField = board.getField(targetX, targetY);
                if (ChessBoard.isOccupiedBySide(targetField, side)) continue;
                moves.add(new NormalMove(board, field, targetField));
            }
        }

        return moves;
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♘' : '♞';
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'N' : 'n';
    }
}
