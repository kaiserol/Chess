package kaiserol.controller.command;

import kaiserol.logic.Game;
import kaiserol.logic.moves.MoveException;
import org.jetbrains.annotations.NotNull;

public class RedoCommand extends Command {

    private final Game game;

    public RedoCommand(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void execute() throws MoveException {
        game.redoMove();
    }

    @Override
    public String keyword() {
        return "redo";
    }

    @Override
    public String description() {
        return "Redo the last undone move";
    }
}