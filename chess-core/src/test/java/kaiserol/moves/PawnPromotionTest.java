package kaiserol.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
import kaiserol.chessboard.Side;
import kaiserol.pieces.Pawn;
import kaiserol.pieces.Piece;
import kaiserol.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PawnPromotionTest {
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @Test
    void testPawnPromotionExecute() {
        ChessField startField = board.getField("a7");
        ChessField targetField = board.getField("a8");

        Pawn pawn = new Pawn(board, Side.WHITE);
        board.link(startField, pawn);

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField, PawnPromotion.Choice.QUEEN);
        Piece promotingQueen = promotion.getPromotingPiece();

        board.toConsole();
        board.executeMove(promotion);
        board.toConsole();

        assertNull(startField.getPiece());
        assertNull(pawn.getField());
        assertEquals(promotingQueen, targetField.getPiece());
        assertEquals(targetField, promotingQueen.getField());
    }

    @Test
    void testPawnPromotionUndo() {
        ChessField startField = board.getField("a7");
        ChessField targetField = board.getField("a8");

        Pawn pawn = new Pawn(board, Side.WHITE);
        board.link(startField, pawn);

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField, PawnPromotion.Choice.QUEEN);
        Piece promotingQueen = promotion.getPromotingPiece();

        board.toConsole();
        board.executeMove(promotion);
        board.toConsole();
        board.undoMove();
        board.toConsole();

        assertNull(targetField.getPiece());
        assertNull(promotingQueen.getField());
        assertEquals(pawn, startField.getPiece());
        assertEquals(startField, pawn.getField());
    }

    @Test
    void testPawnPromotionWithCaptureExecute() {
        ChessField startField = board.getField("a7");
        ChessField targetField = board.getField("b8");

        Pawn pawn = new Pawn(board, Side.WHITE);
        board.link(startField, pawn);

        Piece blackRook = new Rook(board, Side.BLACK);
        board.link(targetField, blackRook);

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField, PawnPromotion.Choice.QUEEN);
        Piece promotingQueen = promotion.getPromotingPiece();

        board.toConsole();
        board.executeMove(promotion);
        board.toConsole();

        assertNull(startField.getPiece());
        assertNull(blackRook.getField());
        assertNull(pawn.getField());
        assertEquals(promotingQueen, targetField.getPiece());
        assertEquals(targetField, promotingQueen.getField());
    }

    @Test
    void testPawnPromotionWithCaptureUndo() {
        ChessField startField = board.getField("a7");
        ChessField targetField = board.getField("b8");

        Pawn pawn = new Pawn(board, Side.WHITE);
        board.link(startField, pawn);

        Piece blackRook = new Rook(board, Side.BLACK);
        board.link(targetField, blackRook);

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField, PawnPromotion.Choice.QUEEN);
        Piece promotingQueen = promotion.getPromotingPiece();

        board.toConsole();
        board.executeMove(promotion);
        board.toConsole();
        board.undoMove();
        board.toConsole();

        assertNull(promotingQueen.getField());
        assertEquals(pawn, startField.getPiece());
        assertEquals(startField, pawn.getField());
        assertEquals(blackRook, targetField.getPiece());
        assertEquals(targetField, blackRook.getField());
    }
}
