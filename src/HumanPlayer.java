import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int getMove(Board board) {
        System.out.println("Choose the column that you want to drop a token in (1-7):  ");
        int input = Integer.parseInt(scanner.nextLine().trim());
        if (input <= 7 && input >= 1) {
            return input-1;
        }

        System.out.println("Invalid input, try again.");
        return -1;
    }
}