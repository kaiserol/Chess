package kaiserol.state;

public final class CastlingRights {
    private boolean whiteCastleKingSide;
    private boolean whiteCastleQueenSide;
    private boolean blackCastleKingSide;
    private boolean blackCastleQueenSide;

    public CastlingRights(boolean whiteCastleKingSide, boolean whiteCastleQueenSide, boolean blackCastleKingSide, boolean blackCastleQueenSide) {
        this.whiteCastleKingSide = whiteCastleKingSide;
        this.whiteCastleQueenSide = whiteCastleQueenSide;
        this.blackCastleKingSide = blackCastleKingSide;
        this.blackCastleQueenSide = blackCastleQueenSide;
    }

    public CastlingRights() {
        this(true, true, true, true);
    }

    public boolean canWhiteCastleKingSide() {
        return whiteCastleKingSide;
    }

    public boolean canWhiteCastleQueenSide() {
        return whiteCastleQueenSide;
    }

    public boolean canBlackCastleKingSide() {
        return blackCastleKingSide;
    }

    public boolean canBlackCastleQueenSide() {
        return blackCastleQueenSide;
    }

    public void revokeWhiteCastleKingSide() {
        this.whiteCastleKingSide = false;
    }

    public void revokeWhiteCastleQueenSide() {
        this.whiteCastleQueenSide = false;
    }

    public void revokeBlackCastleKingSide() {
        this.blackCastleKingSide = false;
    }

    public void revokeBlackCastleQueenSide() {
        this.blackCastleQueenSide = false;
    }

    public void revokeWhiteCastle() {
        this.whiteCastleKingSide = false;
        this.whiteCastleQueenSide = false;
    }

    public void revokeBlackCastle() {
        this.blackCastleKingSide = false;
        this.blackCastleQueenSide = false;
    }

    public CastlingRights copy() {
        return new CastlingRights(
                this.whiteCastleKingSide,
                this.whiteCastleQueenSide,
                this.blackCastleKingSide,
                this.blackCastleQueenSide
        );
    }
}