package kaiserol.chessboard;

import kaiserol.fen.FENCreator;
import kaiserol.moves.Move;
import kaiserol.moves.PawnPromotionProvider;
import kaiserol.pieces.*;
import kaiserol.state.BoardState;
import kaiserol.state.CastlingRights;
import kaiserol.state.GameState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private final ChessField[][] fields;
    private PawnPromotionProvider pawnPromotionProvider;

    // Board history
    private final BoardHistory boardHistory;

    public ChessBoard() {
        this.fields = new ChessField[8][8];
        initializeFields();

        // Initialize history
        this.boardHistory = new BoardHistory();
        synchronizeBoardState();
    }

    public PawnPromotionProvider getPromotionProvider() {
        return pawnPromotionProvider;
    }

    public void setPromotionProvider(PawnPromotionProvider provider) {
        this.pawnPromotionProvider = provider;
    }

    public BoardState getCurrentState() {
        return boardHistory.current();
    }

    public boolean canWhiteCastleKingSide() {
        CastlingRights castlingRights = getCurrentState().getCastlingRights();
        return castlingRights.canWhiteCastleKingSide();
    }

    public boolean canWhiteCastleQueenSide() {
        CastlingRights castlingRights = getCurrentState().getCastlingRights();
        return castlingRights.canWhiteCastleQueenSide();
    }

    public boolean canBlackCastleKingSide() {
        CastlingRights castlingRights = getCurrentState().getCastlingRights();
        return castlingRights.canBlackCastleKingSide();
    }

    public boolean canBlackCastleQueenSide() {
        CastlingRights castlingRights = getCurrentState().getCastlingRights();
        return castlingRights.canBlackCastleQueenSide();
    }

    public ChessField getEnPassantTarget() {
        return getCurrentState().getEnPassantTarget();
    }

    public String getMoveCount() {
        int moveCount = boardHistory.getMoveCount();
        return moveCount > 0 ? "%02d".formatted(moveCount) : "INIT";
    }

    private void initializeFields() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                initializeField(x, y);
            }
        }
    }

    private void initializeField(int x, int y) {
        checkCoordinates(x, y);
        this.fields[x - 1][y - 1] = new ChessField(x, y);
    }

    public ChessField getField(int x, int y) {
        checkCoordinates(x, y);
        return this.fields[x - 1][y - 1];
    }

    public ChessField getField(@NotNull String coord) {
        String c = coord.trim().toLowerCase();
        if (!c.matches("[a-h][1-8]")) {
            throw new CoordinateException("Invalid coordinate '%s'. Expected <column><row> (e.g. a1, h8).".formatted(coord));
        }

        char col = c.charAt(0);
        char row = c.charAt(1);

        int x = col - 'a' + 1;
        int y = row - '1' + 1;
        return getField(x, y);
    }

    public void occupyFieldAndSync(ChessField field, Piece piece) {
        occupyField(field, piece);
        synchronizeBoardState();
    }

    public static void occupyField(ChessField field, Piece piece) {
        if (field != null) {
            if (field.isOccupied()) {
                Piece oldPiece = field.getPiece();
                oldPiece.removeField();
            }
            field.setPiece(piece);
        }

        if (piece != null) {
            if (piece.hasField()) {
                ChessField oldField = piece.getField();
                oldField.removePiece();
            }
            piece.setField(field);
        }
    }

    public static void clearField(ChessField field) {
        if (field == null) return;

        if (field.isOccupied()) {
            Piece oldPiece = field.getPiece();
            oldPiece.removeField();
            field.removePiece();
        }
    }

    public static boolean isOccupiedBySide(ChessField field, Side side) {
        return field.isOccupied() && field.getPiece().getSide() == side;
    }

    private static void checkCoordinates(int x, int y) {
        if (x < 1 || x > 8) throw new CoordinateException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new CoordinateException("y must be between 1 and 8");
    }

    public static boolean inside(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public void initializePieces() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                ChessField field = getField(x, y);
                Side side = y <= 2 ? Side.WHITE : y <= 6 ? null : Side.BLACK;

                if (y == 1 || y == 8) {
                    switch (x) {
                        case 1, 8 -> occupyField(field, new Rook(this, side));
                        case 2, 7 -> occupyField(field, new Knight(this, side));
                        case 3, 6 -> occupyField(field, new Bishop(this, side));
                        case 4 -> occupyField(field, new Queen(this, side));
                        case 5 -> occupyField(field, new King(this, side));
                    }
                } else if (y == 2 || y == 7) {
                    occupyField(field, new Pawn(this, side));
                } else {
                    clearField(field);
                }
            }
        }
        boardHistory.initializeFirstState();
        updateCurrentFEN();
    }

    private void synchronizeBoardState() {
        boardHistory.readFirstStateFromBoard(this);
        updateCurrentFEN();
    }

    public boolean executeMove(Move move) {
        if (move == null) return false;

        boardHistory.addMove(move);
        move.execute();
        updateCurrentFEN();
        return true;
    }

    public boolean undoMove() {
        if (!boardHistory.isUndoable()) {
            return false;
        }

        Move move = boardHistory.undo();
        move.undo();
        updateCurrentFEN();
        return true;
    }

    public boolean redoMove() {
        if (!boardHistory.isRedoable()) {
            return false;
        }

        Move move = boardHistory.redo();
        move.execute();
        updateCurrentFEN();
        return true;
    }

    private void updateCurrentFEN() {
        // Get the current state
        BoardState currentState = getCurrentState();

        // Update the fen of the current board
        currentState.setPositionalFEN(FENCreator.toPositionalFEN(this, currentState.getSideToMove()));
        currentState.setFEN(FENCreator.toFEN(this, currentState.getSideToMove(), currentState.getHalfMoveCount(), currentState.getFullMoveCount()));
    }

    public GameState getGameState() {
        return GameState.getGameState(this, boardHistory);
    }

    public List<Piece> getPieces(Side side) {
        List<Piece> pieces = new ArrayList<>();
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                ChessField field = getField(x, y);
                if (ChessBoard.isOccupiedBySide(field, side)) {
                    pieces.add(field.getPiece());
                }
            }
        }
        return pieces;
    }

    public King getKing(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof King king) {
                return king;
            }
        }
        return null;
    }

    public Piece getNotKing(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (!(piece instanceof King)) {
                return piece;
            }
        }
        return null;
    }

    public void toConsole() {
        System.out.println(BoardPrinter.format(this, true));
    }

    @Override
    public String toString() {
        return BoardPrinter.format(this, false);
    }
}
