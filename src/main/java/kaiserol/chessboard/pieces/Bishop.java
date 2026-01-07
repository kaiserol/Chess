package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Bishop extends Piece {

    public Bishop(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        return getDiagonalMoves();
    }

    @Override
    public String getDisplayName() {
        return "Bishop";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'B' : 'b';
    }
}
