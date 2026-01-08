package kaiserol;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.PawnJump;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(true);

        Field field = board.getField("a2");
        Piece piece = field.getPiece();
        List<Move> moves = piece.getValidMoves();

        System.out.printf("%d Valid moves from %s (%s): %s%n", moves.size(), field, piece.getLetter(), moves);
        board.printBoard();

        board.getGame().executeMove(new PawnJump(board.getField("a2"), board.getField("a4")));

        System.out.println("\nAfter first move:");
        board.printBoard();
    }
}
