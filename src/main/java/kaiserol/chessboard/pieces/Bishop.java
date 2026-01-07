package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Bishop extends Piece {

    public Bishop(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
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
