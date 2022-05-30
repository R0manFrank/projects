/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.evolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author roman
 */
public class Simulation {
    
    
    public static void doStep(ArrayList<Cell> cellArray, Field board){
        for (Cell c: cellArray){
                c.doStep(board);
            }
        board.drawField(cellArray);  
    }
    
    public static void doGen(Field board, ArrayList<Cell> cellArray, int cells, int genomes){
        Iterator<Cell> it = cellArray.iterator();
        while (it.hasNext()) {
            Cell c = it.next();
            if (c.getX() < board.getMinx()){
                c.removeCell(board);
                it.remove();
            }
            else if (c.getX() > board.getMaxx()){
                c.removeCell(board);
                it.remove();
            }
            else if (c.getY() < board.getMiny()){
                c.removeCell(board);
                it.remove();
            }
            else if (c.getY() > board.getMaxy()){
                c.removeCell(board);
                it.remove();
            }
        }
        int currentCells = board.countCells();
        if (currentCells == 0){
            Cell cell = new Cell(0,0,genomes);
            cell.drawCell(board);
            cellArray.add(cell);
            currentCells++;
        }
        System.out.println("Survivors: " + board.countCells());
        Random r = new Random();
        for (int i = 0; i < cells; i++){
            boolean set = false;
            while (set == false){
                int x = r.nextInt(board.getLength());
                int y = r.nextInt(board.getHeight());
                if (board.checkField(x, y) == false){
                    Cell parent = cellArray.get(r.nextInt(currentCells));
                    Cell cell = new Cell(x,y,parent);
                    cell.drawCell(board);
                    cellArray.add(cell);
                    set = true;
                }
            }  
        }
        for (int i = 0; i < currentCells; i++){
            cellArray.get(i).removeCell(board);
            cellArray.remove(i);
        }
    }
    
    public static void main(String[] args) {
        
        int cells = 20;
        int gens = 5;
        int steps = 15;
        int genomes = 2;
        ArrayList cellArray = new ArrayList<Cell>();
        
        Field board = new Field (15, 15, 0, 1, 0, 15);
        board.createField();
        Random r = new Random();
        for (int i = 0; i < cells; i++){
            boolean set = false;
            while (set == false){
                int x = r.nextInt(board.getLength());
                int y = r.nextInt(board.getHeight());
                if (board.checkField(x, y) == false){
                    Cell cell = new Cell(x,y, genomes);
                    cell.drawCell(board);
                    cellArray.add(cell);
                    set = true;
                }
            }
            
        }
        
        for (int i = 1; i <= gens; i++){
            System.out.println("Generation " + i);
            board.drawField(cellArray);
            for (int j = 1; j <= steps; j++){
                System.out.println("Step " + j);
                doStep(cellArray, board);
            }
            doGen(board, cellArray, cells, genomes);
            
        }
    }
}
