package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Knight extends Piece {

    public Knight(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Knight";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'N' : 'n';
    }
}
