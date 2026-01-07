package kaiserol;

import kaiserol.chessboard.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initPieces();
        chessBoard.printBoard();
    }
}
