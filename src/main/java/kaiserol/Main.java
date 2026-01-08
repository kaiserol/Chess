package kaiserol;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.PawnJump;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard(true);

        ChessField field = board.getField("a2");
        Piece piece = field.getPiece();
        List<Move> moves = piece.getMoves();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), field, piece, moves);
        board.printBoard();

        board.getGame().executeMove(new PawnJump(board, board.getField("a2"), board.getField("a4")));

        System.out.println("\nAfter first move:");
        board.printBoard();
    }
}
