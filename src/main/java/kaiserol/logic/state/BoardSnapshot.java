package kaiserol.logic.state;

import kaiserol.controller.Game;
import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.PawnJump;
import kaiserol.logic.pieces.King;
import kaiserol.logic.pieces.Piece;
import kaiserol.logic.pieces.Rook;
import org.jetbrains.annotations.NotNull;

public class BoardSnapshot {
    private final String fen;
    private final Side currentSide;
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
        this.currentSide = currentSide;
        this.halfMoveCount = halfMoveCount;
        this.fullMoveCount = fullMoveCount;
        this.fen = toFEN(chessBoard, currentSide, halfMoveCount, fullMoveCount);
    }

    public String getFEN() {
        return fen;
    }

    public Side getCurrentSide() {
        return currentSide;
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
        return getPiecePlacement(chessBoard) + " " +
                getActivePlayer(currentSide) + " " +
                getCastlingAvailability(chessBoard) + " " +
                getEnPassantTargetSquare(chessBoard) + " " +
                halfMoveCount + " " +
                fullMoveCount;
    }

    private static String getPiecePlacement(ChessBoard chessBoard) {
        StringBuilder sb = new StringBuilder();
        for (int y = 8; y >= 1; y--) {
            int emptyFields = 0;
            for (int x = 1; x <= 8; x++) {
                ChessField field = chessBoard.getField(x, y);
                if (field.isOccupied()) {
                    if (emptyFields > 0) {
                        sb.append(emptyFields);
                        emptyFields = 0;
                    }
                    sb.append(field.getPiece().getLetter());
                } else {
                    emptyFields++;
                }
            }
            if (emptyFields > 0) {
                sb.append(emptyFields);
            }
            if (y > 1) {
                sb.append("/");
            }
        }
        return sb.toString();
    }

    private static String getActivePlayer(Side currentSide) {
        return currentSide.isWhite() ? "w" : "b";
    }

    private static String getCastlingAvailability(ChessBoard chessBoard) {
        StringBuilder castling = new StringBuilder();

        // White side
        if (isValidCastlingPiece(chessBoard, King.class, 5, 1)) {
            if (isValidCastlingPiece(chessBoard, Rook.class, 8, 1)) castling.append("K");
            if (isValidCastlingPiece(chessBoard, Rook.class, 1, 1)) castling.append("Q");
        }

        // Black side
        if (isValidCastlingPiece(chessBoard, King.class, 5, 8)) {
            if (isValidCastlingPiece(chessBoard, Rook.class, 8, 8)) castling.append("k");
            if (isValidCastlingPiece(chessBoard, Rook.class, 1, 8)) castling.append("q");
        }

        return castling.isEmpty() ? "-" : castling.toString();
    }

    private static boolean isValidCastlingPiece(ChessBoard chessBoard, Class<? extends Piece> pieceClass, int x, int y) {
        ChessField field = chessBoard.getField(x, y);
        return field.isOccupied() && field.getPiece().getClass().equals(pieceClass) && !field.getPiece().hasMoved();
    }

    private static String getEnPassantTargetSquare(ChessBoard chessBoard) {
        Move lastMove = chessBoard.getLastMove();
        if (lastMove instanceof PawnJump jump) {
            return jump.getEnPassantField().toString();
        }
        return "-";
    }

    @Override
    public @NotNull String toString() {
        return fen;
    }
}
