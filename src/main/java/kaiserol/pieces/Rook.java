package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.moves.Move;

import java.util.List;

public final class Rook extends kaiserol.pieces.Piece {

    public Rook(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        return Move.getLinearMoves(this);
    }

    @Override
    public String getDisplayName() {
        return "Rook";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'R' : 'r';
    }
}
