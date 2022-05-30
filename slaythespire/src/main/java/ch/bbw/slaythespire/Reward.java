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
public class Reward {
    
    Random r = new Random();
    
    private int money;
    private ArrayList<Drink> drinks;
    private ArrayList<Card> cards;
    private ArrayList<Rock> rocks;
    private String type;
    
    
    /**
     * Creates a new reward for after a battle, money will be between 7 and 20
     * 
     * @param type 
     * Can be either "Elite" or "Encounter" or "Boss".
     */
    public Reward(String type){
        this.money = r.nextInt(14)+7;
        this.type = type;
        this.drinks = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.rocks = new ArrayList<>();
        ArrayList<Object> playerObjRocksArray = new ArrayList<>(Spire.player.getRocks());
        if (type.equals("Elite")){
            boolean done = false;
            while (done == false){
                Rock rock = new Rock(Spire.rocks.get(r.nextInt(Spire.rocks.size())));
                if (!(Spire.isNameInArray(playerObjRocksArray, rock.getName(), "Rock"))){
                    rocks.add(rock);
                    done = true;
                }
                if (Spire.player.getRocks().size() == Spire.rocks.size()){
                    done = true;
                }
            }
        }
        ArrayList<Card> tempCards = new ArrayList<>(Spire.cards);
        if (type.equals("Boss")){
            for (int i = 0; i < 3; i++){
                boolean done = false;
                while (done == false){
                    Rock rock = new Rock(Spire.rocks.get(r.nextInt(Spire.rocks.size())));
                    if (!(Spire.isNameInArray(playerObjRocksArray, rock.getName(), "Rock"))){
                        rocks.add(rock);
                        done = true;
                    }
                    if (Spire.player.getRocks().size() == Spire.rocks.size()){
                        done = true;
                    }
                }
            }
        }
        for (int i = 0; i < Spire.player.getCardSelection(); i++){
            boolean valid = false;
            int rar = r.nextInt(100)+1;
            String rarity = "";
            if (rar <= 70){
                rarity = "common";
            }
            if (rar > 70 && rar <= 95){
                rarity = "uncommon";
            }
            if (rar > 95){
                rarity = "rare";
            }
            if (type.equals("Boss")){
                rarity = "rare";
            }
            while (valid == false){
                int pos = r.nextInt(tempCards.size());
                Card c = tempCards.get(pos);
                if (c.getPool().contains("reward") && c.getRarity().contains(rarity)){
                    cards.add(c);
                    tempCards.remove(c);
                    valid = true;
                }
            }
        }
        int drinkChance = r.nextInt(2)+1;
        if (drinkChance == 1){
            boolean valid = false;
            while (valid == false){
                Drink d = Spire.drinks.get(r.nextInt(Spire.drinks.size()));
                if (d.getPool().contains("reward")){
                    drinks.add(new Drink(d));
                    valid = true;
                }
            }
        }
    }
    
    
    /**
     * Prints the amount of money in the console.
     */
    public void printMoney(){
        System.out.println(money);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drink) {
        this.drinks = drink;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> card) {
        this.cards = card;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public void setRock(ArrayList<Rock> rocks) {
        this.rocks = rocks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
    
}
