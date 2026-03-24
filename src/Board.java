import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private Cell[][] grid;

    public Board() {
        grid = new Cell[ROWS][COLS];
        for (Cell[] row : grid)
            Arrays.fill(row, Cell.EMPTY);
    }

    public static Cell getCell(Cell[][] grid, int row, int col) {
        return grid[row][col];
    }

    public static void setCell(Board grid, int row, int col, Cell value) {
        grid.grid[row][col] = value;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public static void dropCell(Board grid, int col, Cell activeColour) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (grid.grid[i][col].equals(Cell.EMPTY)) {
                grid.grid[i][col] = activeColour;
                return;
            }
        }
        throw new IllegalArgumentException("This column is full!");
    }

    public static boolean isGridFull(Board grid) {
        try {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (grid.grid[i][j].equals(Cell.EMPTY)) {
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Cell checkFour(Cell[][] grid, int row, int col, int direction, Cell colour) {
        // this function checks if there is four in a row
        //Cell start = grid[row][col];
        if (direction == 0) { // direction 0 is diagonal
            if ((grid[row][col] == colour) && (grid[row+1][col+1] == colour) && (grid[row+2][col+2] == colour) && (grid[row+3][col+3] == colour)) {
                return colour;
            }
        } else if (direction == 1) { // diection 1 is horizontal;
            if ((grid[row][col] == colour) && (grid[row][col+1] == colour) && (grid[row][col+2] == colour) && (grid[row][col+3] == colour)) {
                return colour;
            }
        } else if (direction == 2) { // direction 2 is vertical
            if ((grid[row][col] == colour) && (grid[row+1][col] == colour) && (grid[row+2][col] == colour) && (grid[row+3][col] == colour)) {
                return colour;
            }
        } else if (direction == 3) { // anti diagonal
            if ((grid[row][col] == colour) && (grid[row+1][col-1] == colour) && (grid[row+2][col-2] == colour) && (grid[row+3][col-3] == colour)) {
                return colour;
            }
        }

        return Cell.EMPTY;
    }

    public static Cell isGameWon(Board board)
    {
        Cell[][] grid = board.getGrid();

        // diagonal - needs both bounds restricted
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLS - 3; j++) {
                if (checkFour(grid, i, j, 0, Cell.Y) == Cell.Y) {
                    return Cell.Y;
                } else if (checkFour(grid, i, j, 0, Cell.R) == Cell.R) {
                    return Cell.R;
                }
            }
        }
        // horizontal - i can go full ROWS, j restricted to COLS - 3
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 3; j++) {
                if (checkFour(grid, i, j, 1, Cell.Y) == Cell.Y) {
                    return Cell.Y;
                } else if (checkFour(grid, i, j, 1, Cell.R) == Cell.R) {
                    return Cell.R;
                }
            }
        }
        // vertical - i restricted to ROWS - 3, j can go full COLS
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLS; j++) {
                if (checkFour(grid, i, j, 2, Cell.Y) == Cell.Y) {
                    return Cell.Y;
                } else if (checkFour(grid, i, j, 2, Cell.R) == Cell.R) {
                    return Cell.R;
                }
            }
        }
        // anti diagonal - needs both bounds restricted specially
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 3; j < COLS; j++) {
                if (checkFour(grid, i, j, 3, Cell.Y) == Cell.Y) {
                    return Cell.Y;
                } else if (checkFour(grid, i, j, 3, Cell.R) == Cell.R) {
                    return Cell.R;
                }
            }
        }

        return Cell.EMPTY;
    }


    private static String cellToString(Cell cell) {
        if (cell == Cell.R) return "R";
        if (cell == Cell.Y) return "Y";
        return "O";
    }

    public static void printBoard(Board grid) {
        for (int i = 0; i < ROWS; i++) {
            String line = "|";
            for (int j = 0; j < COLS; j++) {
                line += cellToString(grid.grid[i][j]) + "|";
            }
            System.out.println(line);
        }
        System.out.println();
    }

    public static boolean isMoveValid(Board grid, int col) {
        // i only need to check the first row item because of how gravity in the game works, the piece will always drop to the bottom.
        return grid.getGrid()[0][col] == Cell.EMPTY;
    }

    public static List<Integer> colsAvaliable(Board grid) {
        List<Integer> cols = new ArrayList<>();
        for (int i = 0; i < COLS; i++) {
            int count = 0;
            for (int j = 0; j < ROWS; j++) {
                if (grid.getGrid()[j][i] != Cell.EMPTY) {
                    count++;
                }
            }
            if (count != ROWS) {
                cols.add(i);
            }
        }
        return cols;
    }

    public Board cloneBoard() {
        Board clone = new Board();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                clone.grid[i][j] = this.grid[i][j];
            }
        }
        return clone;
    }

    public void resetBoard() {
        for (Cell[] row : grid)
            Arrays.fill(row, Cell.EMPTY);
    }
}