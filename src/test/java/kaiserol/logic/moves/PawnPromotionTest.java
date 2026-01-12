package kaiserol.logic.moves;

import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.ChessField;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.pieces.Pawn;
import kaiserol.logic.pieces.Piece;
import kaiserol.logic.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField);
        promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);
        Piece promotedQueen = promotion.getPromotedPiece();

        board.toConsole();
        promotion.execute();
        board.toConsole();

        assertEquals(1, pawn.getMoveCount());
        assertNull(startField.getPiece());
        assertNull(pawn.getField());
        assertEquals(promotedQueen, targetField.getPiece());
        assertEquals(targetField, promotedQueen.getField());
    }

    @Test
    void testPawnPromotionUndo() {
        ChessField startField = board.getField("a7");
        ChessField targetField = board.getField("a8");

        Pawn pawn = new Pawn(board, Side.WHITE);
        board.link(startField, pawn);

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField);
        promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);
        Piece promotedQueen = promotion.getPromotedPiece();

        board.toConsole();
        promotion.execute();
        board.toConsole();
        promotion.undo();
        board.toConsole();

        assertEquals(0, pawn.getMoveCount());
        assertNull(targetField.getPiece());
        assertNull(promotedQueen.getField());
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

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField);
        promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);
        Piece promotedQueen = promotion.getPromotedPiece();

        board.toConsole();
        promotion.execute();
        board.toConsole();

        assertEquals(1, pawn.getMoveCount());
        assertNull(startField.getPiece());
        assertNull(blackRook.getField());
        assertNull(pawn.getField());
        assertEquals(promotedQueen, targetField.getPiece());
        assertEquals(targetField, promotedQueen.getField());
    }

    @Test
    void testPawnPromotionWithCaptureUndo() {
        ChessField startField = board.getField("a7");
        ChessField targetField = board.getField("b8");

        Pawn pawn = new Pawn(board, Side.WHITE);
        board.link(startField, pawn);

        Piece blackRook = new Rook(board, Side.BLACK);
        board.link(targetField, blackRook);

        PawnPromotion promotion = new PawnPromotion(board, startField, targetField);
        promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);
        Piece promotedQueen = promotion.getPromotedPiece();

        board.toConsole();
        promotion.execute();
        board.toConsole();
        promotion.undo();
        board.toConsole();

        assertEquals(0, pawn.getMoveCount());
        assertNull(promotedQueen.getField());
        assertEquals(pawn, startField.getPiece());
        assertEquals(startField, pawn.getField());
        assertEquals(blackRook, targetField.getPiece());
        assertEquals(targetField, blackRook.getField());
    }
}
