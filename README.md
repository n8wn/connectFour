# Connect Four

A Java implementation of Connect Four with a Swing-based GUI, supporting Human vs Human play with an AI opponent in development.

## Features

- 6x7 grid rendered with Java Swing
- Two-player Human vs Human mode
- Turn indicator and win/draw detection across all directions (horizontal, vertical, diagonal, anti-diagonal)
- Clean graphical interface with coloured piece rendering
- Console board printing for debugging

## Project Structure

```
├── Cell.java          # Enum representing board cell states (R, Y, EMPTY)
├── Board.java         # Core game logic — grid management, move validation, win detection
├── BoardPanel.java    # Swing JPanel — renders the board and handles mouse input
├── GameWindow.java    # JFrame window setup
└── Main.java          # Entry point — launches the game and runs board tests
```

## How to Run

1. Clone the repository
2. Compile all `.java` files
3. Run `Main.java`

```bash
javac *.java
java Main
```

## How to Play

- Click any column to drop a piece into it
- Red always goes first
- The game detects wins in all four directions and draws when the board is full
- Close and rerun to start a new game

## AI Player (In Development)

An AI opponent is planned for a future update. The `Board` class has been designed with AI support in mind — notably `colsAvailable()` for move enumeration and `cloneBoard()` for simulating future game states without affecting the real board. This groundwork will support a search-based AI such as Minimax with Alpha-Beta pruning or Monte Carlo Tree Search.

## Tech Stack

Java, OOP, Java Swing, Game Logic, Win Detection Algorithms
