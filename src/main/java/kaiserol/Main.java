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

        Field field = board.getField("e4");
        if (!field.isOccupied()) System.out.printf("No piece on %s.%n", field);
        else {
            Piece piece = field.getPiece();
            List<Move> validMoves = piece.getValidMoves();
            System.out.printf("Valid moves for %s on %s:%n%s%n", piece.getLetter(), field, validMoves);
        }

        board.getGame().executeMove(new NormalMove(board.getField("b1"), board.getField("c3")));
        board.printBoard();
    }
}
