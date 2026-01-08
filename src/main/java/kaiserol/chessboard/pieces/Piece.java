package kaiserol.chessboard.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Piece {

    protected final Side side;
    protected final Board board;
    protected Field field;
    protected int moveCount;

    public Piece(Side side, Board board, Field field) {
        this.side = side;
        this.board = board;
        this.field = field;
        this.moveCount = 0;
    }

    public Side getSide() {
        return side;
    }

    public Board getBoard() {
        return board;
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

    private boolean addMoveAndStop(int targetX, int targetY, List<Move> moves) {
        Field target = board.getField(targetX, targetY);
        if (board.isOccupiedBySide(target, side)) return true;

        moves.add(new NormalMove(field, target));
        return target.isOccupied();
    }

    protected List<Move> getLinearMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        for (int x = fieldX - 1; x >= 1; x--) if (addMoveAndStop(x, fieldY, moves)) break; // West
        for (int x = fieldX + 1; x <= 8; x++) if (addMoveAndStop(x, fieldY, moves)) break; // East
        for (int y = fieldY - 1; y >= 1; y--) if (addMoveAndStop(fieldX, y, moves)) break; // South
        for (int y = fieldY + 1; y <= 8; y++) if (addMoveAndStop(fieldX, y, moves)) break; // North

        return moves;
    }

    protected List<Move> getDiagonalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        for (int i = 1; fieldX - i >= 1 && fieldY - i >= 1; i++)
            if (addMoveAndStop(fieldX - i, fieldY - i, moves)) break; // South-West
        for (int i = 1; fieldX + i <= 8 && fieldY + i <= 8; i++)
            if (addMoveAndStop(fieldX + i, fieldY + i, moves)) break; // North-East
        for (int i = 1; fieldX - i >= 1 && fieldY + i <= 8; i++)
            if (addMoveAndStop(fieldX - i, fieldY + i, moves)) break; // North-West
        for (int i = 1; fieldX + i <= 8 && fieldY - i >= 1; i++)
            if (addMoveAndStop(fieldX + i, fieldY - i, moves)) break; // South-East

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
        return String.valueOf(getLetter());
    }
}