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
    public char getSymbol() {
        return !side.isWhite() ? '♖' : '♜';
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'R' : 'r';
    }
}
