package ch.mazerunner;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Path extends Field{
    
    public Direction direction;
    
    public Path(int xPos, int yPos, Direction direction){
        super(xPos, yPos, "  ");
        this.direction = direction;
    }
    
    public void createPath(Field[][] fields, ArrayList<Path> expansions, Direction dir){
        int newX = super.getxPos() + dir.getX();
        int newY = super.getyPos() + dir.getY();
        fields[newX][newY] = new Path(newX, newY, dir);
        expansions.add((Path) fields[newX][newY]);
    }
    
    public void goForwards(Field[][] fields, ArrayList<Path> paths){
        createPath(fields, paths, direction);
    }
    
    public void goLeft(Field[][] fields, ArrayList<Path> paths){
        Direction newDirection = Direction.up;
        if(direction == Direction.left){
            newDirection = Direction.down;
        }
        else if(direction == Direction.down){
            newDirection = Direction.right;
        }
        else if(direction == Direction.right){
            newDirection = Direction.up;
        }
        else if(direction == Direction.up){
            newDirection = Direction.left;
        }
        createPath(fields, paths, newDirection);
    }
    
    public void goRight(Field[][] fields, ArrayList<Path> paths){
        Direction newDirection = Direction.up;
        if(direction == Direction.left){
            newDirection = Direction.up;
        }
        else if(direction == Direction.down){
            newDirection = Direction.left;
        }
        else if(direction == Direction.right){
            newDirection = Direction.down;
        }
        else if(direction == Direction.up){
            newDirection = Direction.right;
        }
        createPath(fields, paths, newDirection);
        
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    
    
}
