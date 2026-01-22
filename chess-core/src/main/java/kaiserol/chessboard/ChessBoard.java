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

    public PawnPromotionProvider getPromotionProvider() {
        return pawnPromotionProvider;
    }

    public void setPromotionProvider(PawnPromotionProvider provider) {
        this.pawnPromotionProvider = provider;
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

    private void checkCoordinates(int x, int y) {
        if (x < 1 || x > 8) throw new CoordinateException("x must be between 1 and 8");
        if (y < 1 || y > 8) throw new CoordinateException("y must be between 1 and 8");
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

    public void link(ChessField field, Piece piece) {
        if (field != null) field.setPiece(piece);
        if (piece != null) piece.setField(field);
    }

    public void unlink(ChessField field, Piece piece) {
        if (field != null) field.removePiece();
        if (piece != null) piece.removeField();
    }

    public boolean isOccupiedBySide(ChessField field, Side side) {
        return field.isOccupied() && field.getPiece().getSide() == side;
    }

    public boolean inside(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    /**
     * Synchronizes the history with the current manual setup.
     * Must be called in tests after placing the figures.
     */
    public void syncBoardHistory() {
        boardHistory.initializeWithCurrentState(this);
        updateCurrentFEN();
    }

    public void initializePieces() {
        boardHistory.initialize();
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                ChessField field = getField(x, y);
                Side side = y <= 2 ? Side.WHITE : y <= 6 ? null : Side.BLACK;

                if (y == 1 || y == 8) {
                    switch (x) {
                        case 1, 8 -> link(field, new Rook(this, side));
                        case 2, 7 -> link(field, new Knight(this, side));
                        case 3, 6 -> link(field, new Bishop(this, side));
                        case 4 -> link(field, new Queen(this, side));
                        case 5 -> link(field, new King(this, side));
                    }
                } else if (y == 2 || y == 7) {
                    link(field, new Pawn(this, side));
                } else {
                    if (field.isOccupied()) {
                        unlink(field, field.getPiece());
                    }
                }
            }
        }
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
                if (isOccupiedBySide(field, side)) {
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
        System.out.println(this);
    }

    @Override
    public String toString() {
        return BoardPrinter.format(this);
    }
}
