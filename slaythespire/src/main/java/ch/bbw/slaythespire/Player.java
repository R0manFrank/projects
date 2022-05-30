package ch.bbw.slaythespire;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Player {
    
    private ArrayList<Rock> rocks;
    private ArrayList<Card> deck;
    private ArrayList<Drink> drinks;
    private int money;
    private int hp;
    private int maxHp;
    private int mana;
    private int maxMana;
    private ArrayList<Buff> buffs;
    private int block;
    private int draw;
    private int maxDraw;
    private int cardSelection;
    
    public Player(){
        this.money = 99;
        this.hp = 70;
        this.mana = 3;
        this.block = 0;
        this.maxMana = 10;
        this.maxHp = 70;
        this.draw = 4;
        this.maxDraw = 10;
        rocks = new ArrayList<>();
        deck = new ArrayList<>();
        drinks = new ArrayList<>();
        buffs = new ArrayList<>();
        this.cardSelection = 4;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public void setRocks(ArrayList<Rock> rocks) {
        this.rocks = rocks;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getMaxDraw() {
        return maxDraw;
    }

    public void setMaxDraw(int maxDraw) {
        this.maxDraw = maxDraw;
    }

    public int getCardSelection() {
        return cardSelection;
    }

    public void setCardSelection(int cardSelection) {
        this.cardSelection = cardSelection;
    }
    
    
}
