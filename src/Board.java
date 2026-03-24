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

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Cell value) {
        grid[row][col] = value;
    }

    public void dropCell(int row, Cell activeColour) {
        int lowestRow = -1;
        for (int i = 0; i < 6; i++) {
            if (!grid[row][i].equals(Cell.EMPTY)) {
                lowestRow = i;
                break;
            }
        }

        if (lowestRow == -1) {
            throw new IllegalArgumentException("This Row is full!");
        }
        grid[row][lowestRow+1] = activeColour;
    }
}