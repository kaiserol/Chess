package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Knight extends Piece {

    public Knight(PieceColor color, ChessBoard chessBoard, Field field) {
        super(color, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Knight";
    }

    @Override
    public String getIdentifier() {
        return color.isWhite() ? "N" : "n";
    }
}
