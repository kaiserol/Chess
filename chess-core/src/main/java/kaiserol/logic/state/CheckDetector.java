package kaiserol.logic.state;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.pieces.King;
import kaiserol.logic.pieces.Piece;

import java.util.List;

public class CheckDetector {

    public static boolean isInCheck(ChessBoard board, Side side) {
        List<Piece> pieces = board.getPieces(side);
        King king = board.getKing(pieces);
        if (king == null) {
            throw new IllegalStateException("King not found!");
        }

        return isFieldAttacked(board, king.getField(), side.opposite());
    }

    public static boolean isFieldAttacked(ChessBoard board, ChessField field, Side attackerSide) {
        List<Piece> attackers = board.getPieces(attackerSide);
        for (Piece attacker : attackers) {
            // Check pseudolegal moves of the piece
            List<Move> pseudoLegalMoves = attacker.getSortedPseudoLegalMoves();
            for (Move move : pseudoLegalMoves) {
                if (move.getTargetField().equals(field)) {
                    return true;
                }
            }
        }
        return false;
    }
}
