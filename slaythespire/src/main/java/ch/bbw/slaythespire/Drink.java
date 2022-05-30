/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.slaythespire;

/**
 *
 * @author roman
 */
public class Drink {
    
    private String name;
    private String pool;
    
    public Drink(String name, String pool){
        this.name = name;
        this.pool = pool;
    }
    
    public Drink(Drink drink){
        this.name = drink.getName();
    }
    
    public void useDrink(){
        if (name.contains("Zigüner Drink")){
            Buff stealing = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Stealing", "Buff"));
            stealing.setAmount(stealing.getAmount()+5);
        }
        if (name.contains("Smoke Drink")){
            
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
    
    
}
