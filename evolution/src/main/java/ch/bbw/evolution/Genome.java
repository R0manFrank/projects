/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.evolution;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author roman
 */
public class Genome {
    
    private ArrayList<String> gen;
    
    
    public Genome(int genomes){
        gen = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < genomes; i++){
            String row = "";
            for (int j = 0; j < 8; j++){
                int bin = r.nextInt(2);
                row = (row + bin);   
            }
            gen.add(row);
        }
        
    }

    public ArrayList<String> getGen() {
        return gen;
    }

    public void setGen(ArrayList<String> gen) {
        this.gen = gen;
    }
    
    
    
    
}
