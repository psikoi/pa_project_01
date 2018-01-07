package tads.graph.model;

public class Joint {

    private int x, y;

    public Joint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y:" + y;
    }

}
