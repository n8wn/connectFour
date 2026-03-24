import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Cell activeColour = Cell.R;
        Board grid = new Board();
        Board.printBoard(grid);

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}