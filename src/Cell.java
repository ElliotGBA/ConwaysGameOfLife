public class Cell {

    private Tuple position;
    private int state; // 0 dead - 1 alive
    private int nextState;
    private boolean isUpdating = false;
    private char symbol;

    private final String RESET = "\u001B[0m";
    private final String BLACK = "\u001B[30m";
    private final String GREEN = "\u001B[32m";

    public Cell(Tuple position, int state) {
        this.position = position;
        this.state = state;
        this.symbol = state == 0 ? '0' : 'X';
    }

    public void update() {
        if (isUpdating) {
            setState(this.nextState);
            isUpdating = false;
        }
    }

    public void setNextState(int state) {
        this.isUpdating = true;
        this.nextState = state;
    }
    public void setState(int state) {
        this.state = state;
        this.symbol = state == 0 ? '0' : 'X';
    }
    public int getState() {
        return this.state;
    }
    public Tuple getPosition() {
        return this.position;
    }

    public String toString() {
        String returnString = (state == 0 ? BLACK : GREEN) + symbol + RESET;
        return returnString;
    }

}
