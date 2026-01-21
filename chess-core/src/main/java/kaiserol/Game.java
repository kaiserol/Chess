package kaiserol;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.CoordinateException;
import kaiserol.chessboard.Side;
import kaiserol.fen.FENCreator;
import kaiserol.moves.Move;
import kaiserol.moves.MoveException;
import kaiserol.pieces.Piece;
import kaiserol.state.BoardHistory;
import kaiserol.state.BoardState;
import kaiserol.state.GameState;

public class Game {
    private final ChessBoard board;
    private final BoardHistory boardHistory;
    private GameState gameState;

    public Game() {
        this.board = new ChessBoard();
        this.boardHistory = new BoardHistory();
        reset();
    }

    // =======================
    // Internal logic
    // =======================

    public void reset() {
        board.initializePieces();
        boardHistory.initialize();

        // Update states
        updatesStates();
    }

    private void updatesStates() {
        updateBoard(board, boardHistory);
        gameState = GameState.getGameState(board, boardHistory);
    }

    private void updateBoard(ChessBoard board, BoardHistory boardHistory) {
        // Get the current state
        BoardState currentState = boardHistory.current();

        // Update the chess board
        board.setCastlingRights(currentState.getCastlingRights());
        board.setEnPassantTarget(currentState.getEnPassantTarget());

        // Update the fen
        currentState.setPositionalFEN(FENCreator.toPositionalFEN(board, currentState.getSideToMove()));
        currentState.setFEN(FENCreator.toFEN(board, currentState.getSideToMove(), currentState.getHalfMoveCount(), currentState.getFullMoveCount()));
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
                .filter(m -> m.getTargetField().equals(to))
                .findFirst()
                .orElseThrow(() -> new MoveException("The move is invalid"));

        executeMove(legalMove);
    }

    private void executeMove(Move move) throws MoveException {
        // Execute the next move
        boardHistory.update(move);
        board.executeMove(move);

        // Update states
        updatesStates();
    }

    public void undoMove() throws MoveException {
        // Undo the last move
        board.undoMove();
        boardHistory.undo();

        // Update states
        updatesStates();
    }

    public void redoMove() throws MoveException {
        // Redo the last undone move
        Move undoneMove = boardHistory.redo();
        board.executeMove(undoneMove);

        // Update states
        updatesStates();
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
        return boardHistory.current().getSideToMove();
    }

    public String getCurrentFEN() {
        return boardHistory.current().getFEN();
    }

    public GameState getGameState() {
        return gameState;
    }
}
