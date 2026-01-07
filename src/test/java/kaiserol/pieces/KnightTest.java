package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testKnightMovesCenter() {
        Field field = board.getField("d4");
        Knight knight = new Knight(Side.WHITE, board, field);
        field.setPiece(knight);

        List<Move> moves = knight.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        board.printBoard();
        System.out.printf("Moves: %s%n", moves);
        System.out.printf("Count: %d%n", moves.size());

        // d4 -> c6, e6, f5, f3, e2, c2, b3, b5
        assertTrue(targetFields.contains("c6"));
        assertTrue(targetFields.contains("e6"));
        assertTrue(targetFields.contains("f5"));
        assertTrue(targetFields.contains("f3"));
        assertTrue(targetFields.contains("e2"));
        assertTrue(targetFields.contains("c2"));
        assertTrue(targetFields.contains("b3"));
        assertTrue(targetFields.contains("b5"));
        assertEquals(8, moves.size());
    }

    @Test
    void testKnightMovesCorner() {
        Field field = board.getField("a1");
        Knight knight = new Knight(Side.WHITE, board, field);
        field.setPiece(knight);

        List<Move> moves = knight.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        board.printBoard();
        System.out.printf("Moves: %s%n", moves);
        System.out.printf("Count: %d%n", moves.size());

        // a1 -> b3, c2
        assertTrue(targetFields.contains("b3"));
        assertTrue(targetFields.contains("c2"));
        assertEquals(2, moves.size());
    }

    @Test
    void testKnightMovesBlocked() {
        Field field = board.getField("d4");
        Knight knight = new Knight(Side.WHITE, board, field);
        field.setPiece(knight);

        // Block c6
        board.getField("c6").setPiece(new Pawn(Side.WHITE, board, board.getField("c6")));

        List<Move> moves = knight.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        board.printBoard();
        System.out.printf("Moves: %s%n", moves);
        System.out.printf("Count: %d%n", moves.size());

        assertFalse(targetFields.contains("c6"));
        assertEquals(7, moves.size());
    }
}
