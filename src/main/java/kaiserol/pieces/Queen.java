package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Queen extends Piece {

    public Queen(PieceColor color, ChessBoard chessBoard, Field field) {
        super(color, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Queen";
    }

    @Override
    public String getIdentifier() {
        return this.color.isWhite() ? "Q" : "q";
    }
}
