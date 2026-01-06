package kaiserol.pieces;

public enum PieceColor {
    BLACK(false),
    WHITE(true);

    private final boolean white;

    PieceColor(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isBlack() {
        return !white;
    }

    public PieceColor opposite() {
        return white ? BLACK : WHITE;
    }
}
