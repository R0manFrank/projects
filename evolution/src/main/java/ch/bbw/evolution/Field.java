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
public class Field {
    
    private int length;
    private int height;
    private ArrayList<String> rows;
    private int minx;
    private int maxx;
    private int miny;
    private int maxy;
    
    public Field (int length, int height, int minx, int maxx, int miny, int maxy){
        this.length = length;
        this.height = height;
        rows = new ArrayList<>();
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
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

    public ArrayList<String> getRows() {
        return rows;
    }

    public void setRows(ArrayList<String> rows) {
        this.rows = rows;
    }
    
    public void insertRow(String row, int index){
        rows.set(index, row);
    }
    
    /**
     * Initializes the Field; creates the rows and puts it into an ArrayList
     */
    public void createField(){
        String baseRow = "";
        for (int i = -2; i < length; i++){
            baseRow = (baseRow+"# ");
        }
        rows.add(baseRow);
        for (int i = 0; i < height; i++){
            String gameRow = "";
            gameRow = (gameRow+"# ");
            for (int j = 0; j < length; j++){
                gameRow = (gameRow+"  ");
            }
            gameRow = (gameRow+"#");
            rows.add(gameRow);
            
        }
        rows.add(baseRow);
    }
    
    /**
     * Outputs the Field in the console
     */
    public void drawField(ArrayList<Cell> cellArray){
        /*
        for (String row : rows) {
            System.out.println(row);
        }*/
        for (int y = 0; y < rows.size(); y++){
            String row = rows.get(y);
            if (row.contains("o")){
                char[] charArray = row.toCharArray();
                for (int x = 0; x < charArray.length; x++){
                    char c = charArray[x];
                    if (c == 'o'){
                        boolean colored = false;
                        for (Cell cell : cellArray){
                            if (cell.getY() == y-1 && cell.getX() == (x*2+2)){
                                System.out.print(cell.getColor()+c);
                                colored = true;
                            }
                        }
                        if (colored == true){
                            
                        }
                        else{
                            System.out.print(c);
                        }
                    }
                    else{
                        System.out.print(c);
                    }
                }
                System.out.println("");
            }
            else{
                System.out.println(row);
            }
            
            
        }
    }
    
    public boolean checkField(int x, int y){
        String row = rows.get(y+1);
        if (row.charAt(x*2+2) == 'o'){
            return true;
        }
        else{
            return false;
        } 
    }
    
    public int countCells(){
        int cells = 0;
        for (int i = 1; i < rows.size()-1; i++){
            String row = rows.get(i);
            for (int x = 0; x < length; x++){
                if (row.charAt(x*2+2) == 'o'){
                    cells++;
                }
            }
        }
        return cells;
    }

    public int getMinx() {
        return minx;
    }

    public void setMinx(int minx) {
        this.minx = minx;
    }

    public int getMaxx() {
        return maxx;
    }

    public void setMaxx(int maxx) {
        this.maxx = maxx;
    }

    public int getMiny() {
        return miny;
    }

    public void setMiny(int miny) {
        this.miny = miny;
    }

    public int getMaxy() {
        return maxy;
    }

    public void setMaxy(int maxy) {
        this.maxy = maxy;
    }
    
    
            
    
        
        
    
}
