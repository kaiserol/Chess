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

    public void addSimulation(@NotNull String[] whiteMoves, @NotNull String[] blackMoves) {
        if (whiteMoves.length == 0 || blackMoves.length == 0)
            throw new IllegalArgumentException("Move lists must not be empty.");

        int difference = whiteMoves.length - blackMoves.length;
        if (difference < 0) throw new IllegalArgumentException("White must not have fewer moves than Black.");
        if (difference > 1)
            throw new IllegalArgumentException("White must not have more than one move more than Black.");

        simulations.add(new Simulation(whiteMoves, blackMoves));
    }

    @Override
    public void run() {
        for (int i = 0; i < simulations.size(); i++) {
            Simulation simulation = simulations.get(i);
            runSimulation(simulation.whiteMoves(), simulation.blackMoves(), i + 1);
        }

        scanner.close();
    }

    @Override
    public PawnPromotion.Choice getPromotionChoice() {
        return readPromotionChoice(scanner, this::printMessage, this::printError);
    }

    private void runSimulation(String[] whiteMoves, String[] blackMoves, int simulationNumber) {
        String SEP = "-".repeat(8);
        printlnMessage("%s %d. Simulation started %s".formatted(SEP, simulationNumber, SEP));
        printlnMessage("Initial state of the game:");
        game.getBoard().toConsole();

        int maxMoves = Math.max(whiteMoves.length, blackMoves.length);
        for (int i = 0; i < maxMoves; i++) {
            if (i < whiteMoves.length) {
                printlnMessage("White moves: " + whiteMoves[i]);
                try {
                    game.executeMove(whiteMoves[i]);
                    game.getBoard().toConsole();
                } catch (Exception e) {
                    printlnError(e.getMessage());
                    break;
                }
            }

            if (i < blackMoves.length) {
                printlnMessage("Black moves: " + blackMoves[i]);
                try {
                    game.executeMove(blackMoves[i]);
                    game.getBoard().toConsole();
                } catch (Exception e) {
                    printlnError(e.getMessage());
                    break;
                }
            }
        }

        printlnMessage("%s %d. Simulation ended %s".formatted(SEP, simulationNumber, SEP));
    }

    private record Simulation(String[] whiteMoves, String[] blackMoves) {
    }
}
