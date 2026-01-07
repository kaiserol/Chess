package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.ArrayList;
import java.util.List;

public final class Queen extends Piece {

    public Queen(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getLinearMoves());
        moves.addAll(getDiagonalMoves());
        return moves;
    }

    @Override
    public String getDisplayName() {
        return "Queen";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'Q' : 'q';
    }
}
