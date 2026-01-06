package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Bishop extends Piece {

    public Bishop(PieceColor color, ChessBoard chessBoard, Field field) {
        super(color, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Bishop";
    }

    @Override
    public String getIdentifier() {
        return this.color.isWhite() ? "B" : "b";
    }
}
