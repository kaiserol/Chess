package kaiserol.logic.state;

import kaiserol.logic.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FENTest {

    @Test
    public void testFENInitialPosition() {
        Game game = new Game();
        String fen = game.getLatestFEN();

        // Initial FEN: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
        assertTrue(fen.contains("w KQkq - 0 1"), "Initial FEN should contain castling and no en passant.\nGot: " + fen);
    }

    @Test
    public void testFENAfterPawnJump() throws Exception {
        Game game = new Game();
        game.executeMove("e2e4");

        String fen = game.getLatestFEN();

        // After e2e4: rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1
        assertTrue(fen.contains("b KQkq e3 0 1"), "FEN after e2e4 should contain en passant target e3.\nGot: " + fen);
    }

    @Test
    public void testFENCastlingRightsRemoval() throws Exception {
        Game game = new Game();

        // Move white king
        game.executeMove("e2e4");
        game.executeMove("e7e5");
        game.executeMove("e1e2");

        String fen = game.getLatestFEN();

        // White king moved, so KQkq -> kq
        assertTrue(fen.contains("b kq -"), "FEN should not have white castling rights after king move.\nGot: " + fen);
    }
}
