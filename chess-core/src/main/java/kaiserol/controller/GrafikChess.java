package kaiserol.controller;

import kaiserol.logic.ChessController;
import kaiserol.logic.Game;
import kaiserol.logic.moves.PawnPromotion;

public class GrafikChess extends ChessController {
    public GrafikChess(Game game) {
        super(game);
    }

    @Override
    public void run() {
        printlnMessage("This is a graphical version of chess. It is not yet implemented.");
    }

    @Override
    public PawnPromotion.Choice getPromotionChoice() {
        return PawnPromotion.Choice.QUEEN;
    }
}
