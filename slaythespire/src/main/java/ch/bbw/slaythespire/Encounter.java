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
public class Encounter {

    public static Scanner scanner = new Scanner(System.in);
    public static Random r = new Random();
    public static ArrayList<Card> hand = new ArrayList<>();
    public static ArrayList<Card> draw = new ArrayList<>(Spire.player.getDeck());
    public static ArrayList<Card> discard = new ArrayList<>();
    public static ArrayList<Card> graveyard = new ArrayList<>();

    private Reward reward;
    private ArrayList<Enemy> enemies;
    private int nextId;
    private String type;

    /**
     * Creates a new encounter with a random Enemy and a random Reward
     * 
     * @param type
     */
    public Encounter(String type) {
        this.type = type;
        reward = new Reward(type);
        enemies = new ArrayList<>();
        nextId = 0;
        Spire.objRocksArray = new ArrayList<>(Spire.player.getRocks());
    }

    /**
     * Puts the player into an Encounter with an Enemy
     *
     */
    public void doEncounter() {
        while (!(hand.isEmpty())) {
            hand.remove(0);
        }
        while (!(draw.isEmpty())) {
            draw.remove(0);
        }
        while (!(discard.isEmpty())) {
            discard.remove(0);
        }
        while (!(graveyard.isEmpty())) {
            graveyard.remove(0);
        }
        draw = new ArrayList<>(Spire.player.getDeck());
        System.out.println("A battle has started");
        // Gets a random Enemy
        int enemiesAmount = 1;
        for (int i = 0; i < enemiesAmount; i++) {
            boolean done = false;
            while (done == false){
                Enemy enemy = new Enemy(Spire.enemies.get(r.nextInt(Spire.enemies.size())));
                if (enemy.getEnemyType().equals(type)){
                    enemies.add(enemy);
                    enemies.get(i).setId(nextId);
                    nextId++;
                    done = true;
                }
                
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println("Enemy: " + enemies.get(i).getName());
        }
        boolean endBattle = false;
        int turn = 0;
        while (Spire.player.getHp() > 0 && isAnEnemyAlive(enemies) && endBattle == false) {
            System.out.println("");
            System.out.println("");
            turn++;
            System.out.println("Turn " + turn);
            // Draws cards equal to the amount of draw the player has
            for (int i = 0; i < Spire.player.getDraw(); i++) {
                boolean handFull = drawCard();
                if (handFull || draw.isEmpty() && discard.isEmpty()) {
                    break;
                }
            }
            // Gives every enemy an Intention, which they will do after the player finishes his turn
            for (int i = 0; i < enemies.size(); i++) {
                for (int j = 0; j < 2; j++) {
                    addIntention(enemies.get(i));
                }

            }
            // Makes it so that the Zigüner Enemy cannot run away before turn 4
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).getName().equals("Zigüner")) {
                    for (int j = 0; j < 2; j++) {
                        if (enemies.get(i).getCurrentIntentions().get(j).getType().equals("Run away")) {
                            if (turn + j > 3) {
                            } else {
                                enemies.get(i).getCurrentIntentions().set(j, enemies.get(i).getIntentions().get(r.nextInt(enemies.get(i).getIntentions().size() - 1)));
                            }
                        }
                    }

                }
            }
            boolean endTurn = false;
            Spire.player.setMana(Spire.player.getMaxMana());
            Buff shares = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Shares", "Buff"));
            if (shares.getAmount() > 0) {
                Spire.player.setMana(Spire.player.getMana() + shares.getAmount());
            }
            // Starts the turn and asks what the player wants to use
            while (endTurn == false && isAnEnemyAlive(enemies)) {
                System.out.println("");
                System.out.println("You currently have " + Spire.player.getHp() + " / " + Spire.player.getMaxHp() + " HP and " + Spire.player.getBlock() + " block");
                // Prints out details for every enemy that is alive
                for (int i = 0; i < enemies.size(); i++) {
                    if (enemies.get(i).getHp() > 0 && enemies.get(i).isRunThisTurn() == false) {
                        ArrayList<Intention> currentIntentions = enemies.get(i).getCurrentIntentions();
                        int intentionAmount = currentIntentions.get(0).getAmount();
                        int intentionAmounttwo = currentIntentions.get(1).getAmount();
                        Buff strength = enemies.get(i).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Strength", "Buff"));
                        if (currentIntentions.get(0).getType().equals("Attack")) {
                            intentionAmount = intentionAmount + strength.getAmount();
                        }
                        if (currentIntentions.get(1).getType().equals("Attack")) {
                            intentionAmounttwo = intentionAmounttwo + strength.getAmount();
                        }
                        System.out.println("Enemy's intention: " + enemies.get(i).getCurrentIntentions().get(0).getType() + " " + intentionAmount);
                        if (Spire.isNameInArray(Spire.objRocksArray, "Stein des Auges", "Rock")) {
                            System.out.println("Enemy's intention next turn: " + enemies.get(i).getCurrentIntentions().get(1).getType() + " " + intentionAmounttwo);
                        }
                        System.out.println("Enemy " + enemies.get(i).getName() + " has " + enemies.get(i).getHp() + " HP left");
                        System.out.println("Enemy " + enemies.get(i).getName() + " has " + enemies.get(i).getBlock() + " Block");
                    }
                }
                System.out.println("Mana: " + Spire.player.getMana());
                for (Buff b : Spire.player.getBuffs()) {
                    if (b.getAmount() > 0) {
                        System.out.println("You have " + b.getAmount() + " " + b.getName());
                    }
                }
                System.out.println("Your options:");
                // Prints the Cards and how much Mana they cost
                for (int i = 0; i < hand.size(); i++) {
                    Card c = hand.get(i);
                    String description = c.getDescription();
                    if (c.getDefense() > 0){
                        description = ("Gives " + c.getDefense() + " block. " + description);
                    }
                    if (c.getDamage() > 0){
                        description = ("Deals " + c.getDamage() + " damage. " + description);
                    }
                    System.out.print((i + 1) + " - ");
                    System.out.println(c.getName() + " (" + c.getCost() + " Mana) " + description);
                }
                System.out.println((hand.size() + 1) + " - Use a Drink");
                System.out.println((hand.size() + 2) + " - End Turn");
                int select = 0;
                boolean selectValid = false;
                // Repeatedly checks if the inputted value is correct until it is
                while (selectValid == false) {
                    if (select > 0 && select <= hand.size()) {
                        if (Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Entangled", "Buff")).getAmount() > 0 && (hand.get(select - 1).getType().contains("Attack") || hand.get(select - 1).getDamage() > 0)) {
                            System.out.println("Cannot attack this turn, you are entangled");
                        } else {
                            selectValid = true;
                        }
                    }
                    if (select > hand.size() && select <= hand.size() + 2) {
                        selectValid = true;
                    }
                    if (selectValid == false) {
                        // Takes the next input and tries to make an int out of it
                        String input = scanner.nextLine();
                        try {
                            select = Integer.parseInt(input);
                        } catch (Exception x) {

                        }
                        // if the Number is not between 1 and the number of options, tells the player lets him choose again
                        if (!(select > 0 && select <= hand.size() + 2)) {
                            System.out.println("Invalid Choice");
                        }
                    }
                }
                if (select == hand.size() + 1){
                    System.out.println("Use a drink:");
                    for (int i = 0; i < Spire.player.getDrinks().size(); i++){
                        Drink d = Spire.player.getDrinks().get(i);
                        System.out.print((i + 1) + " - ");
                        System.out.println(d.getName());
                        
                    }
                    System.out.println((Spire.player.getDrinks().size()+1) + " - Back");
                    int drinkSelection = Spire.playerSelection(Spire.player.getDrinks().size()+1);
                    if (drinkSelection < Spire.player.getDrinks().size()){
                        Spire.player.getDrinks().get(drinkSelection).useDrink();
                        if (Spire.player.getDrinks().get(drinkSelection).getName().contains("Smoke Drink")){
                            endBattle = true;
                            break;
                        }
                        Spire.player.getDrinks().remove(drinkSelection);
                    }
                }
                if (select == hand.size() + 2) {
                    endTurn = true;
                }

                // Lets you choose the target if there are multiple
                Enemy target = enemies.get(0);
                if (enemies.size() > 1) {
                    if (hand.size() >= select) {
                        if (hand.get(select - 1).isUseOnEnemy()) {
                            ArrayList<Enemy> currentEnemies = new ArrayList<>();
                            for (Enemy e : enemies) {
                                if ((e.getHp() > 0) && (e.isRunThisTurn() == false)) {
                                    Enemy newE = new Enemy(e);
                                    currentEnemies.add(newE);
                                }
                            }
                            for (int i = 0; i < currentEnemies.size(); i++) {
                                Enemy e = currentEnemies.get(i);
                                System.out.print((i + 1) + " - ");
                                System.out.println(e.getName());
                            }
                            int targetSelect = 0;
                            boolean targetSelectValid = false;
                            // Repeatedly checks if the inputted value is correct until it is
                            while (targetSelectValid == false) {
                                if (targetSelect > 0 && targetSelect <= currentEnemies.size()) {
                                    targetSelectValid = true;
                                }
                                if (targetSelectValid == false) {
                                    // Takes the next input and tries to make an int out of it
                                    String input = scanner.nextLine();
                                    try {
                                        targetSelect = Integer.parseInt(input);
                                    } catch (Exception x) {

                                    }
                                    // if the Number is not between 1 and the number of options, tells the player and lets him choose again
                                    if (!(targetSelect > 0 && targetSelect <= currentEnemies.size())) {
                                        System.out.println("Invalid Choice");
                                    }
                                }
                            }
                            target = enemies.get(Spire.searchForIdInEnemyArray(enemies, currentEnemies.get(targetSelect - 1).getId()));
                        }
                    }
                }
                // If the player has enough mana, uses the card
                if (select > 0 && select <= hand.size()) {
                    if (Spire.player.getMana() >= hand.get(select - 1).getCost()) {
                        int uses = 1;
                        if (hand.get(select - 1).getAddition().contains("useallmana")) {
                            uses = Spire.player.getMana();
                        }
                        for (int i = 0; i < uses; i++) {
                            hand.get(select - 1).useCard(Spire.player, target, select - 1);
                            Spire.player.setMana(Spire.player.getMana() - hand.get(select - 1).getCost());
                        }
                        // If the card is a power card, it won't go into the discard pile and instead just get removed from combat.
                        if (!(hand.get(select - 1).getType().contains("Power"))) {
                            if (hand.get(select - 1).getAddition().contains("exhaust")) {
                                graveyard.add(hand.get(select - 1));
                            } else {
                                discard.add(hand.get(select - 1));
                            }
                        }
                        hand.remove(hand.get(select - 1));
                    } else {
                        System.out.println("Not enough mana");
                    }
                }
                if (Spire.player.getHp() == 0){
                    break;
                }
            }
            if (endBattle == true){
                break;
            }
            // Creates 2 smaller versions of an Enemy if it has splitting and is under half hp, else if it is alive, just uses the enemy's intention
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Splitting", "Buff")).getAmount() > 0 && enemies.get(i).getHp() < enemies.get(i).getMaxHp() / 2 && enemies.get(i).getHp() > 0) {
                    for (int j = 0; j < enemies.get(i).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Splitting", "Buff")).getAmount(); j++) {
                        Enemy newEnemy = new Enemy(enemies.get(i));
                        newEnemy.setMaxHp(newEnemy.getHp());
                        newEnemy.setHp(newEnemy.getMaxHp());
                        newEnemy.setName("Small " + enemies.get(i).getName());
                        newEnemy.setBlock(0);
                        newEnemy.setId(nextId);
                        nextId++;
                        for (int l = 0; l < newEnemy.getIntentions().size(); l++) {
                            if (newEnemy.getIntentions().get(l).getAmount() >= 2) {
                                newEnemy.getIntentions().get(l).setAmount(Math.round(newEnemy.getIntentions().get(l).getAmount() / 2));
                            }
                        }
                        addIntention(newEnemy);
                        addIntention(newEnemy);
                        for (int l = 0; l < newEnemy.getBuffs().size(); l++) {
                            newEnemy.getBuffs().get(l).setAmount(0);
                        }
                        newEnemy.setNoActionThisTurn(true);
                        enemies.add(newEnemy);
                    }
                    enemies.remove(i);
                } else if (enemies.get(i).getHp() > 0) {
                    if (enemies.get(i).isNoActionThisTurn()) {
                        if (enemies.get(i).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Barricade", "Buff")).getAmount() == 0){
                            enemies.get(i).setBlock(0);
                        }
                        enemies.get(i).setNoActionThisTurn(false);
                    }
                    else {
                        if (enemies.get(i).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Barricade", "Buff")).getAmount() == 0){
                            enemies.get(i).setBlock(0);
                        }
                        enemies.get(i).getCurrentIntentions().get(0).doIntention(Spire.player, enemies.get(i));
                        if (Spire.player.getHp() == 0){
                            if (Spire.isNameInArray(Spire.objRocksArray, "Stein von Lazarus", "Rock")){
                                if (r.nextInt(2) == 1){
                                    Spire.player.setHp(1);
                                    System.out.println("You died but were revived by the Stein von Lazarus");
                                }
                            }
                        }
                        if (enemies.get(i).getCurrentIntentions().get(0).getType().equals("Run away")) {
                            enemies.get(i).setRunThisTurn(true);
                            System.out.println("Enemy " + enemies.get(i).getName() + " has run away!");
                        }
                    }
                    Buff revengeDamage = enemies.get(i).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Revenge Damage", "Buff"));
                    revengeDamage.setAmount(0);
                    enemies.get(i).getCurrentIntentions().remove(0);
                    addIntention(enemies.get(i));
                }
            }
            // Empties the hand
            while (!(hand.isEmpty())) {
                if (hand.get(0).getAddition().contains("etherial")) {
                    graveyard.add(hand.get(0));
                    hand.remove(0);
                } else {
                    discard.add(hand.get(0));
                    hand.remove(0);
                }

            }
            if (Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Barricade", "Buff")).getAmount() == 0){
                Spire.player.setBlock(0);
            }
            Encounter.lowerBuffByOne("Frail");
            Encounter.lowerBuffByOne("Intangible");
            Encounter.lowerBuffByOne("Entangled");
            Buff scared = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Scared", "Buff"));
            if (scared.getAmount() == 1) {
                Encounter.increaseBuffByOne("Entangled");
                Encounter.lowerBuffByOne("Scared");
            }
        }
        if (isAnEnemyAlive(enemies) == false) {
            doReward();
            if (Spire.isNameInArray(Spire.objRocksArray, "Wachsender Stein", "Rock")) {
                Spire.player.setMaxHp(Spire.player.getMaxHp()+1);
                Spire.player.setHp(Spire.player.getHp()+1);
                System.out.println("Your Max HP grew by 1 because of your Wachsender Stein");
            }
        }
        else if (Spire.player.getHp() == 0){
            System.out.println("oof that must hurt, you just died because you didn't use your brain lmao");
        }
        else if (endBattle = true){
            System.out.println("The battle has ended.");
        }
        // Reset the player's buffs
        for (Buff b : Spire.player.getBuffs()) {
            b.setAmount(0);
        }

    }

    //
    //
    //  Encounter over, now only functions
    //
    //
    
    public void doReward() {
        System.out.println("You have won the battle!");
        System.out.println("Here are your rewards:");
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isRunThisTurn() == false) {
                reward.setMoney(reward.getMoney() + enemies.get(i).getStolenMoney());
            }
        }

        System.out.println("Money: " + (reward.getMoney()));
        if (reward.getCards().size() > 0){
            System.out.println("Choose one of these Cards:");
            for (int i = 0; i < reward.getCards().size(); i++){
                Card c = reward.getCards().get(i);
                System.out.print((i + 1) + " - ");
                System.out.println(c.getName() + " (" + c.getCost() + " Mana) " + c.getDescription());
            }
            System.out.println(reward.getCards().size()+1 + " - Nothing");
            int select = Spire.playerSelection(reward.getCards().size()+1);
            if (select == reward.getCards().size()){

            }
            else{
                Spire.player.getDeck().add(reward.getCards().get(select));
            }
        }
        if (reward.getDrinks().size() > 0){
            System.out.println("Take a Drink:");
            for (int i = 0; i < reward.getDrinks().size(); i++){
                Drink d = reward.getDrinks().get(i);
                System.out.print((i + 1) + " - ");
                System.out.println(d.getName());
            }
            System.out.println(reward.getDrinks().size()+1 + " - Nothing");
            int select = Spire.playerSelection(reward.getDrinks().size()+1);
            if (select == reward.getDrinks().size()){

            }
            else{
                Spire.player.getDrinks().add(reward.getDrinks().get(select));
            }
        }
        if (reward.getRocks().size() > 0){
            int select = 0;
            if (reward.getType().equals("Boss")){
                System.out.println("Choose a Rock:");
                for (int i = 0; i < reward.getRocks().size(); i++){
                    Rock r = reward.getRocks().get(i);
                    System.out.print((i + 1) + " - ");
                    System.out.println(r.getName());
                }
                System.out.println(reward.getRocks().size()+1 + " - Nothing");
                select = Spire.playerSelection(reward.getRocks().size()+1);
                if (select == reward.getRocks().size()){

                }
                else{
                    Spire.player.getRocks().add(reward.getRocks().get(select));
                }
            }
            else{
                while (select != reward.getRocks().size() && !reward.getRocks().isEmpty()){
                    System.out.println("Take a Rock:");
                    for (int i = 0; i < reward.getRocks().size(); i++){
                        Rock r = reward.getRocks().get(i);
                        System.out.print((i + 1) + " - ");
                        System.out.println(r.getName());
                    }
                    System.out.println(reward.getRocks().size()+1 + " - Nothing");
                    select = Spire.playerSelection(reward.getRocks().size()+1);
                    if (select == reward.getRocks().size()){

                    }
                    else{
                        Spire.player.getRocks().add(reward.getRocks().get(select));
                        reward.getRocks().remove(select);
                    }
                }
            }
        }
        
    }

    public static int selectCard(ArrayList<Card> cardList) {
        for (int i = 0; i < cardList.size(); i++) {
            Card c = cardList.get(i);
            System.out.print((i) + " - ");
            System.out.println(c.getName() + " (" + c.getCost() + " Mana) " + c.getDescription());

        }
        int cardSelect = 0;
        boolean selectValid = false;
        // Repeatedly checks if the inputted value is correct until it is
        while (selectValid == false) {
            if (cardSelect > 0 && cardSelect <= cardList.size() - 1) {
                selectValid = true;
            }
            if (selectValid == false) {
                // Takes the next input and tries to make an int out of it
                String input = scanner.nextLine();
                try {
                    cardSelect = Integer.parseInt(input);
                } catch (Exception x) {

                }
                // if the Number is not between 1 and the number of options, tells the player lets him choose again
                if (!(cardSelect > 0 && cardSelect <= cardList.size() - 1)) {
                    System.out.println("Invalid Choice");
                }
            }
        }
        return cardSelect;
    }

    public static boolean drawCard() {
        boolean handFull = false;
        // Puts the discard pile into the draw pile
        if (draw.isEmpty()) {
            while (!(discard.isEmpty())) {
                draw.add(discard.get(0));
                discard.remove(0);
            }
        }
        // Draws a random card out of the draw pile unless you already have max cards in your hand
        if (hand.size() < Spire.player.getMaxDraw()) {
            int cardNumber = r.nextInt(draw.size());
            hand.add(draw.get(cardNumber));
            draw.remove(draw.get(cardNumber));
        } // If the hand is full, tells the player so and stops attempting to draw cards
        else if (hand.size() == Spire.player.getMaxDraw()) {
            System.out.println("Too many cards in hand, can't draw any more");
            handFull = true;
        }
        return handFull;
    }

    public static void lowerBuffByOne(String buffName) {
        Buff buff = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, buffName, "Buff"));
        if (buff.getAmount() > 0) {
            buff.setAmount(buff.getAmount() - 1);
        }
    }

    public static void increaseBuffByOne(String buffName) {
        Buff buff = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, buffName, "Buff"));
        buff.setAmount(buff.getAmount() + 1);

    }

    public static void exhaustInHand(int cardPosition) {
        graveyard.add(hand.get(cardPosition));
        hand.remove(cardPosition);
    }

    public static void addIntention(Enemy enemy) {
        int randomIntention = r.nextInt(enemy.getIntentions().size());
        if(enemy.getName().equals("Emil")){
            randomIntention = 0;
        }
        enemy.getCurrentIntentions().add(enemy.getIntentions().get(randomIntention));
    }

    public static void rerollIntentionAtPosition(Enemy enemy, int pos) {
        Intention intention = enemy.getIntentions().get(r.nextInt(enemy.getIntentions().size()));
        enemy.getCurrentIntentions().set(pos, intention);
    }

    public boolean isAnEnemyAlive(ArrayList<Enemy> enemyList) {
        boolean oneAlive = false;
        for (Enemy e : enemyList) {
            if (e.getHp() > 0 && e.isRunThisTurn() == false) {
                oneAlive = true;
                break;
            }
        }
        return oneAlive;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
}
