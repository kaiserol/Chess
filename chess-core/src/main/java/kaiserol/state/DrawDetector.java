package kaiserol.state;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.pieces.Bishop;
import kaiserol.pieces.King;
import kaiserol.pieces.Knight;
import kaiserol.pieces.Piece;

import java.util.List;

public class DrawDetector {

    /**
     * Check for insufficient material.
     */
    public static boolean hasInsufficientMaterial(ChessBoard board) {
        List<Piece> whitePieces = board.getPieces(Side.WHITE);
        List<Piece> blackPieces = board.getPieces(Side.BLACK);

        King whiteKing = board.getKing(whitePieces);
        King blackKing = board.getKing(blackPieces);

        // Check if both kings are available
        if (whiteKing == null || blackKing == null) return false;

        // 1. King vs. King
        if (whitePieces.size() == 1 && blackPieces.size() == 1) {
            return true;
        }

        // 2. King + Bishop/Knight vs. King
        if (whitePieces.size() == 2 && blackPieces.size() == 1) {
            Piece whitePiece = board.getNotKing(whitePieces);
            if (whitePiece instanceof Bishop || whitePiece instanceof Knight) return true;
        }

        // 3. King vs. King + Bishop/Knight
        if (whitePieces.size() == 1 && blackPieces.size() == 2) {
            Piece blackPiece = board.getNotKing(blackPieces);
            if (blackPiece instanceof Bishop || blackPiece instanceof Knight) return true;
        }

        // 4. King + Bishop vs. King + Bishop (same field color)
        if (whitePieces.size() == 2 && blackPieces.size() == 2) {
            Piece whitePiece = board.getNotKing(whitePieces);
            Piece blackPiece = board.getNotKing(blackPieces);
            if (whitePiece instanceof Bishop whiteBishop && blackPiece instanceof Bishop blackBishop) {
                // Check whether both bishops are on fields of the same color
                return (whiteBishop.getField().getX() + whiteBishop.getField().getY()) % 2 ==
                        (blackBishop.getField().getX() + blackBishop.getField().getY()) % 2;
            }
        }

        return false;
    }

    /**
     * Check the three-fold position repetition.
     */
    public static boolean isThreefoldRepetition(BoardHistory boardHistory) {
        return boardHistory.countRepetitions() >= 3;
    }

    /**
     * Check the 50-move rule.
     */
    public static boolean is50MoveRule(int halfMoveCount) {
        return halfMoveCount >= 100;
    }
}
