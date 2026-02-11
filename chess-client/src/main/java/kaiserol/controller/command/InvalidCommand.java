package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class InvalidCommand extends Command {
    private final String invalidKeyWord;

    public InvalidCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull String invalidKeyWord) {
        super(out, err);
        this.invalidKeyWord = invalidKeyWord;
    }

    @Override
    public void execute(@NotNull String[] args) {
        err.accept("The command '%s' is invalid.".formatted(invalidKeyWord));
    }

    @Override
    public String keyWord() {
        return invalidKeyWord;
    }

    @Override
    public String description() {
        return "";
    }
}
