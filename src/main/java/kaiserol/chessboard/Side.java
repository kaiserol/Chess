package kaiserol.chessboard;

public enum Side {
    WHITE, BLACK;

    public boolean isWhite() {
        return this == WHITE;
    }

    public Side opposite() {
        return isWhite() ? BLACK : WHITE;
    }
}
