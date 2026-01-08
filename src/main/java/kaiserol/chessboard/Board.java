package kaiserol.chessboard;

import kaiserol.chessboard.pieces.*;
import kaiserol.logic.Game;

public class Board {
    private final Field[][] fields;
    private final Game game;

    public Board(boolean initPieces) {
        this.fields = new Field[8][8];
        initFields();
        if (initPieces) initPieces();

        this.game = new Game(this);
    }

    public Board() {
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
                Field field = getField(x, y);
                Side side = y <= 4 ? Side.WHITE : Side.BLACK;

                if (y == 1 || y == 8) {
                    switch (x) {
                        case 1, 8 -> field.setPiece(new Rook(side, this, field));
                        case 2, 7 -> field.setPiece(new Knight(side, this, field));
                        case 3, 6 -> field.setPiece(new Bishop(side, this, field));
                        case 4 -> field.setPiece(new Queen(side, this, field));
                        case 5 -> field.setPiece(new King(side, this, field));
                    }
                } else if (y == 2 || y == 7) {
                    field.setPiece(new Pawn(side, this, field));
                }
            }
        }
    }

    private void initField(int x, int y) {
        if (x < 1 || x > 8) throw new IllegalArgumentException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new IllegalArgumentException("y must be between 1 and 8");
        this.fields[x - 1][y - 1] = new Field(x, y);
    }

    public Field getField(int x, int y) {
        if (x < 1 || x > 8) throw new IllegalArgumentException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new IllegalArgumentException("y must be between 1 and 8");
        return this.fields[x - 1][y - 1];
    }

    public Field getField(String coord) {
        if (coord.length() != 2) throw new IllegalArgumentException("coord must be a 2-character string");
        if (!Character.isLetter(coord.charAt(0))) throw new IllegalArgumentException("coord must start with a letter");
        if (!Character.isDigit(coord.charAt(1))) throw new IllegalArgumentException("coord must end with a number");

        String cleanCoord = coord.toLowerCase();
        return getField(cleanCoord.charAt(0) - 'a' + 1, cleanCoord.charAt(1) - '1' + 1);
    }

    public boolean isOccupiedBySide(Field target, Side side) {
        return target.isOccupied() && target.getPiece().getSide().equals(side);
    }

    public boolean inside(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public void printBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ").append("+---".repeat(8)).append("+\n");

        for (int y = 8; y >= 1; y--) {
            builder.append(y).append(" ");

            for (int x = 1; x <= 8; x++) {
                Field field = getField(x, y);
                if (!field.isOccupied()) builder.append("|   ");
                else builder.append("| %s ".formatted(field.getPiece().getLetter()));
            }
            builder.append("|\n");
        }

        builder.append("  ").append("+---".repeat(8)).append("+\n  ");
        for (int x = 1; x <= 8; x++) builder.append("  %s ".formatted((char) ('a' + (x - 1))));
        builder.append(" ");

        System.out.println(builder);
    }
}
