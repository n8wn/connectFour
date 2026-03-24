import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {

    private static final int CELL_SIZE = 100;
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static Board board;
    private boolean isRedTurn = true;

    public BoardPanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                Cell activeColour = isRedTurn ? Cell.R : Cell.Y;
                try {
                    Board.dropCell(board, col, activeColour);
                    isRedTurn = !isRedTurn;
                    repaint();
                } catch (IllegalArgumentException ex) {
                    // do nothing
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, COLS * CELL_SIZE, ROWS * CELL_SIZE);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Cell cell = Board.getCell(board.getGrid(), row, col);

                if (cell == Cell.R) {
                    g2.setColor(Color.RED);
                } else if (cell == Cell.Y) {
                    g2.setColor(Color.YELLOW);
                } else {
                    g2.setColor(Color.WHITE);
                }

                g2.fillOval(col * CELL_SIZE + 10, row * CELL_SIZE + 10,
                        CELL_SIZE - 20, CELL_SIZE - 20);
            }
        }
    }


}