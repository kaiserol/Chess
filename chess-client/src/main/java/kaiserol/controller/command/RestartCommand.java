package kaiserol.controller.command;

import kaiserol.Game;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RestartCommand extends Command {
    private final Game game;

    public RestartCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull Game game) {
        super(out, err);
        this.game = game;
    }

    @Override
    public void execute(@NotNull String[] args) {
        game.reset();
    }

    @Override
    public String keyWord() {
        return "restart";
    }

    @Override
    public String description() {
        return "Restarts the game";
    }
}