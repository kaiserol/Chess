package kaiserol.state;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.pieces.Bishop;
import kaiserol.pieces.Knight;
import kaiserol.pieces.Piece;

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

        // For threefold repetition, we only care about:
        // 1. Piece placement
        // 2. Active player
        // 3. Castling availability
        // 4. En passant target square
        // We ignore halfMoveCount and fullMoveCount.

        String currentFen = boardHistory.peek().getFEN();
        String currentBasePosition = getPositionalFEN(currentFen);
        int count = 0;

        for (BoardSnapshot snapshot : boardHistory) {
            String fen = snapshot.getFEN();
            String basePosition = getPositionalFEN(fen);
            if (basePosition.equals(currentBasePosition)) {
                count++;
            }
        }
        return count >= 3;
    }

    private static String getPositionalFEN(String fen) {
        String[] parts = fen.split(" ");
        if (parts.length < 4) return fen;

        // FEN fields: 0=placement, 1=active, 2=castling, 3=enpassant, 4=halfMove, 5=fullMove
        return parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
    }

    /**
     * Check the 50-move rule.
     */
    public static boolean is50MoveRule(int halfMoveCount) {
        return halfMoveCount >= 100;
    }
}
