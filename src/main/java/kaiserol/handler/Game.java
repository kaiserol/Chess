package kaiserol.handler;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.Stack;

public class Game {
    private final ChessBoard board;
    private final Stack<Move> moveHistory;
    private Side currentSide;

    public Game(ChessBoard board) {
        this.board = board;
        this.moveHistory = new Stack<>();
        this.currentSide = Side.WHITE;
    }

    public void executeMove(Move move) {
        moveHistory.push(move);
        move.execute();
        this.currentSide = currentSide.opposite();
    }

    public void undoMove() {
        Move move = moveHistory.pop();
        move.undo();
        this.currentSide = currentSide.opposite();
    }

    public Move getLastMove() {
        return moveHistory.empty() ? null : moveHistory.peek();
    }
}
