# Connect Four

A full-featured Java implementation of Connect Four with a polished Swing GUI, three game modes, a difficulty-selectable AI opponent powered by Minimax with Alpha-Beta pruning, and a packaged macOS application.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue)
![AI](https://img.shields.io/badge/AI-Minimax%20%2B%20Alpha--Beta-brightgreen)

---

## Download

A pre-built macOS application is available to download — no Java installation or compilation required.

**[Download Connect Four (.dmg)](https://drive.google.com/file/d/1IlbtKOs0zez-GAOjld6VMNwN8tEcM6C3/view?usp=sharing)**

### macOS Setup (Required)

Because the app isn't signed with an Apple Developer certificate, macOS will block it from opening by default. To remove the quarantine flag, run the following command in your terminal after dragging the app to your Applications folder:

```bash
xattr -cr /Applications/connectFour.app
```

Then double-click the app to launch it as normal. You only need to run this command once.

> **What does this command do?** `xattr -cr` recursively removes extended attributes (including the `com.apple.quarantine` flag macOS adds to downloaded files) from the app bundle. It does not modify the app itself.

---

## Features

- **Three game modes** — Human vs Human, Human vs AI, AI vs AI
- **Three AI difficulty levels** — Easy, Medium, Hard
- **Minimax AI with Alpha-Beta pruning** — searches up to 6 moves ahead on Hard
- **Heuristic board evaluation** — scores windows, rewards centre control, detects forks
- **Win detection in all four directions** — horizontal, vertical, diagonal, anti-diagonal
- **Polished Swing GUI** — custom-painted dialogs, hover indicators, animated piece drops
- **Non-blocking AI** — AI computation runs on a background thread so the UI never freezes
- **Draw detection** — recognises a full board with no winner
- **Play Again / Quit dialog** — shown at the end of every game

---

## Project Structure

```
src/
├── Main.java            # Entry point — launches the Swing application
├── Cell.java            # Enum: R, Y, EMPTY — represents board cell states
├── Board.java           # Core game logic — grid, move validation, win detection, board cloning
├── BoardPanel.java      # Swing JPanel — renders the board, handles mouse input and game loop
├── GameWindow.java      # JFrame setup — initialises players and wires everything together
├── Player.java          # Interface — getMove(Board) and getColour()
├── HumanPlayer.java     # Implements Player — stores pending mouse click move
├── AIPlayer.java        # Implements Player — Minimax + Alpha-Beta + heuristic evaluation
├── ModeDialog.java      # Swing dialog — Human vs Human / Human vs AI / AI vs AI
├── DifficultyDialog.java# Swing dialog — Easy / Medium / Hard
└── GameOverDialog.java  # Swing dialog — Play Again / Quit
```

---

## How to Run (from source)

**Requirements:** Java 11 or later

```bash
# 1. Clone the repository
git clone https://github.com/n8wn/connectFour.git
cd connectFour/src

# 2. Compile all source files
javac *.java

# 3. Run the application
java Main
```

---

## How to Play

1. On launch, select a game mode: **Human vs Human**, **Human vs AI**, or **AI vs AI**
2. If playing against the AI, choose a difficulty: **Easy**, **Medium**, or **Hard**
3. Click any column to drop your piece into it — pieces fall to the lowest available row
4. **Red always goes first**
5. The first player to connect four pieces in a row (horizontally, vertically, or diagonally) wins
6. If the board fills with no winner, the game is a draw
7. A dialog will appear at the end — choose to play again or quit

---

## AI Design

The AI opponent is the core of this project. Here's how it works in detail.

### Minimax with Alpha-Beta Pruning

The AI uses the **Minimax algorithm** to search the game tree, alternating between maximising its own score and minimising the opponent's. At each node it:

1. Clones the board (`Board.cloneBoard()`) so the real game state is never affected
2. Simulates every available move (`Board.colsAvailable()`)
3. Recursively evaluates the resulting positions
4. Returns the move with the highest score

**Alpha-Beta pruning** is applied to cut branches that cannot affect the final result. If the current path can never beat an already-found option, the search stops early — significantly reducing the number of positions evaluated at deeper depths.

```
Depth 2 (Easy)  →  looks 2 moves ahead
Depth 4 (Medium) →  looks 4 moves ahead
Depth 6 (Hard)   →  looks 6 moves ahead
```

Win/loss states are scored with a depth bonus so the AI prefers faster wins and longer survival:

```java
if (winner == colour)         return 100000 + depth;  // faster win = higher score
if (winner == opponentColour) return -100000 - depth; // faster loss = lower score
```

### Board Evaluation Heuristic

When the search reaches its depth limit without a terminal state, `evaluateBoard()` scores the position statically:

- **Sliding window scoring** — every group of four consecutive cells (horizontal, vertical, diagonal, anti-diagonal) is scored based on how many AI or opponent pieces it contains:
  - 3 AI + 1 empty → +5
  - 2 AI + 2 empty → +2
  - 3 opponent + 1 empty → −4
- **Centre column control** — occupying the centre column is rewarded (+3 per piece), as it maximises the number of winning lines available
- **Fork detection** — positions where a player has two simultaneous winning threats (a fork) are detected and scored heavily (+20 for AI fork, −20 for opponent fork)

### Difficulty Levels

| Level  | Search Depth | Random Move Chance |
|--------|-------------|-------------------|
| Easy   | 2           | 55%               |
| Medium | 4           | 30%               |
| Hard   | 6           | 0%                |

Easy and Medium modes occasionally make random moves to simulate human-like imperfection.

### Threading

Minimax is computationally expensive, especially at depth 6. Running it on the Event Dispatch Thread would freeze the UI while the AI thinks. To prevent this, AI computation is offloaded to a `SwingWorker` background thread:

```java
SwingWorker<Integer, Void> worker = new SwingWorker<>() {
    @Override
    protected Integer doInBackground() {
        return currentPlayer.getMove(board); // runs off the UI thread
    }

    @Override
    protected void done() {
        int col = get();
        Board.dropCell(board, col, currentPlayer.getColour()); // back on UI thread
        repaint();
    }
};
worker.execute();
```

This also enables **AI vs AI mode**, where both players think on background threads and the UI updates cleanly between moves.

---

## Board Architecture

`Board.java` was designed from the start with AI compatibility in mind. Key methods:

| Method | Purpose |
|--------|---------|
| `cloneBoard()` | Deep copies the grid so Minimax can simulate moves without mutating real state |
| `colsAvailable()` | Returns a list of non-full columns for move enumeration |
| `dropCell()` | Applies gravity — drops a piece to the lowest empty row in a column |
| `isGameWon()` | Checks all four directions for a four-in-a-row |
| `isGridFull()` | Detects draw conditions |

---

## GUI Details

The interface is built entirely with Java Swing, with all visual components custom-painted using `Graphics2D`:

- **BoardPanel** — renders the 6×7 grid with circular cells, drop shadows, and a subtle shine effect on empty cells. Handles mouse click and hover events
- **ModeDialog** — modal dialog for selecting game mode, shown on launch
- **DifficultyDialog** — modal dialog for selecting AI difficulty, shown when Human vs AI is chosen
- **GameOverDialog** — modal dialog shown at the end of each game with Play Again / Quit options
- All dialogs use a consistent dark navy colour scheme with accent-coloured buttons that highlight on hover

---

## Tech Stack

- **Language:** Java
- **GUI:** Java Swing (JPanel, JFrame, JDialog, Graphics2D)
- **AI:** Minimax, Alpha-Beta Pruning, Heuristic Evaluation
- **Concurrency:** SwingWorker
- **Packaging:** macOS DMG

---

## Planned Improvements

- [ ] Animated piece drop (smooth fall transition)
- [ ] Sound effects on piece placement and win
- [ ] Score tracking across multiple rounds
- [ ] Iterative deepening for more consistent AI response times
- [ ] Windows and Linux packaging
