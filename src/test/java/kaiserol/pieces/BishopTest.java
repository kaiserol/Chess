package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.Bishop;
import kaiserol.chessboard.pieces.Pawn;
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

        List<Move> moves = bishop.getMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), field, bishop, moves);
        board.printBoard();

        // Diagonals from d4:
        // a1, b2, c3, e5, f6, g7, h8 (7 fields)
        // a7, b6, c5, e3, f2, g1 (6 fields)
        assertEquals(13, moves.size());
        assertTrue(targetFields.contains("a1"));
        assertTrue(targetFields.contains("h8"));
        assertTrue(targetFields.contains("a7"));
        assertTrue(targetFields.contains("g1"));
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

        List<Move> moves = bishop.getMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), field, bishop, moves);
        board.printBoard();

        // Should not contain e5, f6, g7, h8, ...
        assertEquals(6, moves.size()); // 13 - 7 = 6
        assertFalse(targetFields.contains("a1"));
        assertFalse(targetFields.contains("b6"));
        assertFalse(targetFields.contains("e5"));
        assertFalse(targetFields.contains("f6"));
        assertFalse(targetFields.contains("g7"));
        assertFalse(targetFields.contains("g8"));
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

        List<Move> moves = bishop.getMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), field, bishop, moves);
        board.printBoard();

        // Should contain e5, but not f6, g7, h8, ...
        assertEquals(9, moves.size()); // 13 - 4 = 9
        assertTrue(targetFields.contains("a1"));
        assertTrue(targetFields.contains("b6"));
        assertTrue(targetFields.contains("e5"));
        assertFalse(targetFields.contains("f6"));
        assertFalse(targetFields.contains("g7"));
        assertFalse(targetFields.contains("g8"));
    }
}
