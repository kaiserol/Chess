package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PrintBoardCommand extends Command {
    private final Runnable onPrint;

    public PrintBoardCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull Runnable onPrint) {
        super(out, err);
        this.onPrint = onPrint;
    }

    @Override
    public void execute(@NotNull String[] args) throws Exception {
        onPrint.run();
    }

    @Override
    public String keyWord() {
        return "board";
    }

    @Override
    public String description() {
        return "Prints the current board";
    }
}
