public class Tuple {

    private int x;
    private int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Tuple comparator) {
        return comparator.x == this.x && comparator.y == this.y;
    }

    public String toString() {
        return x + " " + y;
    }

}
