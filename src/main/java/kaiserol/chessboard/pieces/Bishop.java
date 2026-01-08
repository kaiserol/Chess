package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Bishop extends Piece {

    public Bishop(Board board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getMovesHelper() {
        return getDiagonalMoves();
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'B' : 'b';
    }
}
