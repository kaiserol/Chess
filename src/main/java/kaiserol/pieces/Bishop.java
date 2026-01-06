package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Bishop extends Piece {

    public Bishop(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Bishop";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'B' : 'b';
    }
}
