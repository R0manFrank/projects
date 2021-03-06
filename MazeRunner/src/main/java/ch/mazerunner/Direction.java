package ch.mazerunner;

/**
 *
 * @author roman
 */
public class Direction {
    
    public static Direction left = new Direction(-1, 0);
    public static Direction up = new Direction(0, -1);
    public static Direction down = new Direction(0, 1);
    public static Direction right = new Direction(1, 0);
    
    private int x;
    private int y;

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
