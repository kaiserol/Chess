package kaiserol.state;

import kaiserol.chessboard.BoardHistory;
import kaiserol.chessboard.ChessBoard;
import kaiserol.pieces.Piece;

import java.util.List;

public enum GameState {
    NORMAL,
    CHECK,
    CHECKMATE,
    DRAW_STALEMATE,
    DRAW_INSUFFICIENT_MATERIAL,
    DRAW_THREEFOLD_REPETITION,
    DRAW_50_MOVE_RULE,
    NO_MATERIAL;

    public boolean isDraw() {
        return this == DRAW_INSUFFICIENT_MATERIAL || this == DRAW_THREEFOLD_REPETITION || this == DRAW_50_MOVE_RULE;
    }

    public static GameState getGameState(ChessBoard board, BoardHistory boardHistory) {
        BoardState currentState = boardHistory.current();

        // 1. Check whether legal moves exist
        List<Piece> pieces = board.getPieces(currentState.getSideToMove());
        if (pieces.isEmpty()) return NO_MATERIAL;

        // 2. Check whether the king is in check
        boolean inCheck = CheckDetector.isInCheck(board, currentState.getSideToMove());

        // 3. Check whether checkmate or stalemate is reached
        boolean hasLegalMoves = pieces.stream().anyMatch(piece -> !piece.getLegalMoves().isEmpty());
        if (!hasLegalMoves) {
            if (inCheck) return GameState.CHECKMATE;
            else return GameState.DRAW_STALEMATE;
        }

        // 4. Check whether the draw rules are fulfilled
        if (DrawDetector.hasInsufficientMaterial(board)) return DRAW_INSUFFICIENT_MATERIAL;
        if (DrawDetector.isThreefoldRepetition(boardHistory)) return DRAW_THREEFOLD_REPETITION;
        if (DrawDetector.is50MoveRule(currentState.getHalfMoveCount())) return DRAW_50_MOVE_RULE;

        // 5. Return the current state
        if (inCheck) return GameState.CHECK;
        else return GameState.NORMAL;
    }
}
