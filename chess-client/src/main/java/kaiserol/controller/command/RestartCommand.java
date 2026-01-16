package kaiserol.controller.command;

import kaiserol.Game;
import org.jetbrains.annotations.NotNull;

public class RestartCommand extends Command {
    private final Game game;

    public RestartCommand(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void execute(String[] args) {
        game.reset();
    }

    @Override
    public String keyword() {
        return "restart";
    }

    @Override
    public String description() {
        return "Restarts the game";
    }
}