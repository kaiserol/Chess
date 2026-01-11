package kaiserol;

import kaiserol.controller.TerminalChessSimulator;
import kaiserol.controller.Game;
import kaiserol.controller.GrafikChess;
import kaiserol.controller.TerminalChess;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        if (args.length > 0 && args[0].equalsIgnoreCase("-simulation")) {
            TerminalChessSimulator simulator = new TerminalChessSimulator(game);

            // Pawn Promotion
            String[] whiteMoves = {"a2a4", "a4a5", "a5a6", "a6b7", "b7a8", "d", "d"};
            String[] blackMoves = {"h7h6", "h6h5", "h5h4", "h4h3", "h3g2"};
            simulator.addSimulation(whiteMoves, blackMoves);

            // Runs the simulator
            simulator.run();
        } else if (args.length > 0 && args[0].equalsIgnoreCase("-terminal")) {
            new TerminalChess(game).run();
        } else {
            new GrafikChess(game).run();
        }
    }
}
