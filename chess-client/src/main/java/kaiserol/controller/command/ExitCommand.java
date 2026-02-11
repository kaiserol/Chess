package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ExitCommand extends Command {
    private final Runnable onExit;

    public ExitCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull Runnable onExit) {
        super(out, err);
        this.onExit = onExit;
    }

    @Override
    public void execute(@NotNull String[] args) {
        onExit.run();
    }

    @Override
    public String keyWord() {
        return "exit";
    }

    @Override
    public String description() {
        return "Ends the game.";
    }
}