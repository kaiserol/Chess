package kaiserol.chessboard;

import kaiserol.moves.Move;
import kaiserol.state.BoardState;

import java.util.Objects;
import java.util.Stack;

public class BoardHistory {
    private final Stack<BoardState> stack;
    private final Stack<BoardState> redoStack;

    public BoardHistory() {
        this.stack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public boolean isInitialized() {
        return !stack.isEmpty();
    }

    public boolean isUndoable() {
        return stack.size() > 1;
    }

    public boolean isRedoable() {
        return !redoStack.isEmpty();
    }

    public BoardState current() {
        if (isInitialized()) {
            return stack.peek();
        }
        throw new IllegalStateException("The board history is not initialized.");
    }

    public void initialize() {
        stack.clear();
        redoStack.clear();

        // Get initial board state
        BoardState initialState = BoardState.initial();
        stack.push(initialState);
    }

    /**
     * Initializes the history with the current state on the board.
     * Important for tests and FEN import!
     */
    public void initializeWithCurrentState(ChessBoard board) {
        stack.clear();
        redoStack.clear();

        // Create a board state from the current board
        BoardState currentState = BoardState.fromBoard(board);
        stack.push(currentState);
    }

    public void addMove(Move move) {
        redoStack.clear();

        // Get next board state
        BoardState currentState = current();
        BoardState nextState = BoardState.nextState(currentState, move);
        stack.push(nextState);
    }

    public Move undo() {
        BoardState previousState = stack.pop();
        redoStack.push(previousState);
        return previousState.getMove();
    }

    public Move redo() {
        BoardState nextState = redoStack.pop();
        stack.push(nextState);
        return nextState.getMove();
    }

    /**
     * Returns how often the current position has occurred.
     */
    public int countRepetitions() {
        if (stack.isEmpty()) return 0;

        String positionalFen = current().getPositionalFEN();
        int count = 0;

        for (BoardState state : stack) {
            if (Objects.equals(state.getPositionalFEN(), positionalFen)) {
                count++;
            }
        }
        return count;
    }
}