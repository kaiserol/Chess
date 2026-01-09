package kaiserol.logic;

import kaiserol.handler.Game;

public class ChessSimulator {
    public void runSimulation(String[] whiteMoves, String[] blackMoves) {
        Game game = new Game();

        String sep = "-".repeat(8);
        System.out.printf("%s Simulation started %s%n", sep, sep);

        System.out.println("Initial state of the game:");
        game.printBoard();

        int maxMoves = Math.max(whiteMoves.length, blackMoves.length);
        for (int i = 0; i < maxMoves; i++) {
            if (i < whiteMoves.length) {
                System.out.printf("White moves: %s%n", whiteMoves[i]);
                try {
                    game.executeMove(whiteMoves[i]);
                    game.printBoard();
                } catch (Exception e) {
                    System.out.println("Mistake by White: " + e.getMessage());
                    break;
                }
            }

            if (i < blackMoves.length) {
                System.out.printf("Black moves: %s%n", blackMoves[i]);
                try {
                    game.executeMove(blackMoves[i]);
                    game.printBoard();
                } catch (Exception e) {
                    System.out.println("Mistake by Black: " + e.getMessage());
                    break;
                }
            }
        }

        System.out.printf("%s Simulation ended %s%n", sep, sep);
    }
}
