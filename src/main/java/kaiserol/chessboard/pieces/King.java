package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class King extends Piece {

    public King(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getMovesHelper() {
        return List.of();
    }

    @Override
    public String getDisplayName() {
        return "King";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'K' : 'k';
    }
}
