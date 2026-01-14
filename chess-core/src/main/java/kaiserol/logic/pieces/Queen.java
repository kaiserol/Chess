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
    protected List<Move> generatePseudoLegalMoves() {
        List<Move> moves = new ArrayList<>();
        moves.addAll(generateOrthogonalMoves());
        moves.addAll(generateDiagonalMoves());
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
