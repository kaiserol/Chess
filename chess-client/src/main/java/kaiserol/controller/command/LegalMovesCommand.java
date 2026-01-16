package kaiserol.controller.command;

import kaiserol.Game;
import kaiserol.moves.Move;
import kaiserol.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LegalMovesCommand extends Command {
    private final Game game;
    private final Consumer<String> output;

    public LegalMovesCommand(Game game, Consumer<String> output) {
        this.game = game;
        this.output = output;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 0) {
            printAllLegalMoves();
        } else {
            if (matchesOptions(args, new String[]{"-count"})) printAllLegalMovesCount();
            else if (matchesOptions(args, new String[]{"<from>"})) printAllLegalMovesFrom(args[0]);
            else if (matchesOptions(args, new String[]{"<from>", "-board"})) printBoardWithAllLegalMovesFrom(args[0]);
            else throw new IllegalArgumentException("Invalid argument '%s'".formatted(String.join(" ", args)));
        }
    }

    @Override
    public String keyword() {
        return "lm";
    }

    @Override
    public String description() {
        return "Displays all legal moves";
    }

    @Override
    public @NotNull Map<String, String> options() {
        return Map.of(
                "-count", "Returns the total number of legal moves",
                "<from>", "Lists all legal moves from the given square (e.g. e2)",
                "<from> -board", "Prints the board and highlights all legal moves starting from the specified square"
        );
    }

    private void printAllLegalMoves() {
        Map<Piece, List<Move>> map = getAllLegalMoves();

        output.accept("Legal moves: ");

        List<Piece> sortedPieces = map.keySet().stream().sorted(Comparator.comparing(Piece::getField)).toList();
        for (Piece piece : sortedPieces) {
            output.accept("  %s at %s: %s".formatted(piece.getSymbol(), piece.getField(), map.get(piece)));
        }
    }

    private void printAllLegalMovesCount() {
        int count = getAllLegalMoves().values().stream().mapToInt(List::size).sum();
        output.accept("Legal moves count: " + count);
    }

    private Map<Piece, List<Move>> getAllLegalMoves() {
        Map<Piece, List<Move>> map = new HashMap<>();
        List<Piece> pieces = game.getBoard().getPieces(game.getCurrentSide());

        for (Piece piece : pieces) {
            List<Move> legalMoves = piece.getLegalMoves();
            if (!legalMoves.isEmpty()) {
                map.put(piece, List.copyOf(legalMoves));
            }
        }
        return map;
    }

    private void printAllLegalMovesFrom(String from) {
        // TODO: Implement this method
    }

    private void printBoardWithAllLegalMovesFrom(String from) {
        // TODO: Implement this method
    }
}