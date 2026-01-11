package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.pieces.Queen;
import kaiserol.chessboard.Side;
import kaiserol.logic.moves.Move;
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

        List<Move> moves = queen.getPseudoLegalMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), field, queen, moves);
        board.toConsole();

        // Queen = Rook (14) + Bishop (13) = 27
        assertTrue(targetFields.contains("d1"));
        assertTrue(targetFields.contains("d8"));
        assertTrue(targetFields.contains("a4"));
        assertTrue(targetFields.contains("h4"));
        assertTrue(targetFields.contains("a1"));
        assertTrue(targetFields.contains("h8"));
        assertTrue(targetFields.contains("a7"));
        assertTrue(targetFields.contains("g1"));
        assertEquals(27, moves.size());
    }
}
