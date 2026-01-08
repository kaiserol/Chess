package kaiserol.pieces;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.Pawn;
import kaiserol.chessboard.pieces.Rook;
import kaiserol.logic.moves.*;
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
        board.link(whiteField, whitePawn);

        List<Move> moves = whitePawn.getMoves();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.printBoard();

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m instanceof NormalMove && m.getTargetField().toString().equals("e3")));
        assertTrue(moves.stream().anyMatch(m -> m instanceof PawnJump && m.getTargetField().toString().equals("e4")));
    }

    @Test
    void testBlackPawnMovesFromStart() {
        ChessField blackField = board.getField("e7");
        Pawn blackPawn = new Pawn(board, Side.BLACK);
        board.link(blackField, blackPawn);

        List<Move> moves = blackPawn.getMoves();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), blackField, blackPawn, moves);
        board.printBoard();

        assertEquals(2, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m instanceof NormalMove && m.getTargetField().toString().equals("e6")));
        assertTrue(moves.stream().anyMatch(m -> m instanceof PawnJump && m.getTargetField().toString().equals("e5")));
    }

    @Test
    void testPawnCapture() {
        ChessField whiteField = board.getField("e4");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.link(whiteField, whitePawn);

        ChessField blackField = board.getField("d5");
        Pawn blackPawn = new Pawn(board, Side.BLACK);
        board.link(blackField, blackPawn);

        ChessField otherBlackField = board.getField("f5");
        Rook blackRook = new Rook(board, Side.BLACK);
        board.link(otherBlackField, blackRook);

        List<Move> moves = whitePawn.getMoves();
        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.printBoard();

        // Forward (e5), Capture Left (d5), Capture Right (f5)
        assertEquals(3, moves.size());
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().toString().equals("e5")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().toString().equals("d5")));
        assertTrue(moves.stream().anyMatch(m -> m.getTargetField().toString().equals("f5")));
    }

    @Test
    void testPawnBlocked() {
        ChessField whiteField = board.getField("e4");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.link(whiteField, whitePawn);

        ChessField blockingField = board.getField("e5");
        Rook blockingPiece = new Rook(board, Side.BLACK);
        board.link(blockingField, blockingPiece);

        List<Move> moves = whitePawn.getMoves();
        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.printBoard();

        assertEquals(0, moves.size(), "Pawn should be blocked by piece in front");
    }

    @Test
    void testPawnPromotionMove() {
        ChessField whiteField = board.getField("e7");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.link(whiteField, whitePawn);

        List<Move> moves = whitePawn.getMoves();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), whiteField, whitePawn, moves);
        board.printBoard();

        assertEquals(1, moves.size());
        assertInstanceOf(PawnPromotion.class, moves.getFirst());
        assertEquals("e8", moves.getFirst().getTargetField().toString());
    }

    @Test
    void testEnPassant() {
        // En Passant setup:
        // 1. White pawn at e5
        // 2. Black pawn jumps from d7 to d5
        // 3. White pawn can capture d5 by moving to d6

        ChessField e5 = board.getField("e5");
        Pawn whitePawn = new Pawn(board, Side.WHITE);
        board.link(e5, whitePawn);

        ChessField d7 = board.getField("d7");
        Pawn blackPawn = new Pawn(board, Side.BLACK);
        board.link(d7, blackPawn);

        ChessField d5 = board.getField("d5");
        PawnJump jump = new PawnJump(board, d7, d5);
        board.executeMove(jump);

        List<Move> moves = whitePawn.getMoves();

        System.out.printf("%d Pseudo moves from %s (%s): %s%n", moves.size(), e5, whitePawn, moves);
        board.printBoard();

        assertTrue(moves.stream().anyMatch(m -> m instanceof EnPassant && m.getTargetField().toString().equals("d6")),
                "En Passant move to d6 should be available");
    }
}
