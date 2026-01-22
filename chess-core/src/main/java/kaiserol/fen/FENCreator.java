package kaiserol.fen;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;

public class FENCreator {
    /**
     * Converts the chess board position to FEN (Forsyth-Edwards Notation) format.
     * FEN consists of six fields separated by spaces:
     * <ol>
     *     <li><b>Piece placement</b>: Rows from 8 to 1, pieces "a-zA-Z", "/" separates rows, numbers indicate empty fields</li>
     *     <li><b>Active player</b>: 'w' for White, 'b' for Black</li>
     *     <li><b>Castling availability</b>: K/Q for White kingside/queenside, k/q for Black (or '-' if none possible)</li>
     *     <li><b>En passant target</b>: Field behind a pawn that just moved two fields (or '-' if not available)</li>
     *     <li><b>Half-move count</b>: Number of half-moves since last capture or pawn advance (for 50-move rule)</li>
     *     <li><b>Full-move count</b>: Increments after Black's move, starts at 1</li>
     * </ol>
     *
     * @param board      the chess board
     * @param sideToMove the side whose turn it is
     * @param halfMoveCount the number of half-moves since the last capture or pawn advance
     * @param fullMoveCount the number of full-moves since the beginning of the game
     */
    public static String toFEN(ChessBoard board, Side sideToMove, int halfMoveCount, int fullMoveCount) {
        return toPositionalFEN(board, sideToMove) + " " +
                halfMoveCount + " " +
                fullMoveCount;
    }

    /**
     * Converts the chess board position to FEN (Forsyth-Edwards Notation) format, without the move count.
     *
     * <ol>
     *     <li><b>Piece placement</b>: Rows from 8 to 1, pieces "a-zA-Z", "/" separates rows, numbers indicate empty fields</li>
     *     <li><b>Active player</b>: 'w' for White, 'b' for Black</li>
     *     <li><b>Castling availability</b>: K/Q for White kingside/queenside, k/q for Black (or '-' if none possible)</li>
     *     <li><b>En passant target</b>: Field behind a pawn that just moved two fields (or '-' if not available)</li>
     * </ol>
     *
     * @param board      the chess board
     * @param sideToMove the side whose turn it is
     */
    public static String toPositionalFEN(ChessBoard board, Side sideToMove) {
        return getPiecePlacement(board) + " " +
                getActivePlayer(sideToMove) + " " +
                getCastlingAvailability(board) + " " +
                getEnPassantTarget(board);
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

    private static String getActivePlayer(Side sideToMove) {
        return sideToMove.isWhite() ? "w" : "b";
    }

    private static String getCastlingAvailability(ChessBoard board) {
        StringBuilder castling = new StringBuilder();
        if (board.canWhiteCastleKingSide()) castling.append("K");
        if (board.canWhiteCastleQueenSide()) castling.append("Q");
        if (board.canBlackCastleKingSide()) castling.append("k");
        if (board.canBlackCastleQueenSide()) castling.append("q");
        return castling.isEmpty() ? "-" : castling.toString();
    }

    private static String getEnPassantTarget(ChessBoard board) {
        ChessField enPassantTarget = board.getEnPassantTarget();
        return enPassantTarget == null ? "-" : enPassantTarget.toString();
    }
}
