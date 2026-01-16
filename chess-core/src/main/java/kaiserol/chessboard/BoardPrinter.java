package kaiserol.chessboard;

import kaiserol.moves.Move;

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
                return field.isOccupied() ? "(%s)".formatted(field.getPiece().getSymbol()) : " o ";
            }

            // Normal field
            char pieceSymbol = field.isOccupied() ? field.getPiece().getSymbol() : ' ';
            return " %s ".formatted(pieceSymbol);
        };

        return format(board, handler);
    }

    public static String format(ChessBoard board) {
        ChessFieldHandler handler = field -> {
            char pieceSymbol = field.isOccupied() ? field.getPiece().getSymbol() : ' ';
            return " %s ".formatted(pieceSymbol);
        };

        return format(board, handler);
    }

    private static String format(ChessBoard board, ChessFieldHandler handler) {
        StringBuilder builder = new StringBuilder();
        builder.append("  ").append("+---".repeat(8)).append("+\n");

        for (int y = 8; y >= 1; y--) {
            builder.append(y).append(" ");

            for (int x = 1; x <= 8; x++) {
                ChessField field = board.getField(x, y);
                builder.append("|");
                builder.append(handler.handle(field));
            }
            builder.append("|\n");
        }

        builder.append("  ").append("+---".repeat(8)).append("+\n  ");
        for (int x = 1; x <= 8; x++) {
            builder.append("  %s ".formatted((char) ('a' + (x - 1))));
        }
        builder.append(" ");

        return builder.toString();
    }

    private interface ChessFieldHandler {
        String handle(ChessField field);
    }
}