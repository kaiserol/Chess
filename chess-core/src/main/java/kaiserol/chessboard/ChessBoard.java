package kaiserol.chessboard;

import kaiserol.moves.Move;
import kaiserol.moves.PawnPromotionProvider;
import kaiserol.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ChessBoard {
    private final ChessField[][] fields;
    private final Stack<Move> moveHistory;
    private PawnPromotionProvider pawnPromotionProvider;

    public ChessBoard() {
        this.fields = new ChessField[8][8];
        initializeFields();

        // History stacks
        this.moveHistory = new Stack<>();
    }

    public PawnPromotionProvider getPromotionProvider() {
        return pawnPromotionProvider;
    }

    public void setPromotionProvider(PawnPromotionProvider provider) {
        this.pawnPromotionProvider = provider;
    }

    public void executeMove(Move move) {
        if (move == null) return;
        move.execute();
        moveHistory.push(move);
    }

    public void undoMove() {
        if (moveHistory.empty()) return;
        Move move = moveHistory.pop();
        move.undo();
    }

    public Move getLastMove() {
        return moveHistory.empty() ? null : moveHistory.peek();
    }

    private void initializeFields() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                initializeField(x, y);
            }
        }
    }

    public void initializePieces() {
        this.moveHistory.clear();
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

    private void initializeField(int x, int y) {
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

    public boolean inside(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public ChessField getField(String coord) {
        if (coord == null) {
            throw new CoordinateException("Coordinate must not be null.");
        }

        String c = coord.trim().toLowerCase();
        if (c.length() != 2) {
            throw new CoordinateException("Invalid coordinate '%s'. Expected <column><row> (e.g. a1, h8).".formatted(coord));
        }

        char col = c.charAt(0);
        char row = c.charAt(1);

        if (col < 'a' || col > 'h') {
            throw new CoordinateException("Invalid coordinate '%s': column '%s' is out of range (a–h).".formatted(coord, col));
        }

        if (row < '1' || row > '8') {
            throw new CoordinateException("Invalid coordinate '%s': row '%s' is out of range (1–8).".formatted(coord, row));
        }

        int x = col - 'a' + 1;
        int y = row - '1' + 1;
        return getField(x, y);
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

    public List<Piece> getPieces(Side side) {
        List<Piece> pieces = new ArrayList<>();
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                ChessField field = getField(x, y);
                if (isOccupiedBySide(field, side)) {
                    pieces.add(field.getPiece());
                }
            }
        }
        return pieces;
    }

    public King getKing(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof King king) {
                return king;
            }
        }
        return null;
    }

    public Piece getNotKing(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (!(piece instanceof King)) {
                return piece;
            }
        }
        return null;
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
