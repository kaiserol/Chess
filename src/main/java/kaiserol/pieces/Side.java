package kaiserol.pieces;

public enum Side {
    WHITE, BLACK;

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public Side opposite() {
        return isWhite() ? BLACK : WHITE;
    }
}
