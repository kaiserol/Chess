package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public abstract class Piece {

    protected final Side side;
    protected final ChessBoard chessBoard;
    protected Field field;
    protected int moveCount;

    public Piece(Side side, ChessBoard chessBoard, Field field) {
        this.side = side;
        this.chessBoard = chessBoard;
        this.field = field;
        this.moveCount = 0;
    }

    public Side getSide() {
        return side;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public abstract String getDisplayName();

    public abstract String getIdentifier();

    @Override
    public String toString() {
        return getDisplayName() + " (" + this.field + ")";
    }
}