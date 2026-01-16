package kaiserol.state;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.pieces.King;
import kaiserol.pieces.Piece;
import kaiserol.pieces.Rook;
import org.jetbrains.annotations.NotNull;

public class BoardSnapshot {
    private final Side currentSide;
    private final int halfMoveCount;
    private final int fullMoveCount;
    private final Move lastMove;
    private final String fen;

    public BoardSnapshot(ChessBoard board, Side currentSide, int halfMoveCount, int fullMoveCount, Move lastMove) {
        this.currentSide = currentSide;
        this.halfMoveCount = halfMoveCount;
        this.fullMoveCount = fullMoveCount;
        this.lastMove = lastMove;
        this.fen = toFEN(board, currentSide, halfMoveCount, fullMoveCount);
    }

    public static BoardSnapshot initial(ChessBoard board) {
        return new BoardSnapshot(board, Side.WHITE, 0, 1, null);
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

    public Move getLastMove() {
        return lastMove;
    }

    public String getFEN() {
        return fen;
    }

    /**
     * Converts the current board position to FEN (Forsyth-Edwards Notation) format.
     * FEN consists of six fields separated by spaces:
     * <ol>
     *     <li><b>Piece placement</b>: Rows from 8 to 1, pieces “a-zA-Z”, “/” separates rows, numbers indicate empty fields</li>
     *     <li><b>Active player</b>: 'w' for White, 'b' for Black</li>
     *     <li><b>Castling availability</b>: K/Q for White kingside/queenside, k/q for Black (or '-' if none possible)</li>
     *     <li><b>En passant target field</b>: Field behind a pawn that just moved two fields (or '-' if not available)</li>
     *     <li><b>Half-move count</b>: Number of half-moves since last capture or pawn advance (for 50-move rule)</li>
     *     <li><b>Full move count</b>: Increments after Black's move, starts at 1</li>
     * </ol>
     *
     * @param board       the current board
     * @param currentSide the side whose turn it is
     */
    private static String toFEN(ChessBoard board, Side currentSide, int halfMoveCount, int fullMoveCount) {
        return getPiecePlacement(board) + " " +
                getActivePlayer(currentSide) + " " +
                getCastlingAvailability(board) + " " +
                getEnPassantTargetField(board) + " " +
                halfMoveCount + " " +
                fullMoveCount;
    }

    private static String getPiecePlacement(ChessBoard board) {
        StringBuilder sb = new StringBuilder();
        for (int y = 8; y >= 1; y--) {
            int emptyFields = 0;
            for (int x = 1; x <= 8; x++) {
                ChessField field = board.getField(x, y);
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

    private static String getCastlingAvailability(ChessBoard board) {
        StringBuilder castling = new StringBuilder();

        // White side
        if (isValidCastlingPiece(board, King.class, 5, 1)) {
            if (isValidCastlingPiece(board, Rook.class, 8, 1)) castling.append("K");
            if (isValidCastlingPiece(board, Rook.class, 1, 1)) castling.append("Q");
        }

        // Black side
        if (isValidCastlingPiece(board, King.class, 5, 8)) {
            if (isValidCastlingPiece(board, Rook.class, 8, 8)) castling.append("k");
            if (isValidCastlingPiece(board, Rook.class, 1, 8)) castling.append("q");
        }

        return castling.isEmpty() ? "-" : castling.toString();
    }

    private static boolean isValidCastlingPiece(ChessBoard board, Class<? extends Piece> pieceClass, int x, int y) {
        ChessField field = board.getField(x, y);
        return field.isOccupied() && field.getPiece().getClass().equals(pieceClass) && !field.getPiece().hasMoved();
    }

    private static String getEnPassantTargetField(ChessBoard board) {
        ChessField enPassantField = board.getEnPassantField();
        if (enPassantField != null) return enPassantField.toString();
        return "-";
    }

    @Override
    public @NotNull String toString() {
        return fen;
    }
}
