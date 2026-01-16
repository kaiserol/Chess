package kaiserol.controller.command;

import kaiserol.chessboard.ChessBoard;

public class PrintBoardCommand extends Command {
    private final ChessBoard board;

    public PrintBoardCommand(ChessBoard board) {
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
