package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.moves.Move;
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
        Field field = board.getField("d4");
        Queen queen = new Queen(Side.WHITE, board, field);
        field.setPiece(queen);

        List<Move> moves = queen.getValidMoves();
        List<String> targetFields = moves.stream().map(Move::toString).toList();

        board.printBoard();
        System.out.printf("Moves: %s%n", moves);
        System.out.printf("Count: %d%n", moves.size());

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
