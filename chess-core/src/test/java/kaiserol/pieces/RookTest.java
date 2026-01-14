package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
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
        ChessField field = board.getField("d4");
        Rook rook = new Rook(board, Side.WHITE);
        board.link(field, rook);

        List<Move> moves = rook.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, rook, moves);
        board.toConsole();

        // Rook from d4: d1-d3, d5-d8 (7) + a4-c4, e4-h4 (7) = 14
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("d1")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("d8")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a4")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("h4")));
        assertEquals(14, moves.size());
    }

    @Test
    void testRookMovesBlocked() {
        ChessField field = board.getField("d4");
        Rook rook = new Rook(board, Side.WHITE);
        board.link(field, rook);

        // Block d6
        board.link(board.getField("d6"), new Pawn(board, Side.WHITE));

        List<Move> moves = rook.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, rook, moves);
        board.toConsole();

        // d1-d3 (3), d5 (1), a4-c4 (3), e4-h4 (4) = 11
        // d6, d7, d8 are blocked
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("d6")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("d5")));
        assertEquals(11, moves.size());
    }
}
