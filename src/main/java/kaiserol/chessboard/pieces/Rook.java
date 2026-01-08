package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Rook extends Piece {

    public Rook(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getMovesHelper() {
        return getLinearMoves();
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'R' : 'r';
    }
}
