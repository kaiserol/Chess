package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Castling;
import kaiserol.moves.Move;
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

        // Black Rook attacking e5, f5, g5
        ChessField rookField = board.getField("a5");
        Rook rook = new Rook(board, Side.BLACK);
        board.link(rookField, rook);

        List<Move> moves = king.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, king, moves);
        board.toConsole();

        assertEquals(8, moves.size());
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
        board.toConsole();

        assertEquals(5, legalMoves.size());
        assertFalse(legalMoves.stream().anyMatch(m -> m.getTargetField().has("d5")));
        assertFalse(legalMoves.stream().anyMatch(m -> m.getTargetField().has("e5")));
        assertFalse(legalMoves.stream().anyMatch(m -> m.getTargetField().has("f5")));
    }

    @Test
    void testCastlingKingside() {
        // King at e1, Rook at h1
        King king = new King(board, Side.WHITE);
        Rook rook = new Rook(board, Side.WHITE);
        board.link(board.getField("e1"), king);
        board.link(board.getField("h1"), rook);

        List<Move> moves = king.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), king.getField(), king, moves);
        board.toConsole();

        boolean hasCastling = moves.stream().anyMatch(m -> m instanceof Castling && m.getTargetField().has("g1"));
        assertTrue(hasCastling, "Should have kingside castling to g1");
    }

    @Test
    void testCastlingQueenside() {
        // King at e1, Rook at a1
        King king = new King(board, Side.WHITE);
        Rook rook = new Rook(board, Side.WHITE);
        board.link(board.getField("e1"), king);
        board.link(board.getField("a1"), rook);

        List<Move> moves = king.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), king.getField(), king, moves);
        board.toConsole();

        boolean hasCastling = moves.stream().anyMatch(m -> m instanceof Castling && m.getTargetField().has("c1"));
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

        List<Move> moves = king.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), king.getField(), king, moves);
        board.toConsole();

        boolean hasCastling = moves.stream().anyMatch(m -> m instanceof Castling);
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

        List<Move> moves = king.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), king.getField(), king, moves);
        board.toConsole();

        boolean hasCastling = moves.stream().anyMatch(m -> m instanceof Castling);
        assertFalse(hasCastling, "Should not have castling when moving through check");
    }
}
