package kaiserol;

import kaiserol.handler.Game;
import kaiserol.logic.ChessSimulator;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("simulation")) {
            ChessSimulator simulator = new ChessSimulator();

            // Pawn Promotion
            String[] whiteMoves = {"a2a4", "a4a5", "a5a6", "a6b7", "b7a8"};
            String[] blackMoves = {"h7h6", "h6h5", "h5h4", "h4h3", "h3g2"};

            simulator.runSimulation(whiteMoves, blackMoves);
        } else {
            Game game = new Game();
            game.startGameLoop();
        }
    }
}
