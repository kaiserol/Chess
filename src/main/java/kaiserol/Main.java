package kaiserol;

import kaiserol.handler.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.printBoard();
        game.executeMove("a2a4");
        game.printBoard();

        // Start game loop
        game.startGameLoop();
    }
}
