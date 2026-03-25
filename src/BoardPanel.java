import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {

    private static final int CELL_SIZE = 100;
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int HEADER_HEIGHT = 100;

    private static Board board;
    private boolean isRedTurn = true;
    private Cell winner = null;
    private boolean isDraw = false;

    public BoardPanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE + HEADER_HEIGHT));
        setBackground(new Color(15, 15, 35));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (winner != null || isDraw) return;

                int col = e.getX() / CELL_SIZE;
                if (col < 0 || col >= COLS) return;

                Cell activeColour = isRedTurn ? Cell.R : Cell.Y;
                try {
                    Board.dropCell(board, col, activeColour);
                    winner = Board.isGameWon(board);
                    if (winner == Cell.EMPTY) winner = null;
                    if (winner == null && Board.isGridFull(board)) isDraw = true;
                    isRedTurn = !isRedTurn;
                    repaint();
                } catch (IllegalArgumentException ex) {
                    // column is full, do nothing
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawHeader(g2);
        drawBoard(g2);
    }

    private void drawHeader(Graphics2D g2) {
        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Georgia", Font.BOLD, 28));
        FontMetrics titleMetrics = g2.getFontMetrics();
        String title = "CONNECT FOUR";
        int titleX = (COLS * CELL_SIZE - titleMetrics.stringWidth(title)) / 2;
        g2.drawString(title, titleX, 38);

        // Status message
        String status;
        Color statusColor;

        if (winner != null) {
            status = (winner == Cell.R ? "Red" : "Yellow") + " wins!";
            statusColor = winner == Cell.R ? new Color(220, 60, 60) : new Color(240, 200, 40);
        } else if (isDraw) {
            status = "It's a draw!";
            statusColor = Color.LIGHT_GRAY;
        } else {
            status = (isRedTurn ? "Red" : "Yellow") + "'s turn";
            statusColor = isRedTurn ? new Color(220, 60, 60) : new Color(240, 200, 40);
        }

        // Turn indicator circle
        g2.setColor(statusColor);
        g2.fillOval(COLS * CELL_SIZE / 2 - 150, 52, 22, 22);

        // Status text
        g2.setFont(new Font("Georgia", Font.PLAIN, 18));
        g2.drawString(status, COLS * CELL_SIZE / 2 - 120, 69);
    }

    private void drawBoard(Graphics2D g2) {
        // Board background
        g2.setColor(new Color(30, 80, 180));
        g2.fillRoundRect(0, HEADER_HEIGHT, COLS * CELL_SIZE, ROWS * CELL_SIZE, 16, 16);

        // Cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Cell cell = Board.getCell(board.getGrid(), row, col);

                if (cell == Cell.R) {
                    g2.setColor(new Color(220, 60, 60));
                } else if (cell == Cell.Y) {
                    g2.setColor(new Color(240, 200, 40));
                } else {
                    g2.setColor(new Color(15, 15, 35));
                }

                g2.fillOval(
                        col * CELL_SIZE + 10,
                        HEADER_HEIGHT + row * CELL_SIZE + 10,
                        CELL_SIZE - 20,
                        CELL_SIZE - 20
                );
            }
        }
    }
}