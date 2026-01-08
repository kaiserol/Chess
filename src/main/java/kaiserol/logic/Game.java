package kaiserol.logic;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;

import java.util.Stack;

public class Game {
    private final ChessBoard board;
    private final Stack<Move> moves;
    private Side currentSide;

    public Game(ChessBoard board) {
        this.board = board;
        this.moves = new Stack<>();
        this.currentSide = Side.WHITE;
    }

    public void executeMove(Move move) {
        moves.push(move);
        move.execute();
        this.currentSide = currentSide.opposite();
    }

    public void undoMove() {
        Move move = moves.pop();
        move.undo();
        this.currentSide = currentSide.opposite();
    }

    public Move getLastMove() {
        return moves.empty() ? null : moves.peek();
    }
}
