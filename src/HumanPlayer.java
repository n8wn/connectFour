public class HumanPlayer implements Player {

    private Cell colour;
    private int pendingMove = -1;

    public HumanPlayer(Cell colour) {
        this.colour = colour;
    }

    @Override
    public Cell getColour() {
        return colour;
    }

    @Override
    public int getMove(Board board) {
        int move = pendingMove;
        pendingMove = -1;
        return move;
    }

    public void setPendingMove(int col) {
        this.pendingMove = col;
    }

    public boolean hasPendingMove() {
        return pendingMove != -1;
    }
}