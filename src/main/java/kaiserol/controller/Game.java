package kaiserol.controller;

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

// TODO: Implement redo
public class Game {
    private final ChessBoard board;
    private final Stack<BoardSnapshot> boardHistory;
    private GameState gameState;
    private Side currentSide;
    private int halfMoveCount;
    private int fullMoveCount;

    public Game() {
        this.board = new ChessBoard();
        this.boardHistory = new Stack<>();

        // Build chessboard
        buildBoard();
    }

    public void buildBoard() {
        this.currentSide = Side.WHITE;
        this.gameState = GameState.ACTIVE;
        this.halfMoveCount = 0;
        this.fullMoveCount = 1;

        // Initialize pieces
        this.boardHistory.clear();
        this.board.initializePieces();

        // Initialize snapshot
        recordSnapshot();
    }

    public ChessBoard getBoard() {
        return board;
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

    public void executeMove(String moveString) throws MoveException {
        if (moveString == null || moveString.length() != 4) {
            throw new MoveException("The move must consist of 4 characters (e.g., a2a4).");
        }

        if (gameState.isFinal()) {
            throw new MoveException("The game is already over.");
        }

        String fromCoord = moveString.substring(0, 2);
        String toCoord = moveString.substring(2, 4);

        ChessField startField = board.getField(fromCoord);
        ChessField targetField = board.getField(toCoord);

        // Check whether the start field is occupied
        if (!startField.isOccupied()) throw new MoveException("The start field has no piece.");

        // Check whether the piece belongs to the current side
        Piece piece = startField.getPiece();
        if (piece.getSide() != currentSide)
            throw new MoveException("The piece to be moved does not belong to your side.");

        // Find the correct move and execute it
        for (Move move : piece.getLegalMoves()) {
            if (move.getTargetField().equals(targetField)) {
                executeMove(move);
                return;
            }
        }
        throw new MoveException("The move is invalid");
    }

    private void executeMove(Move move) {
        // Check if it's a pawn move or a capture to reset halfMoveCount
        Piece piece = move.getStartField().getPiece();
        if (piece instanceof Pawn || move.getTargetField().isOccupied()) halfMoveCount = 0;
        else halfMoveCount++;

        // Increment fullMoveCount after black move
        if (currentSide == Side.BLACK) fullMoveCount++;

        // Execute the move
        board.executeMove(move);

        // Update the current side and record the snapshot
        this.currentSide = currentSide.opposite();
        recordSnapshot();

        // Update the game state
        this.gameState = GameState.getGameState(board, currentSide, boardHistory, halfMoveCount);
    }

    public void undoMove() throws MoveException {
        if (board.getLastMove() == null) throw new MoveException("No moves to undo.");

        // Undo the move
        board.undoMove();

        // Update the current side and remove the snapshot
        this.currentSide = currentSide.opposite();
        removeSnapshot();

        // Restore move counts from the previous snapshot
        BoardSnapshot previousSnapshot = boardHistory.peek();
        this.halfMoveCount = previousSnapshot.getHalfMoveCount();
        this.fullMoveCount = previousSnapshot.getFullMoveCount();

        // Update the game state
        this.gameState = GameState.getGameState(board, currentSide, boardHistory, halfMoveCount);
    }

    private void recordSnapshot() {
        BoardSnapshot snapshot = new BoardSnapshot(board, currentSide, halfMoveCount, fullMoveCount);
        boardHistory.add(snapshot);
    }

    private void removeSnapshot() {
        boardHistory.removeLast();
    }
}
