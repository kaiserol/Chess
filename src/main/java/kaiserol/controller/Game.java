package kaiserol.controller;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.pieces.Piece;
import kaiserol.logic.state.ChessDetector;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.MoveException;
import kaiserol.logic.state.GameState;

public class Game {
    private final ChessBoard board;
    private Side currentSide;
    private GameState gameState;

    public Game() {
        this.board = new ChessBoard();
        buildBoard();
    }

    public void buildBoard() {
        this.currentSide = Side.WHITE;
        this.gameState = GameState.ACTIVE;

        // Initialize chess board
        this.board.setInitialStartingPositions();
    }

    public ChessBoard getBoard() {
        return board;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void executeMove(String moveString) throws MoveException {
        if (moveString == null || moveString.length() != 4) {
            throw new MoveException("The move must consist of 4 characters (e.g., a2a4).");
        }

        if (gameState != GameState.ACTIVE && gameState != GameState.CHECK) {
            throw new MoveException("The game is already over.");
        }

        String fromCoord = moveString.substring(0, 2);
        String toCoord = moveString.substring(2, 4);

        ChessField startField = board.getField(fromCoord);
        ChessField targetField = board.getField(toCoord);

        if (startField.isOccupied()) {
            Piece piece = startField.getPiece();
            if (currentSide == piece.getSide()) {
                for (Move move : piece.getLegalMoves()) {
                    if (move.getTargetField().equals(targetField)) {
                        board.executeMove(move);
                        this.currentSide = currentSide.opposite();
                        updateGameState();
                        return;
                    }
                }
            }
        }
        throw new MoveException("The move is invalid");
    }

    public void undoMove() throws MoveException {
        if (board.getLastMove() == null) {
            throw new MoveException("No moves to undo.");
        }

        board.undoMove();
        this.currentSide = currentSide.opposite();
        updateGameState();
    }

    private void updateGameState() {
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
            if (inCheck) gameState = GameState.CHECKMATE;
            else gameState = GameState.STALEMATE;
        } else {
            if (inCheck) gameState = GameState.CHECK;
            else gameState = GameState.ACTIVE;
        }
    }
}
