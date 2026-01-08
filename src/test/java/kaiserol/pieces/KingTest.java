package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.King;
import kaiserol.chessboard.pieces.Rook;
import kaiserol.chessboard.pieces.Queen;
import kaiserol.logic.moves.Move;
import kaiserol.logic.moves.Castling;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testKingMovesCenter() {
        ChessField field = board.getField("e4");
        King king = new King(board, Side.WHITE);
        board.link(field, king);

        List<Move> legalMoves = king.getLegalMoves();
        System.out.printf("%d Legal moves from %s (%s): %s%n", legalMoves.size(), field, king, legalMoves);
        board.printBoard();

        assertEquals(8, legalMoves.size());
    }

    @Test
    void testKingCannotMoveIntoCheck() {
        ChessField field = board.getField("e4");
        King king = new King(board, Side.WHITE);
        board.link(field, king);

        // Black Rook attacking e5, f5, g5
        ChessField rookField = board.getField("a5");
        Rook rook = new Rook(board, Side.BLACK);
        board.link(rookField, rook);

        List<Move> legalMoves = king.getLegalMoves();
        System.out.printf("%d Legal moves from %s (%s): %s%n", legalMoves.size(), field, king, legalMoves);
        board.printBoard();

        List<String> targetFields = legalMoves.stream().map(Move::toString).toList();

        assertFalse(targetFields.contains("d5"));
        assertFalse(targetFields.contains("e5"));
        assertFalse(targetFields.contains("f5"));
        assertEquals(5, legalMoves.size(), "Should have 5 legal moves");
    }

    @Test
    void testCastlingKingside() {
        // King at e1, Rook at h1
        King king = new King(board, Side.WHITE);
        Rook rook = new Rook(board, Side.WHITE);
        board.link(board.getField("e1"), king);
        board.link(board.getField("h1"), rook);

        List<Move> legalMoves = king.getLegalMoves();
        System.out.printf("%d Legal moves from %s (%s): %s%n", legalMoves.size(), king.getField(), king, legalMoves);
        board.printBoard();

        boolean hasCastling = legalMoves.stream().anyMatch(m -> m instanceof Castling && m.getTargetField().toString().equals("g1"));
        assertTrue(hasCastling, "Should have kingside castling to g1");
    }

    @Test
    void testCastlingQueenside() {
        // King at e1, Rook at a1
        King king = new King(board, Side.WHITE);
        Rook rook = new Rook(board, Side.WHITE);
        board.link(board.getField("e1"), king);
        board.link(board.getField("a1"), rook);

        List<Move> legalMoves = king.getLegalMoves();
        System.out.printf("%d Legal moves from %s (%s): %s%n", legalMoves.size(), king.getField(), king, legalMoves);
        board.printBoard();

        boolean hasCastling = legalMoves.stream().anyMatch(m -> m instanceof Castling && m.getTargetField().toString().equals("c1"));
        assertTrue(hasCastling, "Should have queenside castling to c1");
    }

    @Test
    void testCastlingBlockedByPiece() {
        King king = new King(board, Side.WHITE);
        Rook rook = new Rook(board, Side.WHITE);
        board.link(board.getField("e1"), king);
        board.link(board.getField("h1"), rook);
        
        // Block with Bishop
        board.link(board.getField("f1"), new Queen(board, Side.WHITE));

        List<Move> legalMoves = king.getLegalMoves();
        System.out.printf("%d Legal moves from %s (%s): %s%n", legalMoves.size(), king.getField(), king, legalMoves);
        board.printBoard();

        boolean hasCastling = legalMoves.stream().anyMatch(m -> m instanceof Castling);
        assertFalse(hasCastling, "Should not have castling when path is blocked");
    }

    @Test
    void testCastlingThroughCheck() {
        King king = new King(board, Side.WHITE);
        Rook rook = new Rook(board, Side.WHITE);
        board.link(board.getField("e1"), king);
        board.link(board.getField("h1"), rook);
        
        // Black Rook attacking f1
        Rook enemyRook = new Rook(board, Side.BLACK);
        board.link(board.getField("f8"), enemyRook);

        List<Move> legalMoves = king.getLegalMoves();
        System.out.printf("%d Legal moves from %s (%s): %s%n", legalMoves.size(), king.getField(), king, legalMoves);
        board.printBoard();

        boolean hasCastling = legalMoves.stream().anyMatch(m -> m instanceof Castling);
        assertFalse(hasCastling, "Should not have castling when moving through check");
    }
}
