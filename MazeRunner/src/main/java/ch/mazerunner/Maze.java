package ch.mazerunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 *
 * @author roman
 */
public class Maze {
    
    Random r = new Random();
    
    /**
     * Defines the length of the maze (Will reduce by 2 because of walls)
     */
    private int length;
    
    /**
     * Defines the height of the maze (Will reduce by 2 because of walls)
     */
    private int height;
    
    /**
     * Position where the Maze opens
     */
    private int xStart;
    private int yStart;
    
    /**
     * Difficulty modifier
     */
    private int floor;
    
    /**
     * The 2D Array for the maze using lenght and height
     */
    private Field[][] maze; 

    /**
     * Initializes the Maze
     * 
     * @param length
     * @param height
     * @param xStart
     * @param yStart
     * @param floor 
     */
    public Maze(int length, int height, int xStart, int yStart, int floor) {
        this.length = length;
        this.height = height;
        this.xStart = xStart;
        this.yStart = yStart;
        this.floor = floor;
        maze = new Field[length][height];
    }
    
    /**
     * Function to generate the maze structure
     */
    public void generateMaze(){
        // Randomizes the starting position
        int xPosStart = (r.nextInt(3));
        int yPosStart = 0;
        
        Direction direction = Direction.down;
        
        // If x is zero, it must be on the left facing right
        if (xPosStart == 0){
            yPosStart = height/2;
            direction = Direction.right;
        }
        
        // If x is 1, make it to half of length
        else if (xPosStart == 1){
            xPosStart = length/2;
            
            // Randomizes the y position because it can be on the top or bottom
            yPosStart = r.nextInt(2);
            
            // If y is 1, make it to height-1 so that it is at the bottom and facing up
            if (yPosStart == 1){
                yPosStart = height-1;
                direction = Direction.up;
            }
            
            // If y is 0, it must be at the top facing down
            else{
                direction = Direction.down;
            }
        }
        
        // If x is 2, make it all the way to the right and facing left
        else if (xPosStart == 2){
            xPosStart = length-1;
            yPosStart = height/2;
            direction = Direction.left;
        }
        maze[xPosStart][yPosStart] = new Path(xPosStart, yPosStart, direction); // Creates the first path with the starting positions
        
        // Surrounds the maze with walls everywhere aside from where the start is
        for (int i = 0; i < length; i++){
            for (int j = 0; j < height; j++){
                if (i == xPosStart && j == yPosStart){
                    continue;
                }
                if (i == 0 || i == length-1 || j == 0 || j == height-1){
                    maze[i][j] = new Wall(i, j);
                }
            }
        }
        ArrayList<Path> expansions = new ArrayList<>();                         // Create an ArrayList for every Path that can still be expanded upon
        expansions.add(new Path(xPosStart, yPosStart, direction));              // Adds the starting position to the previously created ArrayList

        // Generates all the paths necessary for the maze
        while(true){
            boolean action = false;                                             // Variable for stopping the while(true){} when needed
            Collections.shuffle(expansions);                                    // Shuffles the expansions ArrayList so that it isnt biased towards certain fields
            for (int i = 0; i < expansions.size(); i++){                        // Iterates over all the Paths in the expansions ArrayList
                
                // Stores the Path and its variables in seperate variables for more convenient use
                Path p = expansions.get(i);
                Direction pDirection = p.getDirection();
                int xPos = p.getxPos();
                int yPos = p.getyPos();
                
                // Checks for every direction to see if it is possible to go that was using functions possible[direction]()
                boolean forwards = possibleForwards(xPos, yPos, pDirection);
                boolean left = possibleLeft(xPos, yPos, pDirection);
                boolean right = possibleRight(xPos, yPos, pDirection);
                
                boolean[] possibilities = new boolean[3];                       // Array for randomizing the direction it will go in
                boolean possible = false;                                       // Variable to check if a direction is available
                possibilities[0] = forwards;
                possibilities[1] = left;
                possibilities[2] = right;
                if (forwards == true){
                    possible = true;
                }
                if (left == true){
                    possible = true;
                }
                if (right == true){
                    possible = true;
                }
                
                // If at least one direction is free, randomize where it will go
                if (possible == true){
                    while(true){
                        int rndm = r.nextInt(3);                                // Creates a random number
                        if (possibilities[rndm] == true){                       // Goes in that direction if the boolean on rndm position is true
                            if (rndm == 0){
                                ((Path) maze[xPos][yPos]).goForwards(maze, expansions);
                            }
                            if (rndm == 1){
                                ((Path) maze[xPos][yPos]).goLeft(maze, expansions);
                            }
                            if (rndm == 2){
                                ((Path) maze[xPos][yPos]).goRight(maze, expansions);
                            }
                            action = true;                                      // Sets action to true so that it doesnt stop the loop
                            possibilities[rndm] = false;
                            boolean remainingPossible = false;
                            
                            // Check if there is still a direction available. If not, remove the Path from expansions
                            if (possibilities[0] == true){
                                remainingPossible = true;
                            }
                            if (possibilities[1] == true){
                                remainingPossible = true;
                            }
                            if (possibilities[2] == true){
                                remainingPossible = true;
                            }
                            if (remainingPossible == false){
                                expansions.remove(p);
                            }
                            break;                                              // Stops the while(true){} loop that only stops when having done an action
                        }
                    }
                }
                
                // If there is no possible direction to go anymore, remove the Path from expansions
                else{
                    expansions.remove(p);
                }
            }
            
            // If no action has been done, the maze is completed and the while(true){} loop can be stopped
            if (action == false){
                break;
            }
        }
        
        // Sets walls for every field that isnt anything yet
        for (int i = 0; i < length; i++){
            for (int j = 0; j < height; j++){
                if (maze[i][j] == null){
                    maze[i][j] = new Wall(i, j);
                }
            }
        }
        
    }
    
