package tads.graph.model;

/**
 * The Data Class bad smell in this class is unavoidable as it's just
 * a generic type to be used to display coordinates on screen.
 * 
 * @author Ruben
 */
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

    public Joint copy() {
        return new Joint(x,y);
    }

}
