package kaiserol;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.CoordinateException;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.moves.MoveException;
import kaiserol.pieces.Piece;
import kaiserol.state.GameState;

import java.util.Objects;

public class Game {
    private final ChessBoard board;
    private GameState gameState;

    public Game() {
        this.board = new ChessBoard();
        reset();
    }

    // =======================
    // Internal logic
    // =======================

    public void reset() {
        board.initializePieces();

        // Update the game state
        gameState = board.getGameState();
    }

    // =======================
    // Execute / Undo / Redo
    // =======================

    public void executeMove(String move) throws MoveException {
        // Validate the move string
        validateMoveString(move);
        ChessField from = validateField(move.substring(0, 2));
        ChessField to = validateField(move.substring(2, 4));

        // Validate the piece on the start field
        Piece piece = requireValidPiece(from);

        // Execute the move
        Move legalMove = piece.getLegalMoves().stream()
                .filter(m -> Objects.equals(m.getTargetField(), to))
                .findFirst()
                .orElseThrow(() -> new MoveException("The move is invalid"));

        executeMove(legalMove);
    }

    private void executeMove(Move move) throws MoveException {
        // Execute the next move
        boolean executed = board.executeMove(move);
        if (!executed) throw new MoveException("The move could not be executed.");

        // Update the game state
        gameState = board.getGameState();
    }

    public void undoMove() throws MoveException {
        // Undo the last move
        boolean undone = board.undoMove();
        if (!undone) throw new MoveException("No moves to undo.");

        // Update the game state
        gameState = board.getGameState();
    }

    public void redoMove() throws MoveException {
        // Redo the last undone move
        boolean redone = board.redoMove();
        if (!redone) throw new MoveException("No moves to redo.");

        // Update the game state
        gameState = board.getGameState();
    }

    // =======================
    // Validation
    // =======================

    private void validateMoveString(String move) throws MoveException {
        if (move == null || move.length() != 4) {
            throw new MoveException("The move '%s' is invalid. It must consist of 4 characters.".formatted(move));
        }
    }

    private ChessField validateField(String coord) throws MoveException {
        try {
            return board.getField(coord);
        } catch (CoordinateException e) {
            throw new MoveException(e.getMessage());
        }
    }

    private Piece requireValidPiece(ChessField field) throws MoveException {
        if (!field.isOccupied()) {
            throw new MoveException("There is no piece on '%s'.".formatted(field));
        }

        Piece piece = field.getPiece();
        if (piece.getSide() != getSideToMove()) {
            throw new MoveException("The piece on '%s' does not belong to the current side.".formatted(field));
        }
        return piece;
    }

    // =======================
    // Getter
    // =======================

    public ChessBoard getBoard() {
        return board;
    }

    public Side getSideToMove() {
        return board.getCurrentState().getSideToMove();
    }

    public String getCurrentFEN() {
        return board.getCurrentState().getFEN();
    }

    public GameState getGameState() {
        return gameState;
    }
}
