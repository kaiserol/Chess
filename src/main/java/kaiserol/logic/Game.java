package kaiserol.logic;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.MoveException;
import kaiserol.logic.pieces.Pawn;
import kaiserol.logic.pieces.Piece;
import kaiserol.logic.state.BoardSnapshot;
import kaiserol.logic.state.GameState;

import java.util.Stack;

public class Game {
    private final ChessBoard board;
    private final Stack<BoardSnapshot> boardHistory;
    private final Stack<BoardSnapshot> redoBoardHistory;
    private GameState gameState;
    private Side currentSide;
    private int halfMoveCount;
    private int fullMoveCount;

    public Game() {
        this.board = new ChessBoard();
        this.boardHistory = new Stack<>();
        this.redoBoardHistory = new Stack<>();
        reset();
    }

    public ChessBoard getBoard() {
        return board;
    }

    public String getLatestFEN() {
        return boardHistory.peek().getFEN();
    }

    public Stack<BoardSnapshot> getBoardHistory() {
        return boardHistory;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void executeMove(String move) throws MoveException {
        validateMoveString(move);

        ChessField from = board.getField(move.substring(0, 2));
        ChessField to = board.getField(move.substring(2, 4));

        Piece piece = requireValidPiece(from);

        // Execute the move
        Move legalMove = piece.getLegalMoves().stream()
                .filter(m -> m.getTargetField().equals(to))
                .findFirst()
                .orElseThrow(() -> new MoveException("The move is invalid"));

        executeMove(legalMove);
    }

    public void undoMove() throws MoveException {
        if (boardHistory.size() <= 1) {
            throw new MoveException("No moves to undo.");
        }

        // Undo the last move
        BoardSnapshot last = boardHistory.pop();
        redoBoardHistory.push(last);
        board.undoMove();

        // Update game values
        BoardSnapshot current = boardHistory.peek();
        restoreFromSnapshot(current);
        updateGameState();
    }

    public void redoMove() throws MoveException {
        if (redoBoardHistory.isEmpty()) {
            throw new MoveException("No moves to redo.");
        }

        // Get the last move from the redo stack
        BoardSnapshot undone = redoBoardHistory.pop();
        Move move = undone.getLastMove();

        // Redo the move
        boardHistory.push(undone);
        board.executeMove(move);

        // Update game values
        restoreFromSnapshot(undone);
        updateGameState();
    }

    public void reset() {
        this.boardHistory.clear();
        this.redoBoardHistory.clear();
        this.board.initializePieces();

        // Create an initial snapshot
        BoardSnapshot initial = BoardSnapshot.initial(board);
        boardHistory.push(initial);

        // Update game values
        restoreFromSnapshot(initial);
        updateGameState();
    }

    private void restoreFromSnapshot(BoardSnapshot snapshot) {
        this.currentSide = snapshot.getCurrentSide();
        this.halfMoveCount = snapshot.getHalfMoveCount();
        this.fullMoveCount = snapshot.getFullMoveCount();
    }

    // =======================
    // Internal logic
    // =======================
    private void executeMove(Move move) {
        // Clear the recovery stack when a new move is executed.
        redoBoardHistory.clear();

        // Calculate values
        int nextHalfMove = calculateHalfMove(move);
        int nextFullMove = fullMoveCount + (currentSide.isWhite() ? 0 : 1);
        Side nextSide = currentSide.opposite();

        // Execute the move
        board.executeMove(move);

        // Update local status variables
        this.currentSide = nextSide;
        this.halfMoveCount = nextHalfMove;
        this.fullMoveCount = nextFullMove;

        // Snapshot für den NEUEN Zustand speichern
        boardHistory.push(new BoardSnapshot(board, currentSide, halfMoveCount, fullMoveCount, move));
        updateGameState();
    }

    private int calculateHalfMove(Move move) {
        Piece piece = move.getStartField().getPiece();

        // 50-move rule: Reset after a pawn move or capture
        return (piece instanceof Pawn || move.getTargetField().isOccupied())
                ? 0 : halfMoveCount + 1;
    }

    private void updateGameState() {
        this.gameState = GameState.getGameState(board, currentSide, boardHistory, halfMoveCount);
    }

    // =======================
    // Validation
    // =======================

    private void validateMoveString(String move) throws MoveException {
        if (move == null || move.length() != 4) {
            throw new MoveException("The move '%s' must consist of 4 characters.".formatted(move));
        }
    }

    private Piece requireValidPiece(ChessField field) throws MoveException {
        if (!field.isOccupied()) {
            throw new MoveException("The field '%s' is empty.".formatted(field));
        }

        Piece piece = field.getPiece();
        if (piece.getSide() != this.currentSide) {
            throw new MoveException("The piece at '%s' does not belong to the current side.".formatted(field));
        }
        return piece;
    }
}
