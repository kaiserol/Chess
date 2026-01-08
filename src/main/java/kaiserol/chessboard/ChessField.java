package kaiserol.chessboard;

import kaiserol.chessboard.pieces.Piece;
import org.jetbrains.annotations.NotNull;

public class ChessField implements Comparable<ChessField> {
    private final int x;
    private final int y;
    private Piece piece;

    public ChessField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    @Override
    public String toString() {
        char coord1 = (char) ('a' + (x - 1));
        char coord2 = (char) ('1' + (y - 1));
        return coord1 + "" + coord2;
    }

    @Override
    public int compareTo(@NotNull ChessField o) {
        int xComparison = Integer.compare(x, o.x);
        if (xComparison != 0) return xComparison;
        return Integer.compare(y, o.y);
    }
}
