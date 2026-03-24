import java.util.Arrays;

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

    public static boolean isRowFull(Board grid, int row) {
        try {
            for (int i = 0; i < COLS; i++) {
                if (!grid.grid[row][i].equals(Cell.EMPTY)) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public static Cell isGameWon(Board grid) {
        return null;
    }

    public static void printBoard(Board grid) {
        for (int i = 0; i < ROWS; i++) {
            String line = "";
            for (int j = 0; j < COLS; j++) {
                if (j != COLS) {
                    line += "|" + grid.grid[i][j];
                }
                line += grid.grid[i][j] + "|";
            }
            System.out.println(line);
        }
        System.out.println();
    }
}