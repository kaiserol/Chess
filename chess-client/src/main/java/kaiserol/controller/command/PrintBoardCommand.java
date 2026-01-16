package kaiserol.controller.command;

import kaiserol.chessboard.ChessBoard;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PrintBoardCommand extends Command {
    private final ChessBoard board;

    public PrintBoardCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, ChessBoard board) {
        super(out, err);
        this.board = board;
    }

    @Override
    public void execute(String[] args) throws Exception {
        board.toConsole();
    }

    @Override
    public String keyword() {
        return "board";
    }

    @Override
    public String description() {
        return "Prints the current board";
    }
}
