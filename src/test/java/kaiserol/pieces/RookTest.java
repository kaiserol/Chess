package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Pawn;
import kaiserol.chessboard.pieces.Rook;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;
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

        List<Move> moves = rook.getPseudoLegalMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), field, rook, moves);
        board.printBoard();

        // Rook from d4: d1-d3, d5-d8 (7) + a4-c4, e4-h4 (7) = 14
        assertTrue(targetFields.contains("d1"));
        assertTrue(targetFields.contains("d8"));
        assertTrue(targetFields.contains("a4"));
        assertTrue(targetFields.contains("h4"));
        assertEquals(14, moves.size());
    }

    @Test
    void testRookMovesBlocked() {
        ChessField field = board.getField("d4");
        Rook rook = new Rook(board, Side.WHITE);
        board.link(field, rook);

        // Block d6
        board.link(board.getField("d6"), new Pawn(board, Side.WHITE));

        List<Move> moves = rook.getPseudoLegalMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), field, rook, moves);
        board.printBoard();

        // d1-d3 (3), d5 (1), a4-c4 (3), e4-h4 (4) = 11
        // d6, d7, d8 are blocked
        assertFalse(targetFields.contains("d6"));
        assertTrue(targetFields.contains("d5"));
        assertEquals(11, moves.size());
    }
}
