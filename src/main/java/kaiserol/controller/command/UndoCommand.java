package kaiserol.controller.command;

import kaiserol.logic.Game;
import kaiserol.logic.moves.MoveException;
import org.jetbrains.annotations.NotNull;

public class UndoCommand extends Command {

    private final Game game;

    public UndoCommand(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void execute() throws MoveException {
        game.undoMove();
    }

    @Override
    public String keyword() {
        return "undo";
    }

    @Override
    public String description() {
        return "Undo the last move";
    }
}