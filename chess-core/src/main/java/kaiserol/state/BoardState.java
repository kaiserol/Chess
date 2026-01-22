package kaiserol.state;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.moves.PawnJump;
import kaiserol.pieces.King;
import kaiserol.pieces.Pawn;
import kaiserol.pieces.Piece;
import kaiserol.pieces.Rook;

import java.util.List;

public class BoardState {
    // Turn information
    private final Side sideToMove;
    private final int halfMoveCount;
    private final int fullMoveCount;

    // Castling rights
    private final CastlingRights castlingRights;

    // En passant Target
    private final ChessField enPassantTarget;

    // Move that leads into this board state
    private final Move move;

    // FEN representation
    private String positionalFEN;
    private String FEN;

    // =========================
    // Constructor
    // =========================

    private BoardState(
            Side sideToMove,
            int halfMoveCount,
            int fullMoveCount,
            CastlingRights castlingRights,
            ChessField enPassantTarget,
            Move move
    ) {
        this.sideToMove = sideToMove;
        this.halfMoveCount = halfMoveCount;
        this.fullMoveCount = fullMoveCount;
        this.castlingRights = castlingRights;
        this.enPassantTarget = enPassantTarget;
        this.move = move;
    }

    public static BoardState initial() {
        return new BoardState(
                Side.WHITE,
                0,
                1,
                new CastlingRights(),
                null,
                null
        );
    }

    /**
     * Creates a board state based on the current ChessBoard.
     * Ideal for FEN imports or manual setups in unit tests.
     */
    public static BoardState fromBoard(ChessBoard board) {
        CastlingRights castlingRights = new CastlingRights();

        // Überprüfe, ob König noch Rochade-Rechte hat
        for (Side side : Side.values()) {
            List<Piece> pieces = board.getPieces(side);
            King king = board.getKing(pieces);

            if (king != null) {
                int kingX = king.getField().getX();
                int kingY = king.getField().getY();
                int castlingRow = King.getCastlingRow(side);

                // 1. When the king moved, he loses both rights
                if (kingX != King.CASTLING_COLUMN_KING || kingY != castlingRow) {
                    if (side.isWhite()) castlingRights.revokeWhiteCastle();
                    else castlingRights.revokeBlackCastle();
                    continue;
                }

                // 2. When a rook moved, it loses the corresponding right
                Piece rookKingSide = board.getField(King.CASTLING_COLUMN_ROOK_KING_SIDE, castlingRow).getPiece();
                if (!(rookKingSide instanceof Rook)) {
                    if (side.isWhite()) castlingRights.revokeWhiteCastleKingSide();
                    else castlingRights.revokeBlackCastleKingSide();
                }

                Piece rookQueenSide = board.getField(King.CASTLING_COLUMN_ROOK_QUEEN_SIDE, castlingRow).getPiece();
                if (!(rookQueenSide instanceof Rook)) {
                    if (side.isWhite()) castlingRights.revokeWhiteCastleQueenSide();
                    else castlingRights.revokeBlackCastleQueenSide();
                }
            } else {
                if (side.isWhite()) castlingRights.revokeWhiteCastle();
                else castlingRights.revokeBlackCastle();
            }
        }

        return new BoardState(
                Side.WHITE,
                0,
                1,
                castlingRights,
                null,
                null
        );
    }

    public static BoardState nextState(BoardState currentState, Move move) {
        Side nextSide = currentState.getSideToMove().opposite();

        // Calculate move counts
        int halfMoveCount = calculateHalfMove(move, currentState.getHalfMoveCount());
        int fullMoveCount = calculateFullMove(currentState.getSideToMove(), currentState.getFullMoveCount());

        // Calculate castling rights
        CastlingRights castlingRights = currentState.getCastlingRights().copy();

        var piece = move.getStartField().getPiece();
        int startX = move.getStartField().getX();
        int startY = move.getStartField().getY();
        int targetX = move.getTargetField().getX();
        int targetY = move.getTargetField().getY();

        // 1. When the king moves, he loses both rights
        if (piece instanceof King) {
            if (piece.getSide().isWhite()) castlingRights.revokeWhiteCastle();
            else castlingRights.revokeBlackCastle();
        }

        // 2. When a rook moves, it loses the corresponding right
        if (piece instanceof Rook) {
            if (piece.getSide().isWhite() && startY == King.getCastlingRow(Side.WHITE)) {
                if (startX == King.CASTLING_COLUMN_ROOK_KING_SIDE) castlingRights.revokeWhiteCastleKingSide();
                if (startX == King.CASTLING_COLUMN_ROOK_QUEEN_SIDE) castlingRights.revokeWhiteCastleQueenSide();
            } else if (!piece.getSide().isWhite() && startY == King.getCastlingRow(Side.BLACK)) {
                if (startX == King.CASTLING_COLUMN_ROOK_KING_SIDE) castlingRights.revokeBlackCastleKingSide();
                if (startX == King.CASTLING_COLUMN_ROOK_QUEEN_SIDE) castlingRights.revokeBlackCastleQueenSide();
            }
        }

        // 3. If a rook is captured on its starting square, that side loses the right
        if (targetY == 1) { // White side
            if (targetX == King.CASTLING_COLUMN_ROOK_KING_SIDE) castlingRights.revokeWhiteCastleKingSide();
            if (targetX == King.CASTLING_COLUMN_ROOK_QUEEN_SIDE) castlingRights.revokeWhiteCastleQueenSide();
        } else if (targetY == 8) {  // Black side
            if (targetX == King.CASTLING_COLUMN_ROOK_KING_SIDE) castlingRights.revokeBlackCastleKingSide();
            if (targetX == King.CASTLING_COLUMN_ROOK_QUEEN_SIDE) castlingRights.revokeBlackCastleQueenSide();
        }

        // En Passant Target
        ChessField enPassantTarget = (move instanceof PawnJump pawnJump) ? pawnJump.getEnPassantTarget() : null;

        // Create a new board state
        return new BoardState(
                nextSide,
                halfMoveCount,
                fullMoveCount,
                castlingRights,
                enPassantTarget,
                move
        );
    }

    private static int calculateHalfMove(Move move, int halfMoveCount) {
        ChessField startField = move.getStartField();
        ChessField targetField = move.getTargetField();

        // 50-move rule: Reset after a pawn move or capture
        boolean resetHalfMove = startField.getPiece() instanceof Pawn || targetField.isOccupied();
        return resetHalfMove ? 0 : halfMoveCount + 1;
    }

    private static int calculateFullMove(Side sideToMove, int fullMoveCount) {
        // FullMove increases when it's a Black turn
        return sideToMove.isWhite() ? fullMoveCount : fullMoveCount + 1;
    }

    // =========================
    // Getters
    // =========================

    public Side getSideToMove() {
        return sideToMove;
    }

    public int getHalfMoveCount() {
        return halfMoveCount;
    }

    public int getFullMoveCount() {
        return fullMoveCount;
    }

    public CastlingRights getCastlingRights() {
        return castlingRights;
    }

    public ChessField getEnPassantTarget() {
        return enPassantTarget;
    }

    public Move getMove() {
        return move;
    }

    public String getPositionalFEN() {
        return positionalFEN;
    }

    public void setPositionalFEN(String positionalFEN) {
        this.positionalFEN = positionalFEN;
    }

    public String getFEN() {
        return FEN;
    }

    public void setFEN(String FEN) {
        this.FEN = FEN;
    }
}
