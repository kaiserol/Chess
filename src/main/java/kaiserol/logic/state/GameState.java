package kaiserol.logic.state;

import kaiserol.controller.Game;
import kaiserol.logic.pieces.Piece;

import java.util.List;

public enum GameState {
    ACTIVE,
    CHECK,
    CHECKMATE,
    STALEMATE,
    DRAW;

    public boolean isFinal() {
        return this == CHECKMATE || this == STALEMATE || this == DRAW;
    }

    public static GameState getGameState(Game game) {
        // 1. Check whether legal moves exist
        List<Piece> pieces = game.getBoard().getPieces(game.getCurrentSide());
        boolean hasLegalMoves = pieces.stream().anyMatch(piece -> !piece.getLegalMoves().isEmpty());

        // 2. Check whether the king is in check
        boolean inCheck = CheckDetector.isInCheck(game.getBoard(), game.getCurrentSide());

        // 3. Check whether the end states are reached (without further rules)
        if (!hasLegalMoves) {
            if (inCheck) return GameState.CHECKMATE;
            else return GameState.STALEMATE;
        }

        // 4. Check whether the draw rules are fulfilled
        if (DrawDetector.hasInsufficientMaterial(game.getBoard())) return DRAW;
        if (DrawDetector.isThreefoldRepetition(game.getBoardHistory())) return DRAW;
        if (DrawDetector.is50MoveRule(game.getHalfMoveCount())) return DRAW;

        // 5. Return the current state
        if (inCheck) return GameState.CHECK;
        else return GameState.ACTIVE;
    }
}
