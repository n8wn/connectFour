import javax.swing.*;
import java.util.Random;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Show mode selection dialog
        int mode = ModeDialog.show(this);
        if (mode == ModeDialog.CANCELLED) {
            System.exit(0);
        }

        // Randomly assign colours to players
        boolean firstIsRed = new Random().nextBoolean();
        Cell colourOne = firstIsRed ? Cell.R : Cell.Y;
        Cell colourTwo = firstIsRed ? Cell.Y : Cell.R;

        // Build players based on chosen mode
        Player playerOne;
        Player playerTwo;

        switch (mode) {
            case ModeDialog.HUMAN_VS_HUMAN:
                playerOne = new HumanPlayer(colourOne);
                playerTwo = new HumanPlayer(colourTwo);
                break;
            case ModeDialog.HUMAN_VS_AI:
                playerOne = new HumanPlayer(colourOne);
                playerTwo = new AIPlayer(colourTwo);
                break;
            case ModeDialog.AI_VS_AI:
                playerOne = new AIPlayer(colourOne);
                playerTwo = new AIPlayer(colourTwo);
                break;
            default:
                playerOne = new HumanPlayer(colourOne);
                playerTwo = new HumanPlayer(colourTwo);
        }

        Board board = new Board();
        add(new BoardPanel(board, playerOne, playerTwo));
        pack();
        setLocationRelativeTo(null);
    }
}