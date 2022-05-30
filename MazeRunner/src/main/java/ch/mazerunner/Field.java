package ch.mazerunner;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Field {
    
    private int xPos;
    private int yPos;
    private String mark;

    public Field(int xPos, int yPos, String mark) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.mark = mark;
    }
    
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
    
    
}
