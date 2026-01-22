package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
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
        ChessField field = board.getField("d4");
        Knight knight = new Knight(board, Side.WHITE);
        board.occupyFieldAndSync(field, knight);

        List<Move> moves = knight.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, knight, moves);
        board.toConsole();

        // d4 -> c6, e6, f5, f3, e2, c2, b3, b5
        assertEquals(8, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("c6")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("e6")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("f5")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("f3")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("e2")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("c2")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("b3")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("b5")));
    }

    @Test
    void testKnightMovesCorner() {
        ChessField field = board.getField("a1");
        Knight knight = new Knight(board, Side.WHITE);
        board.occupyFieldAndSync(field, knight);

        List<Move> moves = knight.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, knight, moves);
        board.toConsole();

        // a1 -> b3, c2
        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("b3")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("c2")));
    }

    @Test
    void testKnightMovesBlocked() {
        ChessField field = board.getField("d4");
        Knight knight = new Knight(board, Side.WHITE);
        board.occupyFieldAndSync(field, knight);

        // Block c6
        board.occupyFieldAndSync(board.getField("c6"), new Pawn(board, Side.WHITE));
        List<Move> moves = knight.getSortedPseudoLegalMoves();

        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, knight, moves);
        board.toConsole();

        assertEquals(7, moves.size());
        assertFalse(moves.stream().anyMatch(m -> m.getTargetField().has("c6")));
    }
}
