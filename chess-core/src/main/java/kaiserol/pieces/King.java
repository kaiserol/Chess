package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Castling;
import kaiserol.moves.Move;
import kaiserol.moves.NormalMove;
import kaiserol.state.CheckDetector;

import java.util.ArrayList;
import java.util.List;

public final class King extends Piece {
    private final int CASTLING_ROW;
    private final int CASTLING_COLUMN_KING;
    private final int CASTLING_COLUMN_ROOK_KING;
    private final int CASTLING_COLUMN_ROOK_QUEEN;

    public King(ChessBoard board, Side side) {
        super(board, side);
        this.CASTLING_ROW = side.isWhite() ? 1 : 8;
        this.CASTLING_COLUMN_KING = 5;
        this.CASTLING_COLUMN_ROOK_KING = 8;
        this.CASTLING_COLUMN_ROOK_QUEEN = 1;
    }

    @Override
    protected List<Move> generatePseudoLegalMoves() {
        List<Move> moves = new ArrayList<>();
        if (field == null) return moves;

        int fieldX = field.getX();
        int fieldY = field.getY();

        // Normal moves
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) continue;

                int targetX = fieldX + x;
                int targetY = fieldY + y;

                if (board.inside(targetX, targetY)) {
                    ChessField targetField = board.getField(targetX, targetY);
                    if (!board.isOccupiedBySide(targetField, side)) {
                        moves.add(new NormalMove(board, field, targetField));
                    }
                }
            }
        }

        // Check whether the king is in the castle position
        if (fieldX != CASTLING_COLUMN_KING || fieldY != CASTLING_ROW) {
            return moves;
        }

        boolean castleKingSide = side.isWhite() ? board.canWhiteCastleKingSide() : board.canBlackCastleKingSide();
        boolean castleQueenSide = side.isWhite() ? board.canWhiteCastleQueenSide() : board.canBlackCastleQueenSide();

        if (castleKingSide) addCastling(moves, CASTLING_COLUMN_ROOK_KING); // Check Kingside Castling
        if (castleQueenSide) addCastling(moves, CASTLING_COLUMN_ROOK_QUEEN); // Check Queenside Castling

        return moves;
    }

    private void addCastling(List<Move> moves, int rookX) {
        int fieldX = field.getX();
        int fieldY = field.getY();
        ChessField rookStartField = board.getField(rookX, fieldY);

        if (board.isOccupiedBySide(rookStartField, side) && rookStartField.getPiece() instanceof Rook) {
            // Check whether the fields between the king and the rook are empty
            int startX = Math.min(field.getX(), rookX) + 1;
            int endX = Math.max(field.getX(), rookX) - 1;
            for (int tx = startX; tx <= endX; tx++) {
                if (board.getField(tx, fieldY).isOccupied()) return;
            }

            // Check whether the king is in check or moves over attacked fields
            int direction = rookX > 4 ? 1 : -1;
            for (int i = 0; i <= 2; i++) {
                int targetX = fieldX + direction * i;
                if (CheckDetector.isFieldAttacked(board, board.getField(targetX, fieldY), side.opposite())) return;
            }

            // Everything is fine, add castling
            ChessField kingTargetField = board.getField(fieldX + direction * 2, fieldY);
            ChessField rookTargetField = board.getField(fieldX + direction, fieldY);
            moves.add(new Castling(board, field, kingTargetField, rookStartField, rookTargetField));
        }
    }

    @Override
    public char getSymbol() {
        return !side.isWhite() ? '♔' : '♚';
    }

    @Override
    public char getLetter() {
        return side.isWhite() ? 'K' : 'k';
    }
}
