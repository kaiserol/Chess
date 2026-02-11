package kaiserol;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Side;
import kaiserol.moves.PawnPromotion;
import kaiserol.moves.PawnPromotionProvider;
import kaiserol.pieces.*;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

public abstract class ChessController implements PawnPromotionProvider {
    protected final Game game;

    public ChessController(Game game) {
        this.game = game;
        // Register the controller as a promotion provider
        this.game.getBoard().setPromotionProvider(this);
    }

    public abstract void run();

    protected PawnPromotion.Choice readPromotionChoice(Scanner scanner, Consumer<String> out, Consumer<String> err) {
        final ChessBoard board = game.getBoard();
        final Side side = game.getSideToMove();

        Piece[] promotions = {new Queen(board, side), new Rook(board, side), new Bishop(board, side), new Knight(board, side)};
        String[] selection = Arrays.stream(promotions)
                .map(p -> "%s (%s)".formatted(p.getSymbol(), p.getLetter())).toArray(String[]::new);

        while (true) {
            out.accept("Pawn promotion! Choose a piece %s: ".formatted(Arrays.toString(selection)));

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
            err.accept("Invalid promotion choice.\n");
        }
    }

    protected final void printMessage(String message) {
        System.out.print(message);
    }

    protected final void printlnMessage(String message) {
        System.out.println(message);
    }

    protected final void printlnError(String error) {
        System.out.println("Error: " + error);
    }

    protected final void printError(Object error) {
        System.out.print("Error: " + error);
    }

    public final void printBoard() {
        System.out.println(game.getBoard().formatUsingAnsiCodes());
    }
}
