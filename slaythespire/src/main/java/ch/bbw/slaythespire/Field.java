/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.slaythespire;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author roman
 */
public class Field {
    
    Random r = new Random();
    
    private String type;
    private Encounter encounter;
    private Shop shop;
    private Secret secret;
    
    
    /**
     * Creates a field which will be a specific room
     * 
     * @param type 
     * Room, which can be either "Elite", "Enemy", "Secret", "Shop" (case sensitive) or "Boss"
     */
    public Field(String type){
        this.type = type;
    }
    
    
    /**
     * Check what variable type is and initializes the according room
     */
    public void doField(){
        if (this.type.equals("Enemy")){
            encounter = new Encounter("Encounter");
            encounter.doEncounter();
        }
        if (this.type.equals("Shop")){
            shop = new Shop();
            shop.doShop();
        }
        if (this.type.equals("Secret")){
            int secretSelect = r.nextInt(Spire.secrets.size());
            secret = new Secret(Spire.secrets.get(secretSelect));
            secret.doSecret();
        }
        if (this.type.equals("Boss")){
            encounter = new Encounter("Boss");
            encounter.doEncounter();
        }
        if (this.type.equals("Elite")){
            encounter = new Encounter("Elite");
            encounter.doEncounter();
        }
        
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
