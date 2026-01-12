package kaiserol.controller;
import kaiserol.logic.moves.PawnPromotion;

public abstract class ChessController {
    protected final Game game;

    public ChessController(Game game) {
        this.game = game;
    }

    public abstract void run();

    public abstract PawnPromotion.Choice getPromotionChoice();
}
