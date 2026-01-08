package kaiserol;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.NormalMove;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(true);
        board.printBoard();

        Field field = board.getField("a2");
        if (!field.isOccupied()) System.out.printf("No piece on %s.%n", field);
        else {
            Piece piece = field.getPiece();
            List<Move> validMoves = piece.getValidMoves();
            System.out.printf("Valid moves from %s (%s): %s%n", field, piece.getLetter(), validMoves);
        }

        board.getGame().executeMove(new NormalMove(board.getField("b1"), board.getField("c3")));

        System.out.println("\nAfter first move:");
        board.printBoard();
    }
}
