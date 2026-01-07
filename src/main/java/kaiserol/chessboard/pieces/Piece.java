package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Piece {

    protected final Side side;
    protected final ChessBoard chessBoard;
    protected Field field;
    protected int moveCount;

    public Piece(Side side, ChessBoard chessBoard, Field field) {
        this.side = side;
        this.chessBoard = chessBoard;
        this.field = field;
        this.moveCount = 0;
    }

    public Side getSide() {
        return side;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    protected List<Move> getLinearMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int startX = field.getX();
        final int startY = field.getY();

        // Moves towards the north
        for (int y = startY + 1; y <= 8; y++) {
            if (chessBoard.isOccupiedBySide(startX, y, side)) break;
            Field target = chessBoard.getField(startX, y);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the east
        for (int x = startX + 1; x <= 8; x++) {
            if (chessBoard.isOccupiedBySide(x, startY, side)) break;
            Field target = chessBoard.getField(x, startY);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the south
        for (int y = startY - 1; y >= 1; y--) {
            if (chessBoard.isOccupiedBySide(startX, y, side)) break;
            Field target = chessBoard.getField(startX, y);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the west
        for (int x = startX - 1; x >= 1; x--) {
            if (chessBoard.isOccupiedBySide(x, startY, side)) break;
            Field target = chessBoard.getField(x, startY);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        return moves;
    }

    protected List<Move> getDiagonalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int startX = field.getX();
        final int startY = field.getY();

        // Moves towards the north-east
        for (int i = 1; startX + i <= 8 && startY + i <= 8; i++) {
            if (chessBoard.isOccupiedBySide(startX + i, startY + i, side)) break;
            Field target = chessBoard.getField(startX + i, startY + i);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the south-east
        for (int i = 1; startX + i <= 8 && startY - i >= 1; i++) {
            if (chessBoard.isOccupiedBySide(startX + i, startY - i, side)) break;
            Field target = chessBoard.getField(startX + i, startY - i);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the south-west
        for (int i = 1; startX - i >= 1 && startY - i >= 1; i++) {
            if (chessBoard.isOccupiedBySide(startX - i, startY - i, side)) break;
            Field target = chessBoard.getField(startX - i, startY - i);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        // Moves towards the north-west
        for (int i = 1; startX - i >= 1 && startY + i <= 8; i++) {
            if (chessBoard.isOccupiedBySide(startX - i, startY + i, side)) break;
            Field target = chessBoard.getField(startX - i, startY + i);
            moves.add(new NormalMove(field, target));
            if (target.isOccupied()) break;
        }

        return moves;
    }

    protected abstract List<Move> getAllMoves();

    public List<Move> getValidMoves() {
        List<Move> validMoves = new ArrayList<>(getAllMoves());
        validMoves.sort(Comparator.comparing(Move::getTarget));
        return validMoves;
    }

    public abstract String getDisplayName();

    public abstract char getLetter();

    @Override
    public String toString() {
        return getDisplayName();
    }
}