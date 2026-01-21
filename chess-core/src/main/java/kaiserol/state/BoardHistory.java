package kaiserol.state;

import kaiserol.moves.Move;
import kaiserol.moves.MoveException;

import java.util.Stack;

public class BoardHistory {
    private final Stack<BoardState> stack;
    private final Stack<BoardState> redoStack;

    public BoardHistory() {
        this.stack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public BoardState current() {
        return stack.peek();
    }

    public void initialize() {
        stack.clear();
        redoStack.clear();

        // Get initial board state
        BoardState initialState = BoardState.initial();
        stack.push(initialState);
    }

    public void update(Move move) throws MoveException {
        if (move == null) throw new MoveException("Move must not be null.");
        redoStack.clear();

        // Get next board state
        BoardState nextState = BoardState.nextState(current(), move);
        stack.push(nextState);
    }

    public void undo() throws MoveException {
        if (isUndoable()) {
            BoardState previous = stack.pop();
            redoStack.push(previous);
        } else {
            throw new MoveException("No moves to undo.");
        }
    }

    public Move redo() throws MoveException {
        if (isRedoable()) {
            BoardState next = redoStack.pop();
            stack.push(next);
            return next.getMove();
        } else {
            throw new MoveException("No moves to redo.");
        }
    }

    private boolean isUndoable() {
        return stack.size() > 1;
    }

    private boolean isRedoable() {
        return !redoStack.isEmpty();
    }

    /**
     * Returns how often the current position has occurred.
     */
    public int countRepetitions() {
        if (stack.isEmpty()) return 0;

        String positionalFen = current().getPositionalFEN();
        int count = 0;

        for (BoardState state : stack) {
            if (state.getPositionalFEN().equals(positionalFen)) {
                count++;
            }
        }
        return count;
    }
}