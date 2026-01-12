package kaiserol.logic.state;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.pieces.Piece;

import java.util.List;

public enum GameState {
    ACTIVE,
    CHECK,
    CHECKMATE,
    STALEMATE,
    DRAW;

    public static GameState getGameState(ChessBoard board, Side currentSide) {
        List<Piece> pieces = board.getPieces(currentSide);
        boolean hasLegalMoves = false;

        for (Piece piece : pieces) {
            if (!piece.getLegalMoves().isEmpty()) {
                hasLegalMoves = true;
                break;
            }
        }

        boolean inCheck = ChessDetector.isInCheck(board, currentSide);
        if (!hasLegalMoves) {
            if (inCheck) return GameState.CHECKMATE;
            else return GameState.STALEMATE;
        } else {
            if (inCheck) return GameState.CHECK;
            else return GameState.ACTIVE;
        }
    }
}
