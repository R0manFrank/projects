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
public class Buff {
    
    private boolean positive;
    private String name;
    private int amount;
    
    public Buff(String name, boolean positive){
        this.amount = 0;
        this.name = name;
        this.positive = positive;
    }
    
    public Buff (Buff buff){
        this.amount = buff.getAmount();
        this.name = buff.getName();
        this.positive = buff.isPositive();
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
