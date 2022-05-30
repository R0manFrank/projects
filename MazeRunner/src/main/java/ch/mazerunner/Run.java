package ch.mazerunner;

/**
 *
 * @author roman
 */
public class Run {
    
    public static void main(String[] args) {
        
        Maze maze = new Maze(20, 20, 0, 0, 1);
        
        maze.generateMaze();
        
        maze.printField();
    }
}
