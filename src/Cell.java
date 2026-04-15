public enum Cell {
    R, Y, EMPTY;

    public boolean isFull() {
        return this != EMPTY;
    }
}