package kaiserol;


import kaiserol.controller.TerminalChess;
import kaiserol.controller.TerminalChessSimulator;
import kaiserol.controller.UIChess;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        ChessController controller;
        if (args.length > 0 && args[0].equalsIgnoreCase("-simulation")) {
            TerminalChessSimulator simulator = new TerminalChessSimulator(game);

            // Pawn Promotion
            addPawnPromotion(simulator);

            // Kingside Castling
            addKingSideCastling(simulator);

            // Queenside Castlin
            addQueenSideCastling(simulator);

            controller = simulator;
        } else if (args.length > 0 && args[0].equalsIgnoreCase("-terminal")) {
            controller = new TerminalChess(game);
        } else {
            controller = new UIChess(game);
        }

        // Starts the controller
        controller.run();
    }

    private static void addPawnPromotion(TerminalChessSimulator simulator) {
        String[] whiteMoves = {
                "a2a4", // Pawn from a2 to a4
                "a4a5", // Pawn from a4 to a5
                "a5a6", // Pawn from a5 to a6
                "a6b7", // Pawn captures diagonally on b7
                "b7a8", // Pawn captures diagonally on a8 and becomes a queen
        };

        String[] blackMoves = {
                "g7g6", // Pawn from g7 to g6
                "h7h6", // Pawn from h7 to h6
                "g6g5", // Pawn from g6 to g5
                "h6h5", // Pawn from h6 to h5
        };

        simulator.addSimulation("Pawn Promotion", whiteMoves, blackMoves);
    }

    private static void addKingSideCastling(TerminalChessSimulator simulator) {
        String[] whiteMoves = {
                "e2e4", // Pawn from e2 to e4
                "f1d3", // Bishop from f1 to d3 (away from the king)
                "g1f3", // Knight from g1 to f3 (away from the king)
                "e1g1", // King castles short (kingside): King to g1, rook to f1
        };

        String[] blackMoves = {
                "g7g6", // Pawn from g7 to g6
                "h7h6", // Pawn from h7 to h6
                "g6g5", // Pawn from g6 to g5
        };

        simulator.addSimulation("Kingside Castling", whiteMoves, blackMoves);
    }

    private static void addQueenSideCastling(TerminalChessSimulator simulator) {
        String[] whiteMoves = {
                "d2d4", // Pawn from d2 to d4
                "c1e3", // Bishop from c1 to e3 (away from king)
                "b1c3", // Knight from b1 to c3 (away from king)
                "d1d2", // Queen from d1 to d2 (away from the king)
                "e1c1", // King castles long (queenside): King to c1, rook to d1
        };

        String[] blackMoves = {
                "g7g6", // Pawn from g7 to g6
                "h7h6", // Pawn from h7 to h6
                "g6g5", // Pawn from g6 to g5
                "h6h5", // Pawn from h6 to h5
        };

        simulator.addSimulation("Queenside Castling", whiteMoves, blackMoves);
    }
}
