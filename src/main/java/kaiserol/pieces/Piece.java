package kaiserol.pieces;


import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.moves.Move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    protected abstract List<Move> getAllMoves();

    public List<Move> getValidMoves() {
        List<Move> validMoves = new ArrayList<>(getAllMoves());
        validMoves.sort(Comparator.comparing(Move::getTarget));
        return validMoves;
    }

    public abstract String getDisplayName();

    public abstract char getLetter();

    @Override
    public String toString() {
        return getDisplayName();
    }
}