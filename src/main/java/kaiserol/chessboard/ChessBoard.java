package kaiserol.chessboard;

import kaiserol.chessboard.pieces.*;
import kaiserol.handler.Game;

public class ChessBoard {
    private final ChessField[][] fields;
    private final Game game;

    public ChessBoard(boolean initPieces) {
        this.fields = new ChessField[8][8];
        initFields();
        if (initPieces) initPieces();

        this.game = new Game(this);
    }

    public ChessBoard() {
        this(false);
    }

    public Game getGame() {
        return game;
    }

    private void initFields() {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                initField(x, y);
            }
        }
    }

    private void initPieces() {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                ChessField field = getField(x, y);
                Side side = y <= 4 ? Side.WHITE : Side.BLACK;

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
                }
            }
        }
    }

    private void initField(int x, int y) {
        if (x < 1 || x > 8) throw new IllegalArgumentException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new IllegalArgumentException("y must be between 1 and 8");
        this.fields[x - 1][y - 1] = new ChessField(x, y);
    }

    public ChessField getField(int x, int y) {
        if (x < 1 || x > 8) throw new IllegalArgumentException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new IllegalArgumentException("y must be between 1 and 8");
        return this.fields[x - 1][y - 1];
    }

    public ChessField getField(String coord) {
        if (coord.length() != 2) throw new IllegalArgumentException("coord must be a 2-character string");
        if (!Character.isLetter(coord.charAt(0))) throw new IllegalArgumentException("coord must start with a letter");
        if (!Character.isDigit(coord.charAt(1))) throw new IllegalArgumentException("coord must end with a number");

        String cleanCoord = coord.toLowerCase();
        return getField(cleanCoord.charAt(0) - 'a' + 1, cleanCoord.charAt(1) - '1' + 1);
    }

    public boolean isOccupiedBySide(ChessField field, Side side) {
        return field.isOccupied() && field.getPiece().getSide() == side;
    }

    public boolean inside(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public void printBoard() {
        System.out.println(this);
    }

    public void link(ChessField field, Piece piece) {
        if (field != null) field.setPiece(piece);
        if (piece != null) piece.setField(field);
    }

    public void unlink(ChessField field, Piece piece) {
        if (field != null) field.removePiece();
        if (piece != null) piece.removeField();
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
                else builder.append("| %s ".formatted(field.getPiece()));
            }
            builder.append("|\n");
        }

        builder.append("  ").append("+---".repeat(8)).append("+\n  ");
        for (int x = 1; x <= 8; x++) builder.append("  %s ".formatted((char) ('a' + (x - 1))));
        builder.append(" ");

        return builder.toString();
    }
}
