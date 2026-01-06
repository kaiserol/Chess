package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Pawn extends Piece {

    public Pawn(PieceColor color, ChessBoard chessBoard, Field field) {
        super(color, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Pawn";
    }

    @Override
    public String getIdentifier() {
        return this.color.isWhite() ? "P" : "p";
    }
}