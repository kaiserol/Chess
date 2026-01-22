package kaiserol.state;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import kaiserol.moves.PawnJump;
import kaiserol.pieces.King;
import kaiserol.pieces.Pawn;
import kaiserol.pieces.Rook;

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
        // TODO: implement castling right detection
        CastlingRights rights = new CastlingRights(
                false,
                false,
                false,
                false
        );

        return new BoardState(
                Side.WHITE,
                0,
                1,
                rights,
                null,
                null
        );
    }

    public static BoardState nextState(BoardState currentState, Move move) {
        Side nextSide = currentState.sideToMove.opposite();

        // Calculate move counts
        int halfMoveCount = calculateHalfMove(move, currentState.halfMoveCount);
        int fullMoveCount = calculateFullMove(currentState.sideToMove, currentState.fullMoveCount);

        // Calculate castling rights
        CastlingRights castlingRights = currentState.castlingRights;
        boolean wKS = castlingRights.canWhiteCastleKingSide();
        boolean wQS = castlingRights.canWhiteCastleQueenSide();
        boolean bKS = castlingRights.canBlackCastleKingSide();
        boolean bQS = castlingRights.canBlackCastleQueenSide();

        var piece = move.getStartField().getPiece();
        int startX = move.getStartField().getX();
        int startY = move.getStartField().getY();
        int targetX = move.getTargetField().getX();
        int targetY = move.getTargetField().getY();

        // 1. When the king moves, he loses both rights
        if (piece instanceof King) {
            if (piece.getSide().isWhite()) wKS = wQS = false;
            else bKS = bQS = false;
        }

        // 2. When a rook moves, it loses the corresponding right
        if (piece instanceof Rook) {
            if (piece.getSide().isWhite() && startY == 1) {
                if (startX == 8) wKS = false;
                if (startX == 1) wQS = false;
            } else if (!piece.getSide().isWhite() && startY == 8) {
                if (startX == 8) bKS = false;
                if (startX == 1) bQS = false;
            }
        }

        // 3. If a rook is captured on its starting square, that side loses the right
        if (targetY == 1) { // White side
            if (targetX == 8) wKS = false;
            if (targetX == 1) wQS = false;
        } else if (targetY == 8) {  // Black side
            if (targetX == 8) bKS = false;
            if (targetX == 1) bQS = false;
        }

        // En Passant Target
        ChessField enPassantTarget = (move instanceof PawnJump pawnJump) ? pawnJump.getEnPassantTarget() : null;

        // Create a new board state
        return new BoardState(
                nextSide,
                halfMoveCount,
                fullMoveCount,
                new CastlingRights(wKS, wQS, bKS, bQS),
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
