/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.slaythespire;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author roman
 */
public class Path {
    
    private int length;
    private ArrayList<Field> path;
    
    Random r = new Random();
    Scanner scanner = new Scanner(System.in);
    
    
    /**
     * Creates a new selection of Fields that will constantly change
     * 
     * @param length 
     * Defines after how many fields a path is done
     */
    public Path(int length){
        path = new ArrayList<>();
        this.length = length;
    }
    
    
    /**
     * Lets you choose between 1-3 Fields and whichever you choose will be played
     */
    public void nextF(int field){
        // Removes all pre-existing Fields in the array
        int pathSize = path.size();
        for (int i = 0; i < pathSize; i++){
            path.remove(0);
        }
        
        // Decides how many options the player should have (1: 50%, 2: 34%, 3: 16%)
        int choices = 3;
        if (r.nextInt(2) == 1){
            choices++;
            if (r.nextInt(3) == 2){
                choices++;
            }
        }
        // Decides for each option, which type of room it will be
        if (!(field == 5)){
            for (int i = 0; i < choices; i++){
                int type = r.nextInt(4);
                if (type == 0){
                    path.add(new Field("Secret"));
                }
                if (type == 1){
                    path.add(new Field("Enemy"));
                }
                if (type == 2){
                    path.add(new Field("Elite"));
                }
                if (type == 3){
                    path.add(new Field("Shop"));
                }
            }
        }
        else{
            path.add(new Field("Boss"));
        }
        
        
        // Prints out all options
        System.out.println("Choose your next Path");
        for (int i = 0; i < path.size(); i++){
            Field f = path.get(i);
            System.out.print((i+1) + " - ");
            System.out.println(f.getType());
        }
        
        
        int select = 0;
        boolean selectValid = false;
        // Repeatedly checks if the inputted value is correct until it is
        while (selectValid == false){
            if (select > 0 && select <= path.size()){
                selectValid = true;
            }
            if (selectValid == false){
                // Takes the next input and tries to make an int out of it
                String input = scanner.nextLine();
                try{
                    select = Integer.parseInt(input);
                }
                catch (Exception x){ 

                }
                // if the Number is not between 1 and the number of options, tells the player lets him choose again
                if (!(select > 0 && select <= path.size())){
                    System.out.println("Invalid Choice");
                }
            }
        }
        path.get(select-1).doField();
        
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Field> getPath() {
        return path;
    }

    public void setPath(ArrayList<Field> path) {
        this.path = path;
    }
    
    
}
