package kaiserol.logic.state;

import kaiserol.logic.Game;
import kaiserol.logic.chessboard.ChessBoard;
import kaiserol.logic.chessboard.Side;
import kaiserol.logic.pieces.King;
import kaiserol.logic.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Extend the GameState enum with additional DRAW objects that describe the reason for the draw.
public class DrawDetectorTest {

    @Test
    public void testInsufficientMaterial() {
        ChessBoard board = new ChessBoard();

        // Only kings
        board.link(board.getField("e1"), new King(board, Side.WHITE));
        board.link(board.getField("e8"), new King(board, Side.BLACK));

        assertTrue(DrawDetector.hasInsufficientMaterial(board));

        // + One Rook (sufficient)
        board.link(board.getField("a1"), new Rook(board, Side.WHITE));
        assertFalse(DrawDetector.hasInsufficientMaterial(board));
    }

    @Test
    public void testThreefoldRepetition() throws Exception {
        // The default starting position (1x)
        Game game = new Game();

        // Moves: Nf3 Nf6 Ng1 Ng8 (Repetition 2x)
        game.executeMove("g1f3");
        game.executeMove("g8f6");
        game.executeMove("f3g1");
        game.executeMove("f6g8");

        assertFalse(DrawDetector.isThreefoldRepetition(game.getBoardHistory()));

        // Moves: Nf3 Nf6 Ng1 Ng8 (Repetition 3x)
        game.executeMove("g1f3");
        game.executeMove("g8f6");
        game.executeMove("f3g1");
        game.executeMove("f6g8");

        assertTrue(DrawDetector.isThreefoldRepetition(game.getBoardHistory()));
    }

    @Test
    public void test50MoveRule() throws Exception {
        Game game = new Game();

        String[] whiteMoves = {"g1f3", "f3g1", "b1a3", "a3b1", "b1c3", "c3b1"};
        String[] blackMoves = {"g8f6", "f6g8", "b8a6", "a6b8", "b8c6", "c6b8"};

        for (int i = 0; i < 9; i++) { // 108 half moves
            for (int j = 0; j < 6; j++) {
                if (game.getGameState() == GameState.DRAW) break;
                game.executeMove(whiteMoves[j]);
                if (game.getGameState() == GameState.DRAW) break;
                game.executeMove(blackMoves[j]);
            }
            if (game.getGameState() == GameState.DRAW) break;
        }

        assertEquals(GameState.DRAW, game.getGameState());
    }
}
