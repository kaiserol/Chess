package kaiserol.logic.state;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.pieces.Piece;

import java.util.List;
import java.util.Stack;

public enum GameState {
    ACTIVE,
    CHECK,
    CHECKMATE,
    STALEMATE,
    DRAW_INSUFFICIENT_MATERIAL,
    DRAW_THREEFOLD_REPETITION,
    DRAW_50_MOVE_RULE,
    DRAW;

    public boolean isDraw() {
        return this == DRAW_INSUFFICIENT_MATERIAL || this == DRAW_THREEFOLD_REPETITION || this == DRAW_50_MOVE_RULE;
    }

    public static GameState getGameState(ChessBoard board, Side currentSide, Stack<BoardSnapshot> boardHistory, int halfMoveCount) {
        // 1. Check whether legal moves exist
        List<Piece> pieces = board.getPieces(currentSide);
        boolean hasLegalMoves = pieces.stream().anyMatch(piece -> !piece.getLegalMoves().isEmpty());

        // 2. Check whether the king is in check
        boolean inCheck = CheckDetector.isInCheck(board, currentSide);

        // 3. Check whether the end states are reached (without further rules)
        if (!hasLegalMoves) {
            if (inCheck) return GameState.CHECKMATE;
            else return GameState.STALEMATE;
        }

        // 4. Check whether the draw rules are fulfilled
        if (DrawDetector.hasInsufficientMaterial(board)) return DRAW_INSUFFICIENT_MATERIAL;
        if (DrawDetector.isThreefoldRepetition(boardHistory)) return DRAW_THREEFOLD_REPETITION;
        if (DrawDetector.is50MoveRule(halfMoveCount)) return DRAW_50_MOVE_RULE;

        // 5. Return the current state
        if (inCheck) return GameState.CHECK;
        else return GameState.ACTIVE;
    }
}
