package kaiserol.chessboard;

import kaiserol.pieces.Piece;

public class Field {
    private final int x;
    private final int y;
    private Piece piece;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece get() {
        return piece;
    }

    public Piece put(Piece piece) {
        Piece oldPiece = this.piece;
        this.piece = piece;
        return oldPiece;
    }

    public Piece remove() {
        return put(null);
    }

    @Override
    public String toString() {
        char coord1 = (char) ('a' + (x - 1));
        char coord2 = (char) ('1' + (y - 1));
        return coord1 + "" + coord2;
    }
}
