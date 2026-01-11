# Chess

A modern chess project in Java with planned multiplayer functionality.

## 🛠️ Development

### Technology Stack

- **IDE:** IntelliJ IDEA (Community / Ultimate)
- **Language**: Java
- **Build Tool**: Maven
- **Testing**: JUnit 5
- **Architecture**: Object-oriented design with clear separation of game logic and presentation

### Notes for IntelliJ IDEA

1. Open project → `Chess/`
2. Select Project SDK: **JDK 24**
3. IntelliJ automatically recognizes the Maven project
4. To start:
    - Menu: *Run → Run 'Main'*
    - or custom Run configuration with Main-Class (e.g., `kaiserol.Main`)

### Important Maven Commands

| Command                  | Purpose                          |
|--------------------------|----------------------------------|
| `mvn clean install`      | Clean and rebuild project        |
| `mvn compile`            | Compile the project              |
| `mvn dependency:resolve` | Only update dependencies         |
| `mvn test`               | Run tests                        |

## ⚙️ Troubleshooting

### Check Java Version

- Check if Java 24 is installed:
    ```bash
    java -version
    ```
- Example output:
    ```bash
    openjdk version "24.0.1" 2025-04-15
    ```

If another version is displayed, adjust `JAVA_HOME` if necessary or install a suitable JDK version.

## 📋 Project Description

**Chess** is a chess implementation in Java. The project
is under active development and is being expanded with new features.

### 🎯 Implemented Features

- [x] 8x8 chessboard with field management
- [x] Chess pieces (King, Queen, Rook, Bishop, Knight, Pawn)
- [x] Implemented chess piece movements (Castling, En Passant, Pawn Promotion, moving and capturing)
- [x] Rule-compliant move validation for all pieces
- [x] Extensive unit tests for game logic
- [x] Chess rules for ending the game (Checkmate, Stalemate)
- [x] Various handlers (Terminal Chess, Simulation Chess, GUI Chess)
  - [x] Console-based representation of the board (Terminal chess)
  - [x] Chess simulation mode (Simulation chess)

### 🚀 Planned Features

- [ ] Pawn promotion implemented in handlers
- [ ] 50-move rule, 3-fold repetition
- [ ] Chess notation (PGN format)
- [ ] Graphical User Interface (GUI)
- [ ] Game state saving and restoration
- [ ] Server-client implementation (Multiplayer server)