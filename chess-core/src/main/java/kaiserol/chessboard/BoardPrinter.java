package kaiserol.chessboard;

import kaiserol.cli.CLIStyle;
import kaiserol.moves.Move;
import kaiserol.pieces.Piece;

import java.awt.*;
import java.util.List;

public class BoardPrinter {

    public static String formatWithHighlights(ChessBoard board, ChessField startField) {
        // Collect all target fields from legal moves of the selected piece
        List<ChessField> targetFields = !startField.isOccupied() ? List.of() :
                startField.getPiece().getLegalMoves().stream().map(Move::getTargetField).toList();

        ChessFieldHandler handler = field -> {
            // Highlight the selected starting field
            if (field.equals(startField)) {
                return "[%s]".formatted(field.isOccupied() ? field.getPiece().getSymbol() : " ");
            }

            // Highlight legal target fields (Capture vs. target field)
            if (targetFields.contains(field)) {
                return field.isOccupied() ? "(%s)".formatted(field.getPiece().getSymbol()) : " x ";
            }

            // Normal field
            char pieceSymbol = field.isOccupied() ? field.getPiece().getWhiteSymbol() : ' ';
            return " %s ".formatted(pieceSymbol);
        };

        return formatWithAnsiCodes(board, handler);
    }

    public static String format(ChessBoard board, boolean useAnsiCodes) {
        ChessFieldHandler handler = field -> {
            char pieceSymbol;
            if (field.isOccupied()) {
                Piece piece = field.getPiece();
                pieceSymbol = useAnsiCodes ? piece.getWhiteSymbol() : piece.getSymbol();
            } else {
                pieceSymbol = ' ';
            }
            return " %s ".formatted(pieceSymbol);
        };

        if (useAnsiCodes) return formatWithAnsiCodes(board, handler);
        return format(board, handler);
    }

    private static String formatWithAnsiCodes(ChessBoard board, ChessFieldHandler handler) {
        StringBuilder builder = new StringBuilder();

        for (int y = 8; y >= 1; y--) {
            builder.append(y).append(" ");

            for (int x = 1; x <= 8; x++) {
                ChessField field = board.getField(x, y);
                String text = handler.handle(field);

                boolean whiteField = (x + y) % 2 != 0;
                Color background = whiteField ? new Color(217, 228, 232) : new Color(119, 154, 176);
                Color foreground = ChessBoard.isOccupiedBySide(field, Side.WHITE) ? Color.WHITE : Color.BLACK;
                builder.append(CLIStyle.text(text).background(background).foreground(foreground));
            }

            if (y == 8) builder.append(" [%s]".formatted(board.getMoveCount()));
            builder.append("\n");
        }

        builder.append("  ");
        for (int x = 1; x <= 8; x++) {
            builder.append(" %s ".formatted((char) ('a' + (x - 1))));
        }

        return builder.toString();
    }

    private static String format(ChessBoard board, ChessFieldHandler handler) {
        StringBuilder builder = new StringBuilder();
        builder.append("  +").append("---+".repeat(8)).append("\n");

        for (int y = 8; y >= 1; y--) {
            builder.append(y).append(" |");

            for (int x = 1; x <= 8; x++) {
                ChessField field = board.getField(x, y);
                builder.append(handler.handle(field));
                builder.append("|");
            }
            if (y == 8) builder.append(" [%s]".formatted(board.getMoveCount()));
            builder.append("\n");
        }

        builder.append("  +").append("---+".repeat(8)).append("\n");
        builder.append("   ");
        for (int x = 1; x <= 8; x++) {
            builder.append(" %s  ".formatted((char) ('a' + (x - 1))));
        }

        return builder.toString();
    }

    private interface ChessFieldHandler {
        String handle(ChessField field);
    }
}