package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;

public abstract class Piece {

    protected final PieceColor color;
    protected final ChessBoard chessBoard;
    protected Field field;
    protected int moveCount;

    public Piece(PieceColor color, ChessBoard chessBoard, Field field) {
        this.color = color;
        this.chessBoard = chessBoard;
        this.field = field;
        this.moveCount = 0;
    }

    public PieceColor getColor() {
        return color;
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
        return this.moveCount;
    }

    public void increaseMoveCount() {
        this.moveCount++;
    }

    public void decreaseMoveCount() {
        this.moveCount--;
    }

    public abstract String getDisplayName();

    public abstract String getIdentifier();

    @Override
    public String toString() {
        return getDisplayName() + " (" + this.field + ")";
    }
}