package kaiserol.logic.state;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.pieces.Bishop;
import kaiserol.logic.pieces.Knight;
import kaiserol.logic.pieces.Piece;

import java.util.List;
import java.util.Stack;

public class DrawDetector {

    /**
     * Check for insufficient material.
     */
    public static boolean hasInsufficientMaterial(ChessBoard board) {
        List<Piece> whitePieces = board.getPieces(Side.WHITE);
        List<Piece> blackPieces = board.getPieces(Side.BLACK);

        // 1. King vs. King
        if (whitePieces.size() == 1 && blackPieces.size() == 1) {
            return true;
        }

        // 2. King + Bishop/Knight vs. King
        if (whitePieces.size() == 2 && blackPieces.size() == 1) {
            Piece other = board.getNotKing(whitePieces);
            if (other instanceof Bishop || other instanceof Knight) return true;
        }

        // 3. King vs. King + Bishop/Knight
        if (whitePieces.size() == 1 && blackPieces.size() == 2) {
            Piece other = board.getNotKing(blackPieces);
            if (other instanceof Bishop || other instanceof Knight) return true;
        }

        // 4. King + Bishop vs. King + Bishop (same field color)
        if (whitePieces.size() == 2 && blackPieces.size() == 2) {
            Piece whiteOther = board.getNotKing(whitePieces);
            Piece blackOther = board.getNotKing(blackPieces);
            if (whiteOther instanceof Bishop b1 && blackOther instanceof Bishop b2) {
                // Check whether both bishops are on squares of the same color
                return (b1.getField().getX() + b1.getField().getY()) % 2 ==
                        (b2.getField().getX() + b2.getField().getY()) % 2;
            }
        }

        return false;
    }

    /**
     * Check the three-fold position repetition.
     */
    public static boolean isThreefoldRepetition(Stack<BoardSnapshot> boardHistory) {
        if (boardHistory.isEmpty()) return false;

        String currentFen = boardHistory.peek().getFEN();
        int count = 0;

        for (BoardSnapshot snapshot : boardHistory) {
            if (snapshot.getFEN().equals(currentFen)) {
                count++;
            }
        }
        return count >= 3;
    }

    /**
     * Check the 50-move rule.
     */
    public static boolean is50MoveRule(int halfMoveCount) {
        return halfMoveCount >= 100;
    }
}
