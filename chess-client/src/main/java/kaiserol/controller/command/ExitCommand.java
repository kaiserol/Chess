package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

public class ExitCommand extends Command {
    private final Runnable onExit;

    public ExitCommand(@NotNull Runnable onExit) {
        this.onExit = onExit;
    }

    @Override
    public void execute(String[] args) {
        onExit.run();
    }

    @Override
    public String keyword() {
        return "exit";
    }

    @Override
    public String description() {
        return "Ends the game";
    }
}