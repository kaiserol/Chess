package kaiserol.logic.chessboard;

import kaiserol.logic.moves.Move;
import kaiserol.logic.pieces.*;

import java.util.Stack;

public class ChessBoard {
    private final ChessField[][] fields;
    private final Stack<Move> moveHistory;

    public ChessBoard() {
        this.fields = new ChessField[8][8];
        initFields();

        this.moveHistory = new Stack<>();
    }

    private void initFields() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                initField(x, y);
            }
        }
    }

    private void initField(int x, int y) {
        checkCoordinates(x, y);
        this.fields[x - 1][y - 1] = new ChessField(x, y);
    }

    public ChessField getField(int x, int y) {
        checkCoordinates(x, y);
        return this.fields[x - 1][y - 1];
    }

    private void checkCoordinates(int x, int y) {
        if (x < 1 || x > 8) throw new CoordinateException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new CoordinateException("y must be between 1 and 8");
    }

    public ChessField getField(String coord) {
        if (coord.length() != 2) throw new CoordinateException("coord must be a 2-character string");
        if (!Character.isLetter(coord.charAt(0))) throw new CoordinateException("coord must start with a letter");
        if (!Character.isDigit(coord.charAt(1))) throw new CoordinateException("coord must end with a number");

        String cleanCoord = coord.toLowerCase();
        int x = cleanCoord.charAt(0) - 'a' + 1;
        int y = cleanCoord.charAt(1) - '1' + 1;
        return getField(x, y);
    }

    public void executeMove(Move move) {
        if (move == null) return;
        moveHistory.push(move);
        move.execute();
    }

    public void undoMove() {
        if (moveHistory.empty()) return;
        Move move = moveHistory.pop();
        move.undo();
    }

    public Move getLastMove() {
        return moveHistory.empty() ? null : moveHistory.peek();
    }

    public void setInitialStartingPositions() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                ChessField field = getField(x, y);
                Side side = y <= 2 ? Side.WHITE : y <= 6 ? null : Side.BLACK;

                if (y == 1 || y == 8) {
                    switch (x) {
                        case 1, 8 -> link(field, new Rook(this, side));
                        case 2, 7 -> link(field, new Knight(this, side));
                        case 3, 6 -> link(field, new Bishop(this, side));
                        case 4 -> link(field, new Queen(this, side));
                        case 5 -> link(field, new King(this, side));
                    }
                } else if (y == 2 || y == 7) {
                    link(field, new Pawn(this, side));
                } else {
                    if (field.isOccupied()) {
                        unlink(field, field.getPiece());
                    }
                }
            }
        }
    }

    public boolean inside(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public void link(ChessField field, Piece piece) {
        if (field != null) field.setPiece(piece);
        if (piece != null) piece.setField(field);
    }

    public void unlink(ChessField field, Piece piece) {
        if (field != null) field.removePiece();
        if (piece != null) piece.removeField();
    }

    public boolean isOccupiedBySide(ChessField field, Side side) {
        return field.isOccupied() && field.getPiece().getSide() == side;
    }

    public void toConsole() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ").append("+---".repeat(8)).append("+\n");

        for (int y = 8; y >= 1; y--) {
            builder.append(y).append(" ");

            for (int x = 1; x <= 8; x++) {
                ChessField field = getField(x, y);
                if (!field.isOccupied()) builder.append("|   ");
                else builder.append("| %s ".formatted(field.getPiece().getSymbol()));
            }
            builder.append("|\n");
        }

        builder.append("  ").append("+---".repeat(8)).append("+\n  ");
        for (int x = 1; x <= 8; x++) {
            builder.append("  %s ".formatted((char) ('a' + (x - 1))));
        }
        builder.append(" ");

        return builder.toString();
    }
}
