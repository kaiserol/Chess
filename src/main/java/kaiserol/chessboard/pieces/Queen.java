package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
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
    public char getDisplayName() {
        return side.isWhite() ? 'Q' : 'q';
    }
}
