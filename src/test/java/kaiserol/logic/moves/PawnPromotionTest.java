package kaiserol.logic.moves;

import kaiserol.chessboard.ChessBoard;
import kaiserol.chessboard.ChessField;
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
        Queen promotedQueen = (Queen) promotion.choosePromotedPiece(PawnPromotion.Choice.QUEEN);

        board.printBoard();
        promotion.execute();
        board.printBoard();

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
        Queen promotedQueen = (Queen) promotion.choosePromotedPiece(PawnPromotion.Choice.QUEEN);

        board.printBoard();
        promotion.execute();
        board.printBoard();
        promotion.undo();
        board.printBoard();

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
        Queen promotedQueen = (Queen) promotion.choosePromotedPiece(PawnPromotion.Choice.QUEEN);

        board.printBoard();
        promotion.execute();
        board.printBoard();

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
        Queen promotedQueen = (Queen) promotion.choosePromotedPiece(PawnPromotion.Choice.QUEEN);

        board.printBoard();
        promotion.execute();
        board.printBoard();
        promotion.undo();
        board.printBoard();

        assertEquals(0, pawn.getMoveCount());
        assertNull(promotedQueen.getField());
        assertEquals(pawn, startField.getPiece());
        assertEquals(startField, pawn.getField());
        assertEquals(blackRook, targetField.getPiece());
        assertEquals(targetField, blackRook.getField());
    }
}
