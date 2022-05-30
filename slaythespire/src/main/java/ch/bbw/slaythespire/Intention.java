/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.slaythespire;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Intention {
    
    private String type;
    private int amount;
    
    public Intention(String type, int amount){
        this.type = type;
        this.amount = amount;
    }
    
    public Intention (Intention intention){
        this.type = intention.getType();
        this.amount = intention.getAmount();
    }
    
    public void doIntention(Player player, Enemy enemy){
        if (type.contains("Attack")){
            int strength = enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Strength", "BufF")).getAmount();
            int trueDamage = amount+strength;
            if (player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Intangible", "Buff")).getAmount() > 0){
                trueDamage = 1;
            }
            if (player.getBlock() > 0){
                if (player.getBlock() > trueDamage){
                    player.setBlock(player.getBlock()-trueDamage);
                }
                else{
                    int damage = trueDamage-player.getBlock();
                    player.setBlock(0);
                    System.out.println("Enemy attacked for " + damage);
                    player.setHp(player.getHp()-damage);
                    if (enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Stealing", "Buff")).getAmount() > 0){
                        stealMoney(player, enemy);
                    }
                    getLifeSteal(damage, enemy);
                    
                    
                }
            }
            else{
                System.out.println("Enemy attacked for " + trueDamage);
                player.setHp(player.getHp()-trueDamage);
                if (enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Stealing", "Buff")).getAmount() > 0){
                    stealMoney(player, enemy);
                }
                getLifeSteal(trueDamage, enemy);
            }
            if (enemy.getHp() < 0){
                enemy.setHp(0);
            }
            if (player.getHp() < 0){
                player.setHp(0);
            }
            System.out.println("You have " + player.getHp() + "/" + player.getMaxHp()+" HP");
        }
        
        if (type.contains("Defend")){
            int dexterity = 0;
            for (Buff b: player.getBuffs()){
                if (b.getName().equals("Dexterity")){
                    dexterity = b.getAmount();
                }
            }
            enemy.setBlock(enemy.getBlock()+(amount+dexterity));
            if (enemy.getName().equals("Emil")){
                if (amount < 14){
                    amount+=2;
                }
            }
        }
        if (type.contains("Buff Strength")){
            Buff strength = enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Strength", "Buff"));
            strength.setAmount(strength.getAmount()+amount);
        }
        if (type.contains("Debuff Frail")){
            Buff frail = player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Frail", "Buff"));
            frail.setAmount(frail.getAmount()+amount);
        }
    }

    public void stealMoney(Player player, Enemy enemy){
        Buff stealing = enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Stealing", "Buff"));
        if (player.getMoney() >= stealing.getAmount()){
            enemy.setStolenMoney(enemy.getStolenMoney()+stealing.getAmount());
            player.setMoney(player.getMoney()-stealing.getAmount());
            System.out.println("Enemy has stolen " + stealing.getAmount() + " money");
        }
        else {
            if (player.getMoney()!= 0){
                System.out.println("Enemy has stolen " + player.getMoney() + " money");
            }
            else if (player.getMoney() == 0){
                System.out.println("You didn't have any money to steal");
            }
            enemy.setStolenMoney(enemy.getStolenMoney()+player.getMoney());
            player.setMoney(0);
        }
    }
    
    public void getLifeSteal(int damage, Enemy enemy){
        Buff lifeSteal = enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Life Steal", "Buff"));
        if (lifeSteal.getAmount() == 1){
            enemy.setHp(enemy.getHp()+damage);
            System.out.println("Enemy healed " + damage + " HP");
            if (enemy.getHp() > enemy.getMaxHp()){
                enemy.setHp(enemy.getMaxHp());
            }
        }
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
