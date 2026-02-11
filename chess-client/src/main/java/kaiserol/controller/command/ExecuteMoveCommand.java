package kaiserol.controller.command;

import kaiserol.Game;
import kaiserol.moves.MoveException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ExecuteMoveCommand extends Command {
    private final Game game;
    private final String move;

    public ExecuteMoveCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull Game game, @NotNull String move) {
        super(out, err);
        this.game = game;
        this.move = move;
    }

    @Override
    public void execute(@NotNull String[] args) throws MoveException {
        game.executeMove(move);
    }

    @Override
    public String keyWord() {
        return move;
    }

    @Override
    public String description() {
        return "Executes the move '%s'.".formatted(move);
    }
}