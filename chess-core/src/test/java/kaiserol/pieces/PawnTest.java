package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.moves.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testWhitePawnMovesFromStart() {
        ChessField whiteField = board.getField("e2");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.occupyFieldAndSync(whiteField, whitePawn);

        List<Move> moves = whitePawn.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.toConsole();

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m instanceof NormalMove && m.getTargetField().has("e3")));
        assertTrue(moves.stream().anyMatch(m -> m instanceof PawnJump && m.getTargetField().has("e4")));
    }

    @Test
    void testBlackPawnMovesFromStart() {
        ChessField blackField = board.getField("e7");
        Pawn blackPawn = new Pawn(board, Side.BLACK);
        board.occupyFieldAndSync(blackField, blackPawn);

        List<Move> moves = blackPawn.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), blackField, blackPawn, moves);
        board.toConsole();

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m instanceof NormalMove && m.getTargetField().has("e6")));
        assertTrue(moves.stream().anyMatch(m -> m instanceof PawnJump && m.getTargetField().has("e5")));
    }

    @Test
    void testPawnCapture() {
        ChessField whiteField = board.getField("e4");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.occupyFieldAndSync(whiteField, whitePawn);

        ChessField blackField = board.getField("d5");
        Pawn blackPawn = new Pawn(board, Side.BLACK);
        board.occupyFieldAndSync(blackField, blackPawn);

        ChessField otherBlackField = board.getField("f5");
        Rook blackRook = new Rook(board, Side.BLACK);
        board.occupyFieldAndSync(otherBlackField, blackRook);

        List<Move> moves = whitePawn.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.toConsole();

        // Forward (e5), Capture Left (d5), Capture Right (f5)
        assertEquals(3, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("e5")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("d5")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().has("f5")));
    }

    @Test
    void testPawnBlocked() {
        ChessField whiteField = board.getField("e4");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.occupyFieldAndSync(whiteField, whitePawn);

        ChessField blockingField = board.getField("e5");
        Rook blockingPiece = new Rook(board, Side.BLACK);
        board.occupyFieldAndSync(blockingField, blockingPiece);

        List<Move> moves = whitePawn.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.toConsole();

        assertEquals(0, moves.size(), "Pawn should be blocked by piece in front");
    }

    @Test
    void testPawnPromotionMove() {
        ChessField whiteField = board.getField("e7");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.occupyFieldAndSync(whiteField, whitePawn);

        List<Move> moves = whitePawn.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.toConsole();

        assertEquals(1, moves.size());
        assertInstanceOf(PawnPromotion.class, moves.getFirst());
        assertEquals("e8", moves.getFirst().getTargetField().toString());
    }

    @Test
    void testEnPassant() {
        // En Passant setup:
        // 1. White pawn on e5
        // 2. Black pawn jumps from d7 to d5
        // 3. White pawn can capture d5 by moving to d6

        ChessField e5 = board.getField("e5");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.occupyFieldAndSync(e5, whitePawn);

        ChessField d7 = board.getField("d7");
        Pawn blackPawn = new Pawn(board, Side.BLACK);
        board.occupyFieldAndSync(d7, blackPawn);

        ChessField d5 = board.getField("d5");
        PawnJump jump = new PawnJump(board, d7, d5);
        board.executeMove(jump);

        List<Move> moves = whitePawn.getSortedPseudoLegalMoves();
        System.out.printf("%d Pseudo legal moves from %s (%s): %s%n", moves.size(), e5, whitePawn, moves);
        board.toConsole();

        assertTrue(moves.stream().anyMatch(m -> m instanceof EnPassant && m.getTargetField().has("d6")),
                "En Passant move to d6 should be available");
    }
}
