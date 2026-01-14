package kaiserol.state;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.pieces.King;
import kaiserol.pieces.Piece;

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
