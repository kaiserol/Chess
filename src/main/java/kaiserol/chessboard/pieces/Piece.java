package kaiserol.chessboard.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.logic.ChessDetector;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Piece {
    protected final ChessBoard board;
    protected final Side side;
    protected ChessField field;
    protected int moveCount;

    public Piece(ChessBoard board, Side side) {
        this.board = board;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public ChessField getField() {
        return field;
    }

    public void setField(ChessField field) {
        this.field = field;
    }

    public void removeField() {
        this.field = null;
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

    private boolean breakWhileAddingMove(int targetX, int targetY, List<Move> moves) {
        ChessField targetField = board.getField(targetX, targetY);
        if (board.isOccupiedBySide(targetField, side)) return true;

        moves.add(new NormalMove(board, field, targetField));
        return targetField.isOccupied();
    }

    protected final List<Move> getLinearMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        for (int x = fieldX - 1; x >= 1; x--) if (breakWhileAddingMove(x, fieldY, moves)) break; // West
        for (int x = fieldX + 1; x <= 8; x++) if (breakWhileAddingMove(x, fieldY, moves)) break; // East
        for (int y = fieldY - 1; y >= 1; y--) if (breakWhileAddingMove(fieldX, y, moves)) break; // South
        for (int y = fieldY + 1; y <= 8; y++) if (breakWhileAddingMove(fieldX, y, moves)) break; // North

        return moves;
    }

    protected final List<Move> getDiagonalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        for (int i = 1; fieldX - i >= 1 && fieldY - i >= 1; i++)
            if (breakWhileAddingMove(fieldX - i, fieldY - i, moves)) break; // South-West
        for (int i = 1; fieldX + i <= 8 && fieldY + i <= 8; i++)
            if (breakWhileAddingMove(fieldX + i, fieldY + i, moves)) break; // North-East
        for (int i = 1; fieldX - i >= 1 && fieldY + i <= 8; i++)
            if (breakWhileAddingMove(fieldX - i, fieldY + i, moves)) break; // North-West
        for (int i = 1; fieldX + i <= 8 && fieldY - i >= 1; i++)
            if (breakWhileAddingMove(fieldX + i, fieldY - i, moves)) break; // South-East

        return moves;
    }

    protected abstract List<Move> getPseudoLegalMovesHelper();

    public final List<Move> getPseudoLegalMoves() {
        List<Move> moves = new ArrayList<>(getPseudoLegalMovesHelper());
        moves.sort(Comparator.comparing(Move::getTargetField));
        return moves;
    }

    public final List<Move> getLegalMoves() {
        List<Move> pseudoLegalMoves = getPseudoLegalMoves();
        List<Move> legalMoves = new ArrayList<>();

        for (Move move : pseudoLegalMoves) {
            move.execute();

            boolean isInCheck = ChessDetector.isInCheck(board, side);
            if (!isInCheck) legalMoves.add(move);

            move.undo();
        }

        legalMoves.sort(Comparator.comparing(Move::getTargetField));
        return legalMoves;
    }

    public abstract char getSymbol();

    public abstract char getDisplayName();

    @Override
    public String toString() {
        return String.valueOf(getDisplayName());
    }
}