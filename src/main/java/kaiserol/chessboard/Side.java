package kaiserol.chessboard;

public enum Side {
    WHITE, BLACK;

    public boolean isWhite() {
        return this == WHITE;
    }

    public Side opposite() {
        return isWhite() ? BLACK : WHITE;
    }

    @Override
    public String toString() {
        String firstLetter = this.name().substring(0, 1).toUpperCase();
        String remainingLetters = this.name().substring(1).toLowerCase();
        return firstLetter + remainingLetters;
    }
}
