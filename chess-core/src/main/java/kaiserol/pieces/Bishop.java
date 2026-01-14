package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;

import java.util.List;

public final class Bishop extends Piece {

    public Bishop(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> generatePseudoLegalMoves() {
        return generateDiagonalMoves();
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♗' : '♝';
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'B' : 'b';
    }
}
