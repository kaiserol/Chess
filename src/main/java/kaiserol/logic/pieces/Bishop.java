package kaiserol.logic.pieces;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Bishop extends Piece {

    public Bishop(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getPseudoLegalMovesHelper() {
        return getDiagonalMoves();
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♗' : '♝';
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'B' : 'b';
    }
}
