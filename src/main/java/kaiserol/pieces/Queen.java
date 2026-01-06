package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.moves.Move;

import java.util.ArrayList;
import java.util.List;

public final class Queen extends Piece {

    public Queen(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        List<Move> moves = new ArrayList<>();
        moves.addAll(Move.getLinearMoves(this));
        moves.addAll(Move.getDiagonalMoves(this));
        return moves;
    }

    @Override
    public String getDisplayName() {
        return "Queen";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'Q' : 'q';
    }
}
