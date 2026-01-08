package kaiserol.handler;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.logic.moves.Move;

public class Game {
    private final ChessBoard board;
    private Side currentSide;

    public Game() {
        this.board = new ChessBoard(true);
        this.currentSide = Side.WHITE;
    }

    public void executeMove(String moveString) {
        if (moveString == null || moveString.length() != 4) {
            throw new IllegalArgumentException("Move string must be 4 characters (e.g., a2a4)");
        }

        String fromCoord = moveString.substring(0, 2);
        String toCoord = moveString.substring(2, 4);

        ChessField startField = board.getField(fromCoord);
        ChessField targetField = board.getField(toCoord);

        if (startField.isOccupied()) {
            Piece piece = startField.getPiece();
            if (currentSide == piece.getSide()) {

                // Finds the same move in the piece's list of legal moves'
                for (Move move : piece.getLegalMoves()) {
                    if (move.getTargetField().equals(targetField)) {
                        board.executeMove(move);
                        this.currentSide = currentSide.opposite();
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Invalid move");
    }

    public void undoMove() {
        board.undoMove();
        this.currentSide = currentSide.opposite();
    }

    public void printBoard() {
        board.printBoard();
    }

    public void startGameLoop() {
    }
}
