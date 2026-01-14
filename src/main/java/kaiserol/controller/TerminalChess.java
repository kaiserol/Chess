package kaiserol.controller;

import kaiserol.logic.ChessController;
import kaiserol.logic.Game;
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
        startGame();
        game.getBoard().toConsole();

        while (true) {
            String trimmed = readInput().trim().toLowerCase();
            try {
                if (trimmed.equals("exit")) {
                    exitGame();
                    break;
                } else if (trimmed.equals("restart")) {
                    restartGame();
                    game.getBoard().toConsole();
                } else if (trimmed.equals("undo")) {
                    game.undoMove();
                    game.getBoard().toConsole();
                } else if (trimmed.equals("redo")) {
                    game.redoMove();
                    game.getBoard().toConsole();
                } else if (trimmed.matches("(\\w|\\d){4}")) {
                    game.executeMove(trimmed);
                    game.getBoard().toConsole();
                    handleGameState();
                } else {
                    printlnError("Invalid input.");
                }
            } catch (Exception e) {
                printlnError(e.getMessage());
            }
        }

        scanner.close();
    }

    @Override
    public PawnPromotion.Choice getPromotionChoice() {
        return readPromotionChoice(scanner, this::printMessage);
    }

    private String readInput() {
        printMessage(game.getCurrentSide() + "'s turn: ");
        return scanner.nextLine();
    }

    private void startGame() {
        printlnMessage("=".repeat(40));
        printlnMessage("Welcome to Chess");
        printlnMessage("");
        printlnMessage("Move format: <from><to> (e.g. e2e4)");
        printlnMessage("Valid squares: a1 to h8");
        printlnMessage("");
        printlnMessage("Commands:");
        printlnMessage("  restart  - start a new game");
        printlnMessage("  undo     - undo last move");
        printlnMessage("  redo     - redo last move");
        printlnMessage("  exit     - quit the game");
        printlnMessage("=".repeat(40));
    }

    private void restartGame() {
        game.reset();
        printlnMessage("=".repeat(40));
        printlnMessage("Game restarted.");
        printlnMessage("=".repeat(40));
    }

    private void exitGame() {
        printlnMessage("Bye!");
    }

    private void handleGameState() {
        String result = switch (game.getGameState()) {
            case CHECKMATE -> "CHECKMATE! " + game.getCurrentSide().opposite() + " wins.";
            case CHECK -> "CHECK! " + game.getCurrentSide() + " is in check.";
            case STALEMATE -> "DRAW! Stalemate!";
            case DRAW_INSUFFICIENT_MATERIAL -> "DRAW! Insufficient material.";
            case DRAW_THREEFOLD_REPETITION -> "DRAW! Threefold repetition.";
            case DRAW_50_MOVE_RULE -> "DRAW! 50-move rule reached.";
            default -> null;
        };

        if (result != null) {
            printlnMessage(result);
        }
    }
}
