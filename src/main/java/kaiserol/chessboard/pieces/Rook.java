package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Rook extends Piece {

    public Rook(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        return getLinearMoves();
    }

    @Override
    public String getDisplayName() {
        return "Rook";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'R' : 'r';
    }
}
