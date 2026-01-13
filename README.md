# ♟️ Java Chess

A modern chess engine and application, developed with a focus on clean object-oriented design, comprehensive test
coverage, and extensibility.

## 🚀 Overview

This project provides a full chess logic implementation in Java. It supports all official FIDE rules, various user
interfaces (Terminal & GUI), and is prepared for easy expansion (e.g., multiplayer, engine connection).

### ✨ Key Features

- **Complete Rule Sets**:
    - All standard moves (En Passant, Castling, Pawn Promotion).
    - Legal move validation (prevents moves that put one's own king in check).
    - End-state detection: Checkmate, Stalemate, Draw (insufficient material, threefold repetition, 50-move rule).
- **Flexible Interface System**:
    - **Graphical User Interface (GUI)**: Intuitive operation for players.
    - **Terminal Mode**: Lightweight control via the console.
    - **Simulation Mode**: Automated move sequences for debugging and testing.
- **Robust Undo/Redo**: Full support for undoing and redoing moves based on board snapshots (FEN-based).
- **High Quality**: Extensive JUnit test suite for piece logic and game states.

## 🛠️ Technology Stack

- **Language**: Java 24
- **Build System**: Maven 3.9.11
- **Testing**: JUnit 5
- **Architecture**: MVC pattern (Model-View-Controller) with decoupled logic.

## 🚦 Getting Started

### Prerequisites

- **JDK 24** or higher
- **Maven** installed (or via IDE)

### Installation

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA or another IDE.
3. Allow Maven dependencies to load.

### Starting the Application

The application can be started in different modes:

| Mode              | Command / Parameter               | Description                          |
|-------------------|-----------------------------------|--------------------------------------|
| **GUI (Default)** | `java -jar chess.jar`             | Starts the graphical user interface. |
| **Terminal**      | `java -jar chess.jar -terminal`   | Starts the game in the console.      |
| **Simulation**    | `java -jar chess.jar -simulation` | Executes a predefined move sequence. |

## 💻 Development

### Important Maven Commands

- **Build project**: `mvn clean install`
- **Run tests**: `mvn test`
- **Compile**: `mvn compile`

### Project Structure

- `kaiserol.logic`: Core logic, pieces, move validation, and state checking.
- `kaiserol.controller`: Game control and state management.
- `kaiserol.view`: (Planned) Separation of UI components.
- `kaiserol.Main`: Entry point of the application.

## 🗺️ Roadmap & Progress

### ✅ Completed Milestones

- [x] **Core Chess Logic**: Full implementation of FIDE rules, including special moves (Castling, En Passant,
  Promotion).
- [x] **Move Validation**: Legal move calculation including check detection and mate/stalemate states.
- [x] **Multi-Interface Support**: Terminal-based play, simulation mode, and a basic GUI.
- [x] **Robust Test Suite**: High coverage for piece movement and edge cases.
- [x] **Draw Detection**: Support for 50-move rule, threefold repetition, and insufficient material.
- [x] **FEN-based State Management**: Efficient board snapshots for undo/redo and position tracking.

### 🚀 Upcoming Features

- [ ] **PGN Support**: Import and export of games in the standardized Portable Game Notation format.
- [ ] **Advanced GUI**: Better graphics, drag-and-drop support, and move animations.
- [ ] **Multiplayer**: Server-client architecture for online play.
- [ ] **Persistence System**: Save and load game states to/from files.
- [ ] **AI Engine**: Integration of a basic move-finding algorithm or UCI support for external engines (like Stockfish).
- [ ] **Clock System**: Blitz/Rapid/Standard timing support.

## 📄 License

This project is licensed under the **MIT License**. You are free to copy, modify, and distribute the software, provided
that the original copyright notice and license are included.

See the [LICENSE](LICENSE) file for the full license text and the [NOTICE](NOTICE) file for additional copyright notices.