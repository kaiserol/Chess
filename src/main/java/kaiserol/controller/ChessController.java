package kaiserol.controller;

import kaiserol.logic.moves.PawnPromotion;

import java.util.Scanner;
import java.util.function.Consumer;

public abstract class ChessController {
    protected final Game game;

    public ChessController(Game game) {
        this.game = game;
    }

    public abstract void run();

    public abstract PawnPromotion.Choice waitForPromotionChoice();

    protected PawnPromotion.Choice readPromotionChoice(Scanner scanner, Consumer<String> output) {
        while (true) {
            output.accept("Pawn promotion! Choose a piece (Q, R, B, N): ");

            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Input stream closed during pawn promotion.");
            }

            String input = scanner.nextLine().trim().toUpperCase();
            PawnPromotion.Choice choice = switch (input) {
                case "Q" -> PawnPromotion.Choice.QUEEN;
                case "R" -> PawnPromotion.Choice.ROOK;
                case "B" -> PawnPromotion.Choice.BISHOP;
                case "N" -> PawnPromotion.Choice.KNIGHT;
                default -> null;
            };

            if (choice != null) {
                return choice;
            }
            output.accept("Invalid promotion choice.\n");
        }
    }

    protected void printMessage(String message) {
        System.out.print(message);
    }

    protected void printlnMessage(String message) {
        System.out.println(message);
    }

    protected void printlnError(String error) {
        System.out.println("Error: " + error);
    }
}
