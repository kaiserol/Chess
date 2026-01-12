package kaiserol.logic.state;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;

public enum GameState {
    ACTIVE,
    CHECK,
    CHECKMATE,
    STALEMATE,
    DRAW;

    public static GameState getGameState(ChessBoard board, Side currentSide) {
        boolean hasLegalMoves = false;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                ChessField field = board.getField(x, y);
                if (board.isOccupiedBySide(field, currentSide)) {
                    if (!field.getPiece().getLegalMoves().isEmpty()) {
                        hasLegalMoves = true;
                        break;
                    }
                }
            }
            if (hasLegalMoves) break;
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
