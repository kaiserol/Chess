package kaiserol.controller.command;

import kaiserol.Game;
import kaiserol.moves.MoveException;
import org.jetbrains.annotations.NotNull;

public class ExecuteMoveCommand extends Command {
    private final Game game;
    private final String move;

    public ExecuteMoveCommand(@NotNull Game game, @NotNull String move) {
        this.game = game;
        this.move = move;
    }

    @Override
    public void execute(String[] args) throws MoveException {
        game.executeMove(move);
    }

    @Override
    public String keyword() {
        return move;
    }

    @Override
    public String description() {
        return "Executes the move '%s'".formatted(move);
    }
}