package kaiserol.logic.pieces;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Rook extends Piece {

    public Rook(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getPseudoLegalMovesHelper() {
        return getLinearMoves();
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♖' : '♜';
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'R' : 'r';
    }
}
