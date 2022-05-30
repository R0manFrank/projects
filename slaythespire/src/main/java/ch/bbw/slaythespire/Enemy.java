package ch.bbw.slaythespire;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Enemy {
    
    private String name;
    private int hp;
    private int maxHp;
    private int id;
    private ArrayList<Buff> buffs;
    private ArrayList<Intention> intentions;
    private ArrayList<Intention> currentIntentions;
    private int block;
    private String enemyType;
    private int stolenMoney;
    private boolean runThisTurn;
    private boolean noActionThisTurn;
    
    public Enemy(String name, int hp, String enemyType){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.id = 0;
        this.block = 0;
        this.enemyType = enemyType;
        this.noActionThisTurn = false;
        intentions = new ArrayList<>();
        buffs = new ArrayList<>();
        currentIntentions = new ArrayList<>();
        stolenMoney = 0;
        runThisTurn = false;
    }
    public Enemy (Enemy enemy){
        this.name = enemy.getName();
        this.hp = enemy.getHp();
        this.maxHp = enemy.getMaxHp();
        this.id = enemy.getId();
        this.block = enemy.getBlock();
        this.enemyType = enemy.getEnemyType();
        this.intentions = new ArrayList<>();
        for (Intention i : enemy.getIntentions()){
            intentions.add(new Intention (i));
        }
        this.currentIntentions = new ArrayList<>();
        this.buffs = new ArrayList<>();
        for (Buff b: enemy.getBuffs()){
            buffs.add(new Buff (b));
        }
        this.stolenMoney = enemy.getStolenMoney();
        this.runThisTurn = enemy.isRunThisTurn();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Intention> getIntentions() {
        return intentions;
    }

    public void setIntentions(ArrayList<Intention> intentions) {
        this.intentions = intentions;
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

    

    public ArrayList<Intention> getCurrentIntentions() {
        return currentIntentions;
    }

    public void setCurrentIntentions(ArrayList<Intention> currentIntentions) {
        this.currentIntentions = currentIntentions;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    public int getStolenMoney() {
        return stolenMoney;
    }

    public void setStolenMoney(int stolenMoney) {
        this.stolenMoney = stolenMoney;
    }

    public boolean isRunThisTurn() {
        return runThisTurn;
    }

    public void setRunThisTurn(boolean runThisTurn) {
        this.runThisTurn = runThisTurn;
    }

    public boolean isNoActionThisTurn() {
        return noActionThisTurn;
    }

    public void setNoActionThisTurn(boolean noActionThisTurn) {
        this.noActionThisTurn = noActionThisTurn;
    }
    
    
    
}
