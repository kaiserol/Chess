package kaiserol.controller;

public abstract class ChessController {
    protected final Game game;

    public ChessController(Game game) {
        this.game = game;
    }

    public abstract void run();
}
