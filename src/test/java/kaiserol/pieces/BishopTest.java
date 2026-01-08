package kaiserol.pieces;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.Bishop;
import kaiserol.chessboard.pieces.Pawn;
import kaiserol.logic.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testBishopMovesOnEmptyBoard() {
        Field field = board.getField("d4");
        Bishop bishop = new Bishop(Side.WHITE, board, field);
        field.setPiece(bishop);

        List<Move> moves = bishop.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Valid moves from %s (%s): %s%n", moves.size(), field, bishop.getLetter(), moves);
        board.printBoard();

        // Diagonals from d4:
        // a1, b2, c3, e5, f6, g7, h8 (7 fields)
        // a7, b6, c5, e3, f2, g1 (6 fields)
        assertTrue(targetFields.contains("a1"));
        assertTrue(targetFields.contains("h8"));
        assertTrue(targetFields.contains("a7"));
        assertTrue(targetFields.contains("g1"));
        assertEquals(13, moves.size());
    }

    @Test
    void testBishopMovesBlocked() {
        Field field = board.getField("d4");
        Bishop bishop = new Bishop(Side.WHITE, board, field);
        field.setPiece(bishop);

        // Blocking pawns
        board.getField("a1").setPiece(new Pawn(Side.WHITE, board, board.getField("a1")));
        board.getField("b6").setPiece(new Pawn(Side.WHITE, board, board.getField("b6")));
        board.getField("e5").setPiece(new Pawn(Side.WHITE, board, board.getField("e5")));

        List<Move> moves = bishop.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Valid moves from %s (%s): %s%n", moves.size(), field, bishop.getLetter(), moves);
        board.printBoard();

        // Should not contain e5, f6, g7, h8, ...
        assertFalse(targetFields.contains("a1"));
        assertFalse(targetFields.contains("b6"));
        assertFalse(targetFields.contains("e5"));
        assertFalse(targetFields.contains("f6"));
        assertFalse(targetFields.contains("g7"));
        assertFalse(targetFields.contains("g8"));
        assertEquals(6, moves.size()); // 13 - 7 = 6
    }

    @Test
    void testBishopMovesCapturingEnemies() {
        Field field = board.getField("d4");
        Bishop bishop = new Bishop(Side.WHITE, board, field);
        field.setPiece(bishop);

        // Blocking Enemies
        board.getField("a1").setPiece(new Pawn(Side.BLACK, board, board.getField("a1")));
        board.getField("b6").setPiece(new Pawn(Side.BLACK, board, board.getField("b6")));
        board.getField("e5").setPiece(new Pawn(Side.BLACK, board, board.getField("e5")));

        List<Move> moves = bishop.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Valid moves from %s (%s): %s%n", moves.size(), field, bishop.getLetter(), moves);
        board.printBoard();

        // Should contain e5, but not f6, g7, h8, ...
        assertTrue(targetFields.contains("a1"));
        assertTrue(targetFields.contains("b6"));
        assertTrue(targetFields.contains("e5"));
        assertFalse(targetFields.contains("f6"));
        assertFalse(targetFields.contains("g7"));
        assertFalse(targetFields.contains("g8"));
        assertEquals(9, moves.size()); // 13 - 4 = 9
    }
}
