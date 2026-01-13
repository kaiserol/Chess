package kaiserol.logic.state;

import kaiserol.controller.Game;
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
    DRAW;

    public boolean isFinal() {
        return this == CHECKMATE || this == STALEMATE || this == DRAW;
    }

    public static GameState getGameState(Game game) {
        return getGameState(
                game.getBoard(),
                game.getCurrentSide(),
                game.getBoardHistory(),
                game.getHalfMoveCount()
        );
    }

    public static GameState getGameState(ChessBoard chessBoard, Side currentSide, Stack<BoardSnapshot> boardHistory, int halfMoveCount) {
        // 1. Check whether legal moves exist
        List<Piece> pieces = chessBoard.getPieces(currentSide);
        boolean hasLegalMoves = pieces.stream().anyMatch(piece -> !piece.getLegalMoves().isEmpty());

        // 2. Check whether the king is in check
        boolean inCheck = CheckDetector.isInCheck(chessBoard, currentSide);

        // 3. Check whether the end states are reached (without further rules)
        if (!hasLegalMoves) {
            if (inCheck) return GameState.CHECKMATE;
            else return GameState.STALEMATE;
        }

        // 4. Check whether the draw rules are fulfilled
        if (DrawDetector.hasInsufficientMaterial(chessBoard)) return DRAW;
        if (DrawDetector.isThreefoldRepetition(boardHistory)) return DRAW;
        if (DrawDetector.is50MoveRule(halfMoveCount)) return DRAW;

        // 5. Return the current state
        if (inCheck) return GameState.CHECK;
        else return GameState.ACTIVE;
    }
}
