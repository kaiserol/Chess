package kaiserol.controller.command;

import kaiserol.Game;
import kaiserol.chessboard.BoardPrinter;
import kaiserol.chessboard.ChessField;
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

    public LegalMovesCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull Game game) {
        super(out, err);
        this.game = game;
    }

    @Override
    public void execute(@NotNull String[] args) throws Exception {
        if (matchesOptions(args, new String[]{})) printAllLegalMoves();
        else if (matchesOptions(args, new String[]{"-count"})) printAllLegalMovesCount();
        else if (matchesOptions(args, new String[]{"\\w\\d"})) printAllLegalMovesFrom(args[0]);
        else if (matchesOptions(args, new String[]{"\\w\\d", "-board"})) printBoardWithAllLegalMovesFrom(args[0]);
        else err.accept("The option%s '%s' %s invalid for the command 'lm'.".formatted(
                    args.length > 1 ? "s" : "",
                    String.join(" ", args),
                    args.length > 1 ? "are" : "is"));
    }

    @Override
    public String keyWord() {
        return "lm";
    }

    @Override
    public String description() {
        return "Displays all legal moves.";
    }

    @Override
    public @NotNull Map<String, String> options() {
        return Map.of(
                "-count", "Returns the total number of legal moves.",
                "<from>", "Lists all legal moves from the starting field (e.g. e2).",
                "<from> -board", "Prints the board and highlights all legal moves from the starting field."
        );
    }

    private void printAllLegalMoves() {
        Map<Piece, List<Move>> map = getAllLegalMoves();

        List<Piece> sortedPieces = map.keySet().stream().sorted(Comparator.comparing(Piece::getField)).toList();
        for (Piece piece : sortedPieces) {
            out.accept("  %s on %s: %s".formatted(piece.getSymbol(), piece.getField(), map.get(piece)));
        }
    }

    private void printAllLegalMovesCount() {
        int count = getAllLegalMoves().values().stream().mapToInt(List::size).sum();
        out.accept("" + count);
    }

    private Map<Piece, List<Move>> getAllLegalMoves() {
        Map<Piece, List<Move>> map = new HashMap<>();
        List<Piece> pieces = game.getBoard().getPieces(game.getSideToMove());

        for (Piece piece : pieces) {
            List<Move> legalMoves = piece.getLegalMoves();
            if (!legalMoves.isEmpty()) {
                map.put(piece, List.copyOf(legalMoves));
            }
        }
        return map;
    }

    private void printAllLegalMovesFrom(@NotNull String coord) {
        Piece piece = getPieceFrom(coord);
        if (piece == null) return;

        List<Move> legalMoves = piece.getLegalMoves();
        out.accept("" + legalMoves);
    }

    private void printBoardWithAllLegalMovesFrom(@NotNull String coord) {
        Piece piece = getPieceFrom(coord);
        if (piece == null) return;

        String boardView = BoardPrinter.formatWithHighlights(game.getBoard(), piece.getField());
        out.accept(boardView);
    }

    private Piece getPieceFrom(@NotNull String coord) {
        try {
            ChessField field = game.getBoard().getField(coord);
            if (!field.isOccupied()) {
                err.accept("There is no piece on '%s'.".formatted(coord));
                return null;
            }

            Piece piece = field.getPiece();
            if (piece.getSide() != game.getSideToMove()) {
                err.accept("The piece on '%s' does not belong to the current side.".formatted(coord));
                return null;
            }
            return piece;
        } catch (Exception e) {
            err.accept(e.getMessage());
            return null;
        }
    }
}