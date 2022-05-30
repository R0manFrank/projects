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
public class Shop {
    
    Random r = new Random();
    
    private Rock[] sellRocks;
    private Card[] sellCards;
    private Drink[] sellDrinks;
    
    public Shop(){
        sellCards = new Card[7];
        for (int i = 0; i < sellCards.length; i++){
            sellCards[i] = new Card(Spire.cards.get(r.nextInt(Spire.cards.size())));
        }
        sellRocks = new Rock[3];
        ArrayList<Object> playerObjRocksArray = new ArrayList<>(Spire.player.getRocks());
        for (int i = 0; i < sellRocks.length; i++){
            boolean done = false;
            while (done == false){
                Rock rock = new Rock(Spire.rocks.get(r.nextInt(Spire.rocks.size())));
                if (!(Spire.isNameInArray(playerObjRocksArray, rock.getName(), "Rock"))){
                    sellRocks[i] = rock;
                    done = true;
                }
                if (Spire.player.getRocks().size() == Spire.rocks.size()){
                    done = true;
                }
            }
        }
        sellDrinks = new Drink[3];
        for (int i = 0; i < sellDrinks.length; i++){
            sellDrinks[i] = new Drink(Spire.drinks.get(r.nextInt(Spire.drinks.size())));
        }
    }

    public void doShop(){
        System.out.println("Welcome to the shop!");
        System.out.println("");
        System.out.println("Cards:");
        for (Card sellCard : sellCards) {
            System.out.println(sellCard.getName());
        }
        System.out.println("");
        System.out.println("Rocks:");
        for (Rock sellRock : sellRocks) {
            if (sellRock != null){
                System.out.println(sellRock.getName());
            }
        }
        System.out.println("");
        System.out.println("Drinks:");
        for (Drink sellDrink : sellDrinks) {
            System.out.println(sellDrink.getName());
        }
    }

    public Rock[] getSellRocks() {
        return sellRocks;
    }

    public void setSellRocks(Rock[] sellRocks) {
        this.sellRocks = sellRocks;
    }

    public Card[] getSellCards() {
        return sellCards;
    }

    public void setSellCards(Card[] sellCards) {
        this.sellCards = sellCards;
    }

    public Drink[] getSellDrinks() {
        return sellDrinks;
    }

    public void setSellDrinks(Drink[] sellDrinks) {
        this.sellDrinks = sellDrinks;
    }
    
    
    
    
}
