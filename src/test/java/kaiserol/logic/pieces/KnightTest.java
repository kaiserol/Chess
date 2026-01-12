package kaiserol.logic.pieces;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.moves.Move;
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
        board.link(field, knight);

        List<Move> moves = knight.getPseudoLegalMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, knight, moves);
        board.toConsole();

        // d4 -> c6, e6, f5, f3, e2, c2, b3, b5
        assertEquals(8, moves.size());
        assertTrue(targetFields.contains("c6"));
        assertTrue(targetFields.contains("e6"));
        assertTrue(targetFields.contains("f5"));
        assertTrue(targetFields.contains("f3"));
        assertTrue(targetFields.contains("e2"));
        assertTrue(targetFields.contains("c2"));
        assertTrue(targetFields.contains("b3"));
        assertTrue(targetFields.contains("b5"));
    }

    @Test
    void testKnightMovesCorner() {
        ChessField field = board.getField("a1");
        Knight knight = new Knight(board, Side.WHITE);
        board.link(field, knight);

        List<Move> moves = knight.getPseudoLegalMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, knight, moves);
        board.toConsole();

        // a1 -> b3, c2
        assertEquals(2, moves.size());
        assertTrue(targetFields.contains("b3"));
        assertTrue(targetFields.contains("c2"));
    }

    @Test
    void testKnightMovesBlocked() {
        ChessField field = board.getField("d4");
        Knight knight = new Knight(board, Side.WHITE);
        board.link(field, knight);

        // Block c6
        board.link(board.getField("c6"), new Pawn(board, Side.WHITE));

        List<Move> moves = knight.getPseudoLegalMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, knight, moves);
        board.toConsole();

        assertEquals(7, moves.size());
        assertFalse(targetFields.contains("c6"));
    }
}
