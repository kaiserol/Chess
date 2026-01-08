package kaiserol.logic.moves;

import kaiserol.chessboard.Board;
import kaiserol.chessboard.Field;
import kaiserol.chessboard.Side;
import kaiserol.chessboard.pieces.Pawn;
import kaiserol.chessboard.pieces.Piece;
import kaiserol.chessboard.pieces.Queen;
import kaiserol.chessboard.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PawnPromotionTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testPawnPromotionExecute() {
        Field startField = board.getField("a7");
        Field targetField = board.getField("a8");
        Pawn pawn = new Pawn(Side.WHITE, board, startField);
        startField.setPiece(pawn);

        PawnPromotion promotion = new PawnPromotion(startField, targetField);
        Queen promotedQueen = (Queen) promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);

        promotion.execute();

        assertNull(startField.getPiece());
        assertNull(pawn.getField());
        assertEquals(promotedQueen, targetField.getPiece());
        assertEquals(targetField, promotedQueen.getField());
        assertEquals(1, pawn.getMoveCount());
    }

    @Test
    void testPawnPromotionUndo() {
        Field startField = board.getField("a7");
        Field targetField = board.getField("a8");
        Pawn pawn = new Pawn(Side.WHITE, board, startField);
        startField.setPiece(pawn);

        PawnPromotion promotion = new PawnPromotion(startField, targetField);
        Queen promotedQueen = (Queen) promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);

        promotion.execute();
        promotion.undo();

        assertNull(targetField.getPiece());
        assertNull(promotedQueen.getField());
        assertEquals(pawn, startField.getPiece());
        assertEquals(startField, pawn.getField());
        assertEquals(0, pawn.getMoveCount());
    }

    @Test
    void testPawnPromotionWithCaptureExecute() {
        Field startField = board.getField("a7");
        Field targetField = board.getField("b8");
        Pawn pawn = new Pawn(Side.WHITE, board, startField);
        startField.setPiece(pawn);

        Piece blackRook = new Rook(Side.BLACK, board, targetField);
        targetField.setPiece(blackRook);

        PawnPromotion promotion = new PawnPromotion(startField, targetField);
        Queen promotedQueen = (Queen) promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);

        promotion.execute();

        assertNull(startField.getPiece());
        assertNull(blackRook.getField());
        assertNull(pawn.getField());
        assertEquals(promotedQueen, targetField.getPiece());
        assertEquals(targetField, promotedQueen.getField());
        assertEquals(1, pawn.getMoveCount());
    }

    @Test
    void testPawnPromotionWithCaptureUndo() {
        Field startField = board.getField("a7");
        Field targetField = board.getField("b8");
        Pawn pawn = new Pawn(Side.WHITE, board, startField);
        startField.setPiece(pawn);

        Piece blackRook = new Rook(Side.BLACK, board, targetField);
        targetField.setPiece(blackRook);

        PawnPromotion promotion = new PawnPromotion(startField, targetField);
        Queen promotedQueen = (Queen) promotion.setPromotedPiece(PawnPromotion.Choice.QUEEN);

        promotion.execute();
        promotion.undo();

        assertNull(promotedQueen.getField());
        assertEquals(pawn, startField.getPiece());
        assertEquals(startField, pawn.getField());
        assertEquals(blackRook, targetField.getPiece());
        assertEquals(targetField, blackRook.getField());
        assertEquals(0, pawn.getMoveCount());
    }
}
