package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.List;

public final class Pawn extends Piece {

    public Pawn(Side side, ChessBoard chessBoard, Field field) {
        super(side, chessBoard, field);
    }

    @Override
    protected List<Move> getAllMoves() {
        return List.of();
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