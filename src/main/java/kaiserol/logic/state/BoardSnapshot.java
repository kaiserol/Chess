package kaiserol.logic.state;

import kaiserol.controller.Game;
import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import org.jetbrains.annotations.NotNull;

public class BoardSnapshot {
    private final String fen;
    private final int halfMoveCount;
    private final int fullMoveCount;

    public BoardSnapshot(Game game) {
        this(
                game.getBoard(),
                game.getCurrentSide(),
                game.getHalfMoveCount(),
                game.getFullMoveCount()
        );
    }

    public BoardSnapshot(ChessBoard chessBoard, Side currentSide, int halfMoveCount, int fullMoveCount) {
        this.halfMoveCount = halfMoveCount;
        this.fullMoveCount = fullMoveCount;
        this.fen = toFEN(chessBoard, currentSide, halfMoveCount, fullMoveCount);
    }

    public String getFEN() {
        return fen;
    }

    public int getHalfMoveCount() {
        return halfMoveCount;
    }

    public int getFullMoveCount() {
        return fullMoveCount;
    }

    /**
     * Converts the current board position to FEN (Forsyth-Edwards Notation) format.
     * FEN consists of six fields separated by spaces:
     * <ol>
     *     <li><b>Piece placement</b>: Rows from 8 to 1, pieces “a-zA-Z”, “/” separates rows, numbers indicate empty squares</li>
     *     <li><b>Active player</b>: 'w' for White, 'b' for Black</li>
     *     <li><b>Castling availability</b>: K/Q for White kingside/queenside, k/q for Black (or '-' if none possible)</li>
     *     <li><b>En passant target square</b>: Square behind a pawn that just moved two squares (or '-' if not available)</li>
     *     <li><b>Half-move count</b>: Number of half-moves since last capture or pawn advance (for 50-move rule)</li>
     *     <li><b>Full move count</b>: Increments after Black's move, starts at 1</li>
     * </ol>
     *
     * @param chessBoard  the current board
     * @param currentSide the side whose turn it is
     */
    private static String toFEN(ChessBoard chessBoard, Side currentSide, int halfMoveCount, int fullMoveCount) {
        StringBuilder fen = new StringBuilder();

        // 1. Piece placement
        for (int y = 8; y >= 1; y--) {
            int emptyFields = 0;

            for (int x = 1; x <= 8; x++) {
                ChessField field = chessBoard.getField(x, y);
                if (field.isOccupied()) {
                    if (emptyFields > 0) {
                        fen.append(emptyFields);
                        emptyFields = 0;
                    }
                    fen.append(field.getPiece().getLetter());
                } else {
                    emptyFields++;
                }
            }
            if (emptyFields > 0) {
                fen.append(emptyFields);
            }
            if (y > 1) {
                fen.append("/");
            }
        }

        // 2. Active player
        fen.append(" ").append(currentSide.isWhite() ? "w" : "b");

        // TODO: 3. Castling availability
        fen.append(" -");

        // TODO: 4. En passant target square
        fen.append(" -");

        // 5. Half-move count
        fen.append(" ").append(halfMoveCount);

        // 6. Full move count
        fen.append(" ").append(fullMoveCount);

        return fen.toString();
    }

    @Override
    public @NotNull String toString() {
        return fen;
    }
}
