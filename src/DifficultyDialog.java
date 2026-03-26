import javax.swing.*;
import java.awt.*;

public class DifficultyDialog extends JDialog {
    public static final int EASY = 1;
    public static final int MEDIUM    = 2;
    public static final int HARD       = 3;
    public static final int CANCELLED      = -1;

    private int choice = CANCELLED;

    public DifficultyDialog(Window parent) {
        super(parent, "Select Difficulty", Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(new Color(10, 10, 28));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Title
        JLabel title = new JLabel("CONNECT FOUR", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        // Subtitle
        JLabel subtitle = new JLabel("Choose a difficulty.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Georgia", Font.ITALIC, 14));
        subtitle.setForeground(new Color(160, 160, 200));

        // Buttons panel
        JPanel buttons = new JPanel(new GridLayout(3, 1, 0, 12));
        buttons.setOpaque(false);

        JButton easy = createDifficultyButton("Easy", new Color(220, 60, 60));
        JButton medium = createDifficultyButton("Medium",    new Color(240, 200, 40));
        JButton hard = createDifficultyButton("Hard",       new Color(80, 160, 255));

        easy.addActionListener(e -> { choice = EASY; dispose(); });
        medium.addActionListener(e -> { choice = MEDIUM; dispose(); });
        hard.addActionListener(e -> { choice = HARD; dispose(); });

        buttons.add(easy);
        buttons.add(medium);
        buttons.add(hard);

        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);
        center.add(subtitle, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    private JButton createDifficultyButton(String text, Color accent) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(accent);
                } else {
                    g2.setColor(new Color(25, 25, 55));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(accent);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
                g2.setColor(getModel().isRollover() ? Color.BLACK : Color.WHITE);
                g2.setFont(new Font("Georgia", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(260, 50));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static int show(Window parent) {
        DifficultyDialog dialog = new DifficultyDialog(parent);
        dialog.setVisible(true);
        return dialog.choice;
    }
}
