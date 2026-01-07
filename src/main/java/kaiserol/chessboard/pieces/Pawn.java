package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Pawn extends Piece {

    public Pawn(Side side, Board board, Field field) {
        super(side, board, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        return List.of();
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