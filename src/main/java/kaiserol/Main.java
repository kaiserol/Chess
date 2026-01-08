package kaiserol;

import kaiserol.handler.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        // Start game
        System.out.println("Welcome to the game of chess! (Write 'exit' to exit)");
        game.printBoard();

        String[] whiteMoves = {"a2a4", "a4a5", "a5a6", "a6b7", "b7a8"};
        String[] blackMoves = {"h7h6", "h6h5", "h5h4", "h4h3", "h3g2"};

        for (int i = 0; i < whiteMoves.length; i++) {
            System.out.printf("Enter move (White's turn): %s%n", whiteMoves[i]);
            game.executeMove(whiteMoves[i]);
            game.printBoard();

            System.out.printf("Enter move (Black's turn): %s%n", blackMoves[i]);
            game.executeMove(blackMoves[i]);
            game.printBoard();
        }

        // Start the game loop
        game.startGameLoop();
    }
}
