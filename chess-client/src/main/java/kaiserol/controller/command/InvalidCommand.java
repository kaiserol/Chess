package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class InvalidCommand extends Command {
    private final String keyword;

    public InvalidCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull String keyword) {
        super(out, err);
        this.keyword = keyword;
    }

    @Override
    public void execute(String[] args) {
        err.accept("The command '%s' is invalid.".formatted(keyword));
    }

    @Override
    public String keyword() {
        return keyword;
    }

    @Override
    public String description() {
        return "";
    }
}
