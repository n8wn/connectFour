import javax.swing.*;
import java.awt.*;

public class GameOverDialog extends JDialog {

    public static final int PLAY_AGAIN = 0;
    public static final int QUIT       = 1;

    private int result = QUIT;

    public GameOverDialog(Window parent, String message) {
        super(parent, "Game Over", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(0, 24));
        panel.setBackground(new Color(10, 10, 28));
        panel.setBorder(BorderFactory.createEmptyBorder(36, 48, 32, 48));

        // Result message
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Georgia", Font.BOLD, 26));
        label.setForeground(Color.WHITE);

        // Buttons
        JPanel buttons = new JPanel(new GridLayout(1, 2, 16, 0));
        buttons.setOpaque(false);

        JButton playAgain = createButton("Play Again", new Color(80, 200, 120));
        JButton quit      = createButton("Quit",       new Color(180, 60, 60));

        playAgain.addActionListener(e -> { result = PLAY_AGAIN; dispose(); });
        quit.addActionListener(e ->      { result = QUIT;       dispose(); });

        buttons.add(playAgain);
        buttons.add(quit);

        panel.add(label,   BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    private JButton createButton(String text, Color accent) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? accent : new Color(25, 25, 55));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(accent);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
                g2.setColor(getModel().isRollover() ? Color.BLACK : Color.WHITE);
                g2.setFont(new Font("Georgia", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(120, 44));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static int show(Window parent, String message) {
        GameOverDialog dialog = new GameOverDialog(parent, message);
        dialog.setVisible(true);
        return dialog.result;
    }
}