import javax.swing.*;

public class TestingClass {
    public static void main(String[] args) {
        Cell activeColour = Cell.R;
        Board board = new Board();
        Cell[][] grid = board.getGrid();
        Board.dropCell(board, 1, activeColour);
        Board.printBoard(board);
        System.out.println(Board.cellsInCol(board, 1));
        System.out.println(Board.cellsInCol(board, 0));

    }
}