    /**
     * Checks if the field in a direction from a position is empty (available)
     * 
     * @param xPos
     * @param yPos
     * @param dir
     * @return 
     */
    public boolean isFieldAvailable(int xPos, int yPos, Direction dir){
        boolean empty = false;
        
        // Gets the position of the new Field
        int newX = xPos + dir.getX();
        int newY = yPos + dir.getY();
        
        // Checks if the new Field is null (empty)
        try{
            if (maze[newX][newY] == null){
                empty = true;
            }
        }
        
        // If the Field is out of bounds, it cant be a new Path
        catch(Exception e){
            empty = false;
        }
        boolean possible = false;
        
        // Checks if the Field would be continuing an existing Path
        if (empty){
            possible = true;
            // Checks all fields around the current one
            for(int i = -1; i <= 1; i++){
                for (int j = -1; j <= 1; j++) {
                    int newnewX = newX + i;
                    int newnewY = newY + j;
                    if (dir.getX() != 0){                                       // If the direction is left or right
                        if (newnewX == xPos){                                   // And if the Field that is being checked is on the same x-axis as the original new field
                            continue;
                        }
                    }
                    else if (dir.getY() != 0){
                        if (newnewY == yPos){
                            continue;
                        }
                    }
                    try{
                        if (maze[newnewX][newnewY].getClass().getName().equals("ch.mazerunner.Path")) {
                            possible = false;
                        }
                    }
                    catch(Exception e){}
                }
            }
                
            
        }
        return possible;
    }
    
    public boolean possibleForwards(int xPos, int yPos, Direction dir){
        boolean possible = isFieldAvailable(xPos, yPos, dir);
        return possible;
    }
    
    public boolean possibleRight(int xPos, int yPos, Direction dir){
        Direction newDirection = Direction.up;
        if(dir == Direction.left){
            newDirection = Direction.up;
        }
        else if(dir == Direction.down){
            newDirection = Direction.left;
        }
        else if(dir == Direction.right){
            newDirection = Direction.down;
        }
        else if(dir == Direction.up){
            newDirection = Direction.right;
        }
        boolean possible = isFieldAvailable(xPos, yPos, newDirection);
        return possible;
    }
    
    public boolean possibleLeft(int xPos, int yPos, Direction dir){
        Direction newDirection = Direction.up;
        if(dir == Direction.left){
            newDirection = Direction.down;
        }
        else if(dir == Direction.down){
            newDirection = Direction.right;
        }
        else if(dir == Direction.right){
            newDirection = Direction.up;
        }
        else if(dir == Direction.up){
            newDirection = Direction.left;
        }
        boolean possible = isFieldAvailable(xPos, yPos, newDirection);
        return possible;
    }
    
    public int countPaths(){
        int paths = 0;
        for (Field[] field : maze){
            for (Field f : field){
                if (f.getClass().getName().equals("Path")){
                    paths++;
                }
            }
        }
        return paths;
    }
    
    public void printField(){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < height; j++){
                if (maze[i][j] == null){
                    System.out.print("e ");
                }
                else{
                    System.out.print(maze[i][j].getMark());
                }
                
            }
            System.out.println();
        }
    }

    public Field[][] getMaze() {
        return maze;
    }

    public void setMaze(Field[][] fields) {
        this.maze = fields;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
    
    
    
}
