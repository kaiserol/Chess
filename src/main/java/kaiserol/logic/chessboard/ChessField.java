package kaiserol.logic.chessboard;

import kaiserol.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    public boolean has(String coord) {
        return toString().equals(coord);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChessField that = (ChessField) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(@NotNull ChessField o) {
        int xComparison = Integer.compare(x, o.x);
        if (xComparison != 0) return xComparison;
        return Integer.compare(y, o.y);
    }

    @Override
    public String toString() {
        char col = (char) ('a' + (this.x - 1));
        char row = (char) ('1' + (this.y - 1));
        return col + "" + row;
    }
}
