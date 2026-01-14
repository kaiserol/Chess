package kaiserol.controller.command;

import kaiserol.logic.Game;
import kaiserol.logic.moves.MoveException;
import org.jetbrains.annotations.NotNull;

public class MoveCommand extends Command {
    private final Game game;
    private final String move;

    public MoveCommand(@NotNull Game game, @NotNull String move) {
        this.game = game;
        this.move = move;
    }

    @Override
    public void execute() throws MoveException {
        game.executeMove(move);
    }

    @Override
    public String keyword() {
        return move;
    }

    @Override
    public String description() {
        return "Execute the move '%s'".formatted(move);
    }
}