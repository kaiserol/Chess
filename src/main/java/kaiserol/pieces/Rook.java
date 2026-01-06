package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public final class Rook extends kaiserol.pieces.Piece {

    public Rook(PieceColor color, ChessBoard chessBoard, Field field) {
        super(color, chessBoard, field);
    }

    @Override
    public String getDisplayName() {
        return "Rook";
    }

    @Override
    public String getIdentifier() {
        return this.color.isWhite() ? "R" : "r";
    }
}
