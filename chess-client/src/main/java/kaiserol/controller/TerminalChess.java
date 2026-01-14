package kaiserol.controller;

import kaiserol.ChessController;
import kaiserol.Game;
import kaiserol.controller.command.*;
import kaiserol.moves.PawnPromotion;

import java.util.Scanner;

public class TerminalChess extends ChessController {
    private final Scanner scanner;
    private final CommandRegistry commandRegistry;
    private boolean running;

    public TerminalChess(Game game) {
        super(game);
        this.scanner = new Scanner(System.in);
        this.commandRegistry = new CommandRegistry();
        this.running = false;
        registerCommands();
    }

    private void registerCommands() {
        commandRegistry.add(new ExitCommand(this::exitGame));
        commandRegistry.add(new RestartCommand(game));
        commandRegistry.add(new UndoCommand(game));
        commandRegistry.add(new RedoCommand(game));
        commandRegistry.add(new HelpCommand(commandRegistry, this::printlnMessage));
    }

    @Override
    public void run() {
        if (running) return;
        else running = true;

        startGame();
        game.getBoard().toConsole();

        while (running) {
            String input = readInput();
            Command command = getCommand(input);

            try {
                command.execute();

                // Print board after each move
                if (command instanceof MoveCommand || command instanceof RestartCommand ||
                        command instanceof UndoCommand || command instanceof RedoCommand) {
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
    public PawnPromotion.Choice getPromotionChoice() {
        return readPromotionChoice(scanner, this::printMessage);
    }

    private String readInput() {
        printMessage(game.getCurrentSide() + "'s turn: ");
        return scanner.nextLine().trim();
    }

    private Command getCommand(String input) {
        return commandRegistry.resolve(input)
                .orElse(input.matches("\\w\\d\\w\\d") ?
                        new MoveCommand(game, input) :
                        new InvalidCommand(input, this::printlnError));
    }

    private void startGame() {
        printlnMessage("Welcome to Chess");
        printlnMessage("=".repeat(40));
        printlnMessage("Type 'help' for a list of commands.");
        printlnMessage(".".repeat(40));
        printlnMessage("Move format: <from><to> (e.g. e2e4)");
        printlnMessage("Valid squares: a1 to h8");
        printlnMessage("=".repeat(40));
    }

    private void exitGame() {
        running = false;
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
