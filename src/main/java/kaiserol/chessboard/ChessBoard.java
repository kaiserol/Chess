package kaiserol.chessboard;

import kaiserol.pieces.*;

public class ChessBoard {
    private final Field[][] fields;

    public static void main(String[] args) {
        new ChessBoard();
    }

    public ChessBoard() {
        this.fields = new Field[8][8];
        initBoard();
        printBoard();
    }

    private void initBoard() {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                setField(x, y, new Field(x, y));
                Field field = getField(x, y);

                Side side = y <= 4 ? Side.WHITE : Side.BLACK;
                if (y == 1 || y == 8) {
                    switch (x) {
                        case 1, 8 -> field.put(new Rook(side, this, field));
                        case 2, 7 -> field.put(new Knight(side, this, field));
                        case 3, 6 -> field.put(new Bishop(side, this, field));
                        case 4 -> field.put(new Queen(side, this, field));
                        case 5 -> field.put(new King(side, this, field));
                    }
                } else if (y == 2 || y == 7) {
                    field.put(new Pawn(side, this, field));
                }
            }
        }
    }

    private void printBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ").append("+---".repeat(8)).append("+\n");

        for (int y = 8; y >= 1; y--) {
            builder.append(y).append(" ");

            for (int x = 1; x <= 8; x++) {
                Field field = getField(x, y);
                if (field.isEmpty()) builder.append("|   ");
                else builder.append("| %s ".formatted(field.get().getIdentifier()));
            }
            builder.append("|\n");
        }

        builder.append("  ").append("+---".repeat(8)).append("+\n  ");
        for (int x = 1; x <= 8; x++) builder.append("  %s ".formatted((char) ('a' + (x - 1))));
        builder.append(" \n");

        System.out.println(builder);
    }

    private void setField(int x, int y, Field field) {
        if (x < 1 || x > 8) throw new IllegalArgumentException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new IllegalArgumentException("y must be between 1 and 8");
        this.fields[x - 1][y - 1] = field;
    }

    public Field getField(int x, int y) {
        if (x < 1 || x > 8) throw new IllegalArgumentException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new IllegalArgumentException("y must be between 1 and 8");
        return this.fields[x - 1][y - 1];
    }
}
