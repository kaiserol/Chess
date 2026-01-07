package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testRookMovesCenter() {
        Field field = board.getField("d4");
        Rook rook = new Rook(Side.WHITE, board, field);
        field.setPiece(rook);

        List<Move> moves = rook.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        board.printBoard();
        System.out.printf("Moves: %s%n", moves);
        System.out.printf("Count: %d%n", moves.size());

        // Rook from d4: d1-d3, d5-d8 (7) + a4-c4, e4-h4 (7) = 14
        assertTrue(targetFields.contains("d1"));
        assertTrue(targetFields.contains("d8"));
        assertTrue(targetFields.contains("a4"));
        assertTrue(targetFields.contains("h4"));
        assertEquals(14, moves.size());
    }

    @Test
    void testRookMovesBlocked() {
        Field field = board.getField("d4");
        Rook rook = new Rook(Side.WHITE, board, field);
        field.setPiece(rook);

        // Block d6
        board.getField("d6").setPiece(new Pawn(Side.WHITE, board, board.getField("d6")));

        List<Move> moves = rook.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        board.printBoard();
        System.out.printf("Moves: %s%n", moves);
        System.out.printf("Count: %d%n", moves.size());

        // d1-d3 (3), d5 (1), a4-c4 (3), e4-h4 (4) = 11
        // d6, d7, d8 are blocked
        assertFalse(targetFields.contains("d6"));
        assertTrue(targetFields.contains("d5"));
        assertEquals(11, moves.size());
    }
}
