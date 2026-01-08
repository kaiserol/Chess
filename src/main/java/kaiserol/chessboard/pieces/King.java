package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class King extends Piece {

    public King(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> getMovesHelper() {
        return List.of();
    }

    @Override
    public char getDisplayName() {
        return side.isWhite() ? 'K' : 'k';
    }
}
