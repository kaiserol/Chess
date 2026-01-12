package kaiserol;

import kaiserol.controller.*;

public class Main {
    private static ChessController controller;

    public static void main(String[] args) {
        Game game = new Game();

        if (args.length > 0 && args[0].equalsIgnoreCase("-simulation")) {
            TerminalChessSimulator simulator = new TerminalChessSimulator(game);

            // Pawn Promotion
            String[] whiteMoves = {"a2a4", "a4a5", "a5a6", "a6b7", "b7a8"};
            String[] blackMoves = {"h7h6", "h6h5", "h5h4", "h4h3", "h3g2"};
            simulator.addSimulation(whiteMoves, blackMoves);

            controller = simulator;
        } else if (args.length > 0 && args[0].equalsIgnoreCase("-terminal")) {
            controller = new TerminalChess(game);
        } else {
            controller = new GrafikChess(game);
        }

        // Starts the controller
        controller.run();
    }

    public static ChessController getController() {
        return controller;
    }
}
