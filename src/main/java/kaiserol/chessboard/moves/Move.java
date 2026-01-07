package kaiserol.chessboard.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.pieces.Piece;
import kaiserol.pieces.Side;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class Move permits NormalMove, Castling, EnPassant, PawnJump, PawnPromotion {
    protected final Field start;
    protected final Field target;

    public Move(Field start, Field target) {
        this.start = start;
        this.target = target;
    }

    public Field getStart() {
        return start;
    }

    public Field getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getStart() + "->" + getTarget();
    }

    public abstract void execute();

    public abstract void undo();

    public static List<Move> getLinearMoves(Piece piece) {
        final ChessBoard chessBoard = piece.getChessBoard();
        final List<Move> moves = new ArrayList<>();

        final Side side = piece.getSide();
        final Field start = piece.getField();
        final int startX = start.getX();
        final int startY = start.getY();

        // Moves towards the north
        for (int y = startY + 1; y <= 8; y++) {
            if (chessBoard.isOccupiedBySide(startX, y, side)) break;
            Field target = chessBoard.getField(startX, y);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the east
        for (int x = startX + 1; x <= 8; x++) {
            if (chessBoard.isOccupiedBySide(x, startY, side)) break;
            Field target = chessBoard.getField(x, startY);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the south
        for (int y = startY - 1; y >= 1; y--) {
            if (chessBoard.isOccupiedBySide(startX, y, side)) break;
            Field target = chessBoard.getField(startX, y);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the west
        for (int x = startX - 1; x >= 1; x--) {
            if (chessBoard.isOccupiedBySide(x, startY, side)) break;
            Field target = chessBoard.getField(x, startY);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        return moves;
    }

    public static List<Move> getDiagonalMoves(Piece piece) {
        final ChessBoard chessBoard = piece.getChessBoard();
        final List<Move> moves = new ArrayList<>();

        final Side side = piece.getSide();
        final Field start = piece.getField();
        final int startX = start.getX();
        final int startY = start.getY();

        // Moves towards the north-east
        for (int i = 1; startX + i <= 8 && startY + i <= 8; i++) {
            if (chessBoard.isOccupiedBySide(startX + i, startY + i, side)) break;
            Field target = chessBoard.getField(startX + i, startY + i);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the south-east
        for (int i = 1; startX + i <= 8 && startY - i >= 1; i++) {
            if (chessBoard.isOccupiedBySide(startX + i, startY - i, side)) break;
            Field target = chessBoard.getField(startX + i, startY - i);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the south-west
        for (int i = 1; startX - i >= 1 && startY - i >= 1; i++) {
            if (chessBoard.isOccupiedBySide(startX - i, startY - i, side)) break;
            Field target = chessBoard.getField(startX - i, startY - i);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the north-west
        for (int i = 1; startX - i >= 1 && startY + i <= 8; i++) {
            if (chessBoard.isOccupiedBySide(startX - i, startY + i, side)) break;
            Field target = chessBoard.getField(startX - i, startY + i);
            moves.add(new NormalMove(start, target));
            if (target.isOccupied()) break;
        }

        return moves;
    }
}