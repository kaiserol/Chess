package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;

import java.util.ArrayList;
import java.util.List;

public final class Queen extends Piece {

    public Queen(ChessBoard board, Side side) {
        super(board, side);
    }

    @Override
    protected List<Move> generatePseudoLegalMoves() {
        List<Move> moves = new ArrayList<>();
        moves.addAll(generateOrthogonalMoves());
        moves.addAll(generateDiagonalMoves());
        return moves;
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'Q' : 'q';
    }

    @Override
    public char getWhiteSymbol() {
        return '♛';
    }

    @Override
    public char getBlackSymbol() {
        return '♕';
    }
}
