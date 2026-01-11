package kaiserol.controller;

import java.util.Scanner;

public class TerminalChess extends ChessController {
    private final Scanner scanner;

    public TerminalChess(Game game) {
        super(game);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        start();
        game.getBoard().printBoard();

        while (true) {
            String input = readMove();
            try {
                if (input.equalsIgnoreCase("exit")) {
                    exit();
                    break;
                } else if (input.equalsIgnoreCase("restart")) {
                    restart();
                    game.getBoard().printBoard();
                } else if (input.equalsIgnoreCase("undo")) {
                    game.undoMove();
                    game.getBoard().printBoard();
                } else {
                    game.executeMove(input);
                    game.getBoard().printBoard();
                    handleGameState();
                }
            } catch (Exception e) {
                printError(e.getMessage());
            }
        }

        scanner.close();
    }

    private String readMove() {
        System.out.print(game.getCurrentSide() + "'s turn: ");
        return scanner.nextLine().trim();
    }

    private void start() {
        printMessage("=".repeat(40));
        printMessage("Welcome to the game of chess!");
        printMessage("- Enter moves in the format 'e2e4'");
        printMessage("- Enter 'restart' to start a new game");
        printMessage("- Enter 'undo' to undo");
        printMessage("- Enter 'exit' to quit.");
        printMessage("=".repeat(40));
    }

    private void restart() {
        game.buildBoard();
        printMessage("=".repeat(40));
        printMessage("Game restarted.");
        printMessage("=".repeat(40));
    }

    private void exit() {
        printMessage("Bye!");
    }

    private void handleGameState() {
        String result = switch (game.getGameState()) {
            case CHECKMATE -> "CHECKMATE" + game.getCurrentSide().opposite() + " wins.";
            case CHECK -> "CHECK! " + game.getCurrentSide().opposite() + " is in check.";
            case STALEMATE -> "STALEMATE! Draw.";
            case DRAW -> "DRAW! Draw.";
            default -> null;
        };

        if (result != null) {
            printMessage(result);
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private void printError(String error) {
        System.out.println("Error: " + error);
    }
}
