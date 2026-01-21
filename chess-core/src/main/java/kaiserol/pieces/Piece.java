package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.moves.NormalMove;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Piece {
    protected final ChessBoard board;
    protected final Side side;
    protected ChessField field;

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

    private boolean addMoveAndShouldStop(int targetX, int targetY, List<Move> moves) {
        ChessField targetField = board.getField(targetX, targetY);

        // Stop if the target field is occupied by an own piece
        if (board.isOccupiedBySide(targetField, side)) {
            return true;
        }

        // Add the move to the list
        moves.add(new NormalMove(board, field, targetField));

        // Stop if the target field is occupied
        return targetField.isOccupied();
    }

    protected final List<Move> generateOrthogonalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        for (int x = fieldX - 1; x >= 1; x--) if (addMoveAndShouldStop(x, fieldY, moves)) break; // West
        for (int x = fieldX + 1; x <= 8; x++) if (addMoveAndShouldStop(x, fieldY, moves)) break; // East
        for (int y = fieldY - 1; y >= 1; y--) if (addMoveAndShouldStop(fieldX, y, moves)) break; // South
        for (int y = fieldY + 1; y <= 8; y++) if (addMoveAndShouldStop(fieldX, y, moves)) break; // North

        return moves;
    }

    protected final List<Move> generateDiagonalMoves() {
        final List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        final int fieldX = field.getX();
        final int fieldY = field.getY();

        for (int i = 1; fieldX - i >= 1 && fieldY - i >= 1; i++)
            if (addMoveAndShouldStop(fieldX - i, fieldY - i, moves)) break; // South-West
        for (int i = 1; fieldX + i <= 8 && fieldY + i <= 8; i++)
            if (addMoveAndShouldStop(fieldX + i, fieldY + i, moves)) break; // North-East
        for (int i = 1; fieldX - i >= 1 && fieldY + i <= 8; i++)
            if (addMoveAndShouldStop(fieldX - i, fieldY + i, moves)) break; // North-West
        for (int i = 1; fieldX + i <= 8 && fieldY - i >= 1; i++)
            if (addMoveAndShouldStop(fieldX + i, fieldY - i, moves)) break; // South-East

        return moves;
    }

    protected abstract List<Move> generatePseudoLegalMoves();

    public final List<Move> getSortedPseudoLegalMoves() {
        List<Move> moves = new ArrayList<>(generatePseudoLegalMoves());
        moves.sort(Comparator.comparing(Move::getTargetField));
        return moves;
    }

    public final List<Move> getLegalMoves() {
        List<Move> pseudoLegalMoves = getSortedPseudoLegalMoves();
        List<Move> legalMoves = new ArrayList<>();

        for (Move move : pseudoLegalMoves) {
            if (move.isLegal()) legalMoves.add(move);
        }
        return legalMoves;
    }

    public abstract char getSymbol();

    public abstract char getLetter();

    @Override
    public String toString() {
        return String.valueOf(getLetter());
    }
}