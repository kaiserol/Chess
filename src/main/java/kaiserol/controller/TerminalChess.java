package kaiserol.controller;

import kaiserol.logic.moves.PawnPromotion;

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
        game.getBoard().toConsole();

        while (true) {
            String input = readMove();
            try {
                if (input.equalsIgnoreCase("exit")) {
                    exit();
                    break;
                } else if (input.equalsIgnoreCase("restart")) {
                    restart();
                    game.getBoard().toConsole();
                } else if (input.equalsIgnoreCase("undo")) {
                    game.undoMove();
                    game.getBoard().toConsole();
                } else if (input.equalsIgnoreCase("redo")) {
                    game.redoMove();
                    game.getBoard().toConsole();
                } else {
                    game.executeMove(input);
                    game.getBoard().toConsole();
                    handleGameState();
                }
            } catch (Exception e) {
                printlnError(e.getMessage());
            }
        }

        scanner.close();
    }

    @Override
    public PawnPromotion.Choice waitForPromotionChoice() {
        return readPromotionChoice(scanner, this::printMessage);
    }

    private String readMove() {
        printMessage(game.getCurrentSide() + "'s turn: ");
        return scanner.nextLine().trim();
    }

    private void start() {
        printlnMessage("=".repeat(40));
        printlnMessage("Welcome to the game of chess!");
        printlnMessage("- Enter moves in the format 'e2e4'");
        printlnMessage("- Enter 'restart' to start a new game");
        printlnMessage("- Enter 'undo' to undo");
        printlnMessage("- Enter 'redo' to redo");
        printlnMessage("- Enter 'exit' to quit.");
        printlnMessage("=".repeat(40));
    }

    private void restart() {
        game.buildBoard();
        printlnMessage("=".repeat(40));
        printlnMessage("Game restarted.");
        printlnMessage("=".repeat(40));
    }

    private void exit() {
        printlnMessage("Bye!");
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
            printlnMessage(result);
        }
    }
}
