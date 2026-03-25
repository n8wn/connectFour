public class AIPlayer implements Player {

    private final Cell aiColour;
    private final Cell humanColour;

    public AIPlayer(Cell aiColour) {
        this.aiColour = aiColour;
        this.humanColour = aiColour.equals(Cell.R) ? Cell.Y : Cell.R;
    }

    @Override
    public int getMove(Board grid) {



        return 0;
    }

    public static int minimax() {

    }



    // maybe see how i can program in "levels" perhaps if i modify the weights i can make it shittier,
    // or implement a "random" element to some turns.
}
