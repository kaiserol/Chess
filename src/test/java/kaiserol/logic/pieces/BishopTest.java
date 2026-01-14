package kaiserol.logic.pieces;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testBishopMovesOnEmptyBoard() {
        ChessField field = board.getField("d4");
        Bishop bishop = new Bishop(board, Side.WHITE);
        board.link(field, bishop);

        List<Move> moves = bishop.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, bishop, moves);
        board.toConsole();

        // Diagonals from d4:
        // a1, b2, c3, e5, f6, g7, h8 (7 fields)
        // a7, b6, c5, e3, f2, g1 (6 fields)
        assertEquals(13, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a1")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("h8")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a7")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("g1")));
    }

    @Test
    void testBishopMovesBlocked() {
        ChessField field = board.getField("d4");
        Bishop bishop = new Bishop(board, Side.WHITE);
        board.link(field, bishop);

        // Blocking pawns
        board.link(board.getField("a1"), new Pawn(board, Side.WHITE));
        board.link(board.getField("b6"), new Pawn(board, Side.WHITE));
        board.link(board.getField("e5"), new Pawn(board, Side.WHITE));

        List<Move> moves = bishop.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, bishop, moves);
        board.toConsole();

        // Should not contain e5, f6, g7, h8, ...
        assertEquals(6, moves.size()); // 13 - 7 = 6
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("a1")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("b6")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("e5")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("f6")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("g7")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("g8")));
    }

    @Test
    void testBishopMovesCapturingEnemies() {
        ChessField field = board.getField("d4");
        Bishop bishop = new Bishop(board, Side.WHITE);
        board.link(field, bishop);

        // Blocking Enemies
        board.link(board.getField("a1"), new Pawn(board, Side.BLACK));
        board.link(board.getField("b6"), new Pawn(board, Side.BLACK));
        board.link(board.getField("e5"), new Pawn(board, Side.BLACK));

        List<Move> moves = bishop.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, bishop, moves);
        board.toConsole();

        // Should contain e5, but not f6, g7, h8, ...
        assertEquals(9, moves.size()); // 13 - 4 = 9
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a1")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("b6")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("e5")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("f6")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("g7")));
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("g8")));
    }
}
