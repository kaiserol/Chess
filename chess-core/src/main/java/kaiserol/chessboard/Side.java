package kaiserol.chessboard;

public enum Side {
    WHITE("White"),
    BLACK("Black");

    private final String text;

    Side(String text) {
        this.text = text;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public Side opposite() {
        return isWhite() ? BLACK : WHITE;
    }

    @Override
    public String toString() {
        return text;
    }
}
