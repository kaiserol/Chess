package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Pawn extends Piece {

    public Pawn(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Pawn";
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'P' : 'p';
    }
}