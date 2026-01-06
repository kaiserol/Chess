package kaiserol.pieces;

public enum Side {
    WHITE, BLACK;

    public Side opposite() {
        return (this == WHITE) ? BLACK : WHITE;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }
}
