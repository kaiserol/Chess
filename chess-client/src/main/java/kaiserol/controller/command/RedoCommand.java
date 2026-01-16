package kaiserol.controller.command;

import kaiserol.Game;
import kaiserol.moves.MoveException;
import org.jetbrains.annotations.NotNull;

public class RedoCommand extends Command {
    private final Game game;

    public RedoCommand(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void execute(String[] args) throws MoveException {
        game.redoMove();
    }

    @Override
    public String keyword() {
        return "redo";
    }

    @Override
    public String description() {
        return "Redoes the last undone move";
    }
}