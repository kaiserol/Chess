package kaiserol.controller;

import kaiserol.ChessController;
import kaiserol.Game;
import kaiserol.moves.PawnPromotion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalChessSimulator extends ChessController {
    private final List<Simulation> simulations;
    private final Scanner scanner;

    public TerminalChessSimulator(Game game) {
        super(game);
        this.simulations = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addSimulation(@NotNull String name, String[] whiteMoves, @NotNull String[] blackMoves) {
        if (whiteMoves.length == 0 || blackMoves.length == 0)
            throw new IllegalArgumentException("Move lists must not be empty.");

        int difference = whiteMoves.length - blackMoves.length;
        if (difference < 0) throw new IllegalArgumentException("White must not have fewer moves than Black.");
        if (difference > 1)
            throw new IllegalArgumentException("White must not have more than one move more than Black.");

        simulations.add(new Simulation(name, whiteMoves, blackMoves));
    }

    @Override
    public void run() {
        for (int i = 0; i < simulations.size(); i++) {
            if (i > 0) printlnMessage("\n");
            Simulation sim = simulations.get(i);
            runSimulation(i + 1, sim);
            game.reset();
        }

        scanner.close();
    }

    @Override
    public PawnPromotion.Choice getPromotionChoice() {
        return readPromotionChoice(scanner, this::printMessage, this::printError);
    }

    private void runSimulation(int simulationNumber, Simulation simulation) {
        final String simulationName = simulation.name();
        final String[] whiteMoves = simulation.whiteMoves();
        final String[] blackMoves = simulation.blackMoves();

        String SEP = "-".repeat(8);
        printlnMessage("%s %d. Simulation '%s' started %s".formatted(SEP, simulationNumber, simulationName, SEP));
        printlnMessage("Initial state of the game:");
        printBoard();

        int maxMoves = Math.max(whiteMoves.length, blackMoves.length);
        for (int i = 0; i < maxMoves; i++) {
            if (i < whiteMoves.length) {
                printlnMessage("White moves: " + whiteMoves[i]);
                try {
                    game.executeMove(whiteMoves[i]);
                    printBoard();
                } catch (Exception e) {
                    printlnError(e.getMessage());
                    break;
                }
            }

            if (i < blackMoves.length) {
                printlnMessage("Black moves: " + blackMoves[i]);
                try {
                    game.executeMove(blackMoves[i]);
                    printBoard();
                } catch (Exception e) {
                    printlnError(e.getMessage());
                    break;
                }
            }
        }

        printlnMessage("%s %d. Simulation '%s' ended %s".formatted(SEP, simulationNumber, simulationName, SEP));
    }

    private record Simulation(String name, String[] whiteMoves, String[] blackMoves) {
    }
}
