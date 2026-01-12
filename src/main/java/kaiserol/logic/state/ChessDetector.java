package kaiserol.logic.state;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.pieces.King;
import kaiserol.logic.pieces.Piece;

import java.util.List;

public class ChessDetector {

    public static boolean isInCheck(ChessBoard board, Side side) {
        King king = findKing(board, side);
        if (king == null) throw new IllegalStateException("King not found!");

        return isFieldAttacked(board, king.getField(), side.opposite());
    }

    private static King findKing(ChessBoard board, Side kingSide) {
        List<Piece> pieces = board.getPieces(kingSide);
        for (Piece piece : pieces) {
            if (piece instanceof King king) {
                return king;
            }
        }
        return null;
    }

    public static boolean isFieldAttacked(ChessBoard board, ChessField field, Side attackerSide) {
        List<Piece> attackers = board.getPieces(attackerSide);
        for (Piece attacker : attackers) {
            // Checks pseudolegal moves of the piece
            List<Move> pseudoLegalMoves = attacker.getPseudoLegalMoves();
            for (Move move : pseudoLegalMoves) {
                if (move.getTargetField().equals(field)) {
                    return true;
                }
            }
        }
        return false;
    }
}
