package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class InvalidCommand extends Command {
    private final String keyword;
    private final Consumer<String> output;

    public InvalidCommand(@NotNull String keyword, @NotNull Consumer<String> output) {
        this.keyword = keyword;
        this.output = output;
    }

    @Override
    public void execute() throws Exception {
        String result = "The command '%s' is invalid".formatted(keyword);
        output.accept(result);
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
