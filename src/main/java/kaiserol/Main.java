package kaiserol;

import kaiserol.chessboard.ChessBoard;
import kaiserol.logic.moves.Move;
import kaiserol.chessboard.pieces.Piece;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initPieces();
        chessBoard.printBoard();

        String coord = "e5";
        Piece piece = chessBoard.getField(coord).getPiece();
        if (piece == null) {
            System.out.printf("No piece on %s.%n", coord);
            return;
        }

        List<Move> validMoves = piece.getValidMoves();
        System.out.printf("Valid moves for %s on %s:%n%s%n", piece.getLetter(), coord, validMoves);
    }
}
