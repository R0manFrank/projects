/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.evolution;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Cell {
    
    private int x;
    private int y;
    private Genome genome;
    private String color;
    
    public Cell (int x, int y, int genomes){
        this.x = x;
        this.y = y;
        genome = new Genome(genomes);
        ArrayList list = new ArrayList<>(genome.getGen());
        for (int i = 0; i < list.size(); i++){
            String s = list.get(i).toString();
        }
        
        
    }
    public Cell (int x, int y, Cell cell){
        this.x = x;
        this.y = y;
        genome = cell.getGenome();
        ArrayList list = new ArrayList<>(genome.getGen());
        for (int i = 0; i < list.size(); i++){
            String s = list.get(i).toString();
        }
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

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
    
    public void doStep(Field board){
        ArrayList list = new ArrayList<>(genome.getGen());
        for (int i = 0; i < list.size(); i++){
            String s = list.get(i).toString();
            if (i == 0){
                String dir = "";
                if (s.charAt(0) == '1' && s.charAt(1) == '0'){
                    dir = dir+"north";
                }
                if (s.charAt(1) == '1' && s.charAt(0) == '0'){
                    dir = dir+"south";
                }
                if (s.charAt(2) == '1' && s.charAt(3) == '0'){
                    dir = dir+"east";
                }
                if (s.charAt(3) == '1' && s.charAt(2) == '0'){
                    dir = dir+"west";
                }
                if (dir != ""){
                    removeCell(board);
                    if (dir.contains("north")){
                        if (y > 0 && board.checkField(x, y-1) == false){
                            y = y-1;
                        }
                    }
                    if (dir.contains("south")){
                        if (y < (board.getHeight()-1) && board.checkField(x, y+1) == false){
                            y = y+1;
                        }
                    }
                    if (dir.contains("east")){
                        if (x < (board.getLength()-1) && board.checkField(x+1, y) == false){
                            x = x+1;
                        }
                    }
                    if (dir.contains("west") && board.checkField(x-1, y) == false){
                        if (x > 0){
                            x = x-1;
                        }
                    }
                    drawCell(board);
                }
            }
        }
    }
   
    public void drawCell(Field board){
        ArrayList list = new ArrayList<>(board.getRows());
        String row = list.get(y+1).toString();
        char[] charArray = row.toCharArray();
        char ch = 'o';
        charArray[x*2+2] = ch;
        String newRow = new String(charArray);
        board.insertRow(newRow, y+1);
    }
    
    public void removeCell(Field board){
        ArrayList list = new ArrayList<>(board.getRows());
        String row = list.get(y+1).toString();
        char[] charArray = row.toCharArray();
        char ch = ' ';
        charArray[x*2+2] = ch;
        String newRow = new String(charArray);
        board.insertRow(newRow, y+1);
    }
}
