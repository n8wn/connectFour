import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Board board = new Board();
        add(new BoardPanel(board));
        pack();
        setLocationRelativeTo(null);
    }
}
