package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class King extends Piece {

    public King(PieceColor color, ChessBoard chessBoard, Field field) {
        super(color, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "King";
    }

    @Override
    public String getIdentifier() {
        return this.color.isWhite() ? "K" : "k";
    }
}
