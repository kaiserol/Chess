package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class King extends Piece {

    public King(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "King";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'K' : 'k';
    }
}
