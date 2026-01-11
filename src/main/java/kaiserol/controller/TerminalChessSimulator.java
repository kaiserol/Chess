package kaiserol.controller;

import java.util.ArrayList;
import java.util.List;

public class TerminalChessSimulator extends ChessController {
    private final List<Simulation> simulations;

    public TerminalChessSimulator(Game game) {
        super(game);
        this.simulations = new ArrayList<>();
    }

    public void addSimulation(String[] whiteMoves, String[] blackMoves) {
        if (whiteMoves == null || blackMoves == null) throwException("Moves cannot be null");
        if (whiteMoves.length == 0 || blackMoves.length == 0) throwException("Moves cannot be empty");

        int difference = whiteMoves.length - blackMoves.length;
        if (difference < 0) throwException("White moves must be longer than black moves");
        if (difference > 1) throwException("White moves must not be longer than black moves by more than 1 move");

        simulations.add(new Simulation(whiteMoves, blackMoves));
    }

    @Override
    public void run() {
        for (int i = 0; i < simulations.size(); i++) {
            Simulation simulation = simulations.get(i);
            runSimulation(simulation.whiteMoves(), simulation.blackMoves(), i + 1);
        }
    }

    private void runSimulation(String[] whiteMoves, String[] blackMoves, int simulationNumber) {
        String SEP = "-".repeat(8);
        printMessage(SEP + simulationNumber + ". Simulation started " + SEP);
        printMessage("Initial state of the game:");
        game.getBoard().toConsole();

        int maxMoves = Math.max(whiteMoves.length, blackMoves.length);
        for (int i = 0; i < maxMoves; i++) {
            if (i < whiteMoves.length) {
                printMessage("White moves: " + whiteMoves[i]);
                try {
                    game.executeMove(whiteMoves[i]);
                    game.getBoard().toConsole();
                } catch (Exception e) {
                    printError(e.getMessage());
                    break;
                }
            }

            if (i < blackMoves.length) {
                printMessage("Black moves: " + blackMoves[i]);
                try {
                    game.executeMove(blackMoves[i]);
                    game.getBoard().toConsole();
                } catch (Exception e) {
                    printError(e.getMessage());
                    break;
                }
            }
        }

        printMessage(SEP + simulationNumber + ". Simulation ended " + SEP);
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private void printError(String error) {
        System.out.println("Error: " + error);
    }

    private void throwException(String message) {
        throw new IllegalArgumentException(message);
    }

    private record Simulation(String[] whiteMoves, String[] blackMoves) {
    }
}
