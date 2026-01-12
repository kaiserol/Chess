package kaiserol.controller;

import kaiserol.logic.moves.PawnPromotion;

public class GrafikChess extends ChessController {
    public GrafikChess(Game game) {
        super(game);
    }

    @Override
    public void run() {
    }

    @Override
    public PawnPromotion.Choice getPromotionChoice() {
        return PawnPromotion.Choice.QUEEN;
    }
}
