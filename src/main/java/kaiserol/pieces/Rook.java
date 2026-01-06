package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Rook extends kaiserol.pieces.Piece {

    public Rook(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Rook";
    }

    @Override
    public String getIdentifier() {
        return side.isWhite() ? "R" : "r";
    }
}
