package kaiserol.controller.command;

import kaiserol.Game;
import kaiserol.moves.MoveException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class UndoCommand extends Command {
    private final Game game;

    public UndoCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull Game game) {
        super(out, err);
        this.game = game;
    }

    @Override
    public void execute(String[] args) throws MoveException {
        game.undoMove();
    }

    @Override
    public String keyword() {
        return "undo";
    }

    @Override
    public String description() {
        return "Undoes the last move";
    }
}