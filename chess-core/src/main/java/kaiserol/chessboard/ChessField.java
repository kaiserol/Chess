package kaiserol.chessboard;

import kaiserol.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

public class ChessField implements Comparable<ChessField> {
    private final int x;
    private final int y;
    private Piece piece;

    public enum SortOrder {X_ASC, X_DESC, Y_ASC, Y_DESC}

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
    public String toString() {
        char col = (char) ('a' + (this.x - 1));
        char row = (char) ('1' + (this.y - 1));
        return col + "" + row;
    }

    @Override
    public int compareTo(@NotNull ChessField o) {
        return comparator(SortOrder.X_ASC, SortOrder.Y_ASC).compare(this, o);
    }

    public static Comparator<ChessField> comparator(SortOrder... orders) {
        Comparator<ChessField> comp = null;
        for (SortOrder order : orders) {
            Comparator<ChessField> next = switch (order) {
                case X_ASC -> Comparator.comparingInt(ChessField::getX);
                case X_DESC -> Comparator.comparingInt(ChessField::getX).reversed();
                case Y_ASC -> Comparator.comparingInt(ChessField::getY);
                case Y_DESC -> Comparator.comparingInt(ChessField::getY).reversed();
            };

            comp = (comp == null) ? next : comp.thenComparing(next);
        }

        // Default: a1 bis h8
        return comp != null ? comp : Comparator.comparingInt(ChessField::getX).thenComparingInt(ChessField::getY);
    }
}
