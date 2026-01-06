package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Queen extends Piece {

    public Queen(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Queen";
    }

    @Override
    public String getIdentifier() {
        return side.isWhite() ? "Q" : "q";
    }
}
