package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Rook extends Piece {

    public Rook(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
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
