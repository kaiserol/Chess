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
// TODO: Implement functionality for halfMoveCount and fullMoveCount
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

    public int getHalfMoveCount() {
        return halfMoveCount;
    }

    public int getFullMoveCount() {
        return fullMoveCount;
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

        // Execute move
        board.executeMove(move);

        // Update the game state
        this.currentSide = currentSide.opposite();
        recordSnapshot();
        this.gameState = GameState.getGameState(this);
    }

    public void undoMove() throws MoveException {
        if (board.getLastMove() == null) throw new MoveException("No moves to undo.");

        // Undo move
        Move move = board.undoMove();

        // Check if the move was a pawn move or a capture to reset halfMoveCount
        Piece piece = move.getStartField().getPiece();
        if (piece instanceof Pawn || move.getTargetField().isOccupied()) halfMoveCount = 0;
        else halfMoveCount--;

        // Update the game state
        this.currentSide = currentSide.opposite();
        removeSnapshot();
        this.gameState = GameState.getGameState(this);
    }

    private void recordSnapshot() {
        BoardSnapshot snapshot = new BoardSnapshot(board, currentSide);
        boardHistory.add(snapshot);
    }

    private void removeSnapshot() {
        boardHistory.removeLast();
    }
}
