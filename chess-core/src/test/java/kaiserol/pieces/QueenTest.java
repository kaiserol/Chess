package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueenTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testQueenMovesCenter() {
        ChessField field = board.getField("d4");
        Queen queen = new Queen(board, Side.WHITE);
        board.link(field, queen);

        List<Move> moves = queen.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, queen, moves);
        board.toConsole();

        // Queen = Rook (14) + Bishop (13) = 27
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("d1")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("d8")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a4")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("h4")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a1")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("h8")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("a7")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("g1")));
        assertEquals(27, moves.size());
    }
}
