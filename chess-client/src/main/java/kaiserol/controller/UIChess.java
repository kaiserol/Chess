package kaiserol.controller;

import kaiserol.ChessController;
import kaiserol.Game;
import kaiserol.moves.PawnPromotion;

public class UIChess extends ChessController {
    public UIChess(Game game) {
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
