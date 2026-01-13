package kaiserol.logic.pieces;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.ArrayList;
import java.util.List;

public final class Queen extends Piece {

    public Queen(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getPseudoLegalMovesHelper() {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getLinearMoves());
        moves.addAll(getDiagonalMoves());
        return moves;
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♕' : '♛';
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'Q' : 'q';
    }
}
