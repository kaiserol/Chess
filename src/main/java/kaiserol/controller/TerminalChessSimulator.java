package kaiserol.controller;

// TODO: Should also extend ChessController
public class TerminalChessSimulator {
    public void runSimulation(String[] whiteMoves, String[] blackMoves) {
        Game game = new Game();

        String SEP = "-".repeat(8);
        printMessage(SEP + " Simulation started " + SEP);
        printMessage("Initial state of the game:");
        game.getBoard().printBoard();

        int maxMoves = Math.max(whiteMoves.length, blackMoves.length);
        for (int i = 0; i < maxMoves; i++) {
            if (i < whiteMoves.length) {
                printMessage("White moves: " + whiteMoves[i]);
                try {
                    game.executeMove(whiteMoves[i]);
                    game.getBoard().printBoard();
                } catch (Exception e) {
                    printError(e.getMessage());
                    break;
                }
            }

            if (i < blackMoves.length) {
                printMessage("Black moves: " + blackMoves[i]);
                try {
                    game.executeMove(blackMoves[i]);
                    game.getBoard().printBoard();
                } catch (Exception e) {
                    printError(e.getMessage());
                    break;
                }
            }
        }

        printMessage(SEP + " Simulation ended " + SEP);
    }

    private void printMessage(String msg) {
        System.out.println(msg);
    }

    private void printError(String errorMsg) {
        System.out.println("Error: " + errorMsg);
    }
}
