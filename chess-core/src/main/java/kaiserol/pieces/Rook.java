package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;

import java.util.List;

public final class Rook extends Piece {

    public Rook(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> generatePseudoLegalMoves() {
        return generateOrthogonalMoves();
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'R' : 'r';
    }

    @Override
    public char getWhiteSymbol() {
        return '♜';
    }

    @Override
    public char getBlackSymbol() {
        return '♖';
    }
}
