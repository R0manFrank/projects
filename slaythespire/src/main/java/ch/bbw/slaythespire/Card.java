package ch.bbw.slaythespire;

import static ch.bbw.slaythespire.Encounter.scanner;
import java.util.Random;

/**
 *
 * @author roman
 */
public class Card {
    
    Random r = new Random();

    private String state;
    private int damage;
    private int cost;
    private int defense;
    private String name;
    private String description;
    private String type;
    private String addition;
    private String rarity;
    private boolean useOnEnemy;
    private String pool;

    public Card(String name, int damage, int cost, int defense, String type, String addition, String rarity, boolean useOnEnemy, String pool, String description) {
        this.state = "";
        this.damage = damage;
        this.cost = cost;
        this.defense = defense;
        this.name = name;
        this.type = type;
        this.addition = addition;
        this.useOnEnemy = useOnEnemy;
        this.description = description;
        this.rarity = rarity;
        this.pool = pool;
    }

    public Card(Card card) {
        this.state = card.getState();
        this.damage = card.getDamage();
        this.cost = card.getCost();
        this.defense = card.getDefense();
        this.name = card.getName();
        this.type = card.getType();
        this.addition = card.getAddition();
        this.useOnEnemy = card.isUseOnEnemy();
        this.description = card.getDescription();
        this.rarity = card.getRarity();
        this.pool = card.getPool();
    }

    public void useCard(Player player, Enemy enemy, int cardPosition) {
        System.out.println("You used " + name);
        int uses = 1;
        if (addition.contains("multiuse")) {
            uses = 2;
        }
        if (name.equals("Schlaganfall")) {
            uses = 4;
        }
        if (damage > 0) {
            int damageDealt = 0;
            int strength = 0;
            for (Buff b : player.getBuffs()) {
                if (b.getName().equals("Strength")) {
                    strength = b.getAmount();
                }
            }
            int trueDamage = damage + strength;
            for (int i = 0; i < uses; i++) {
                if (enemy.getBlock() > 0) {
                    if (enemy.getBlock() >= trueDamage) {
                        enemy.setBlock(enemy.getBlock() - trueDamage);
                        System.out.println("Enemy blocked " + trueDamage + " damage.");
                    } else {
                        int damagecalc = trueDamage - enemy.getBlock();
                        enemy.setBlock(0);
                        System.out.println("You have dealt " + damagecalc + " damage");
                        enemy.setHp(enemy.getHp() - damagecalc);
                        damageDealt+=damagecalc;                        
                    }
                } else {
                    System.out.println("You have dealt " + trueDamage + " damage");
                    enemy.setHp(enemy.getHp() - trueDamage);
                    damageDealt+=trueDamage;
                }
            }
            if (damageDealt > 0){
                stealMoney();
                vampirismHeal();
                lifestealHeal();
                revengism(enemy, damageDealt);
            }

            if (enemy.getHp() < 0) {
                enemy.setHp(0);
            }
            System.out.println("Enemy has " + enemy.getHp() + "/" + enemy.getMaxHp() + " HP");
        }
        if (defense > 0) {
            int dexterity = 0;
            for (Buff b : player.getBuffs()) {
                if (b.getName().equals("Dexterity")) {
                    dexterity = b.getAmount();
                }
            }
            int trueDefense = defense + dexterity;
            if (player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Frail", "Buff")).getAmount() > 0) {
                float defenseFrail = trueDefense * 0.65f;
                trueDefense = Math.round(defenseFrail);
            }
            for (int i = 0; i < uses; i++) {
                player.setBlock(player.getBlock() + trueDefense);
            }

        }
        if (name.contains("Autismus")) {
            int plusStrength = 3;
            for (Buff b : Spire.player.getBuffs()) {
                if (b.getName().equals("Strength")) {
                    b.setAmount(b.getAmount() + plusStrength);
                    System.out.println("Your Strength has been increased by " + plusStrength);
                }
            }
        }
        if (name.contains("Sozialkapitalismus")) {
            for (Buff pb : Spire.player.getBuffs()) {
                for (Buff eb : enemy.getBuffs()) {
                    if (pb.getName().equals(eb.getName())) {
                        if (pb.getAmount() > eb.getAmount() && pb.isPositive() == false) {
                            eb.setAmount(pb.getAmount());
                        }
                        if (pb.getAmount() < eb.getAmount() && eb.isPositive() == true) {
                            pb.setAmount(eb.getAmount());
                        }
                    }
                }
            }
        }
        if (name.contains("Vampirismus")) {
            Buff vampirism = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Vampirism", "Buff"));
            vampirism.setAmount(vampirism.getAmount() + 1);
            System.out.println("Your Vampirism has been increased by 1");
        }

        if (name.contains("Aktienkauf")) {
            Buff shares = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Shares", "Buff"));
            shares.setAmount(shares.getAmount() + 1);
            System.out.println("Your Aktienkauf has been increased by 1");
        }
        if (name.contains("Daumen Drehen")) {
            Encounter.drawCard();
        }
        if (name.contains("Angsthase")) {
            Buff intangible = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Intangible", "Buff"));
            Buff scared = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Scared", "Buff"));
            scared.setAmount(1);
            intangible.setAmount(intangible.getAmount() + 1);
        }
        if (name.contains("Karte essen")) {
            if (Encounter.hand.size() >= 2){
                System.out.println("Select a card to exhaust and gain its mana");
                int exhaustCardPosition = selectOtherCard(cardPosition);
                int manaValue = Encounter.hand.get(exhaustCardPosition).getCost()-1;
                Spire.player.setMana(Spire.player.getMana()+manaValue);
                Encounter.exhaustInHand(exhaustCardPosition);
            }
            else{
                System.out.println("No card to exhaust");
            }
            
        }
        if (name.contains("Blendgranate")){
            Encounter.drawCard();
            if (Encounter.hand.get(Encounter.hand.size()-1).getDamage() > 0){
                Encounter.rerollIntentionAtPosition(enemy, 0);
            }
        }
        if (name.contains("Versorgung")){
            int cardAmount = r.nextInt(3)+1;
            for (int i = 0; i < cardAmount; i++){
                boolean drawn = false;
                while (drawn == false){
                    int randomPosition = r.nextInt(Spire.cards.size());
                    Card c = new Card(Spire.cards.get(randomPosition));
                    if (c.getRarity().equals("rare")){
                        c.setAddition(c.getAddition() + " " + "exhaust");
                        Encounter.draw.add(c);
                        drawn = true;
                    }
                }
            }
        }
        if (name.contains("Friedhof")){
            if (Encounter.graveyard.size() > 0){
                int cardPos = Encounter.selectCard(Encounter.graveyard);
                Encounter.hand.add(Encounter.graveyard.get(cardPos-1));
                Encounter.graveyard.remove(cardPos-1);
            }
            else{
                System.out.println("There is no card in the graveyard");
            }   
        }
        if (name.contains("Feuerzeug")){
            if (enemy.getHp() <= 0){
                damage = damage+3;
            }
            System.out.println("You took 3 damage from using Feuerzeug");
            Spire.player.setHp(Spire.player.getHp()-3);
            if (Spire.player.getHp() < 0){
                Spire.player.setHp(0);
            }
        }
    }

    public void stealMoney() {
        Buff stealing = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Stealing", "Buff"));
        if (stealing.getAmount() > 0) {
            Spire.player.setMoney(Spire.player.getMoney() + stealing.getAmount());
            System.out.println("You stole " + stealing.getAmount() + " money");
        }
    }

    public void vampirismHeal() {
        Buff vampirism = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Vampirism", "Buff"));
        if (vampirism.getAmount() > 0) {
            Spire.player.setHp(Spire.player.getHp() + vampirism.getAmount());
            if (Spire.player.getHp() > Spire.player.getMaxHp()) {
                Spire.player.setHp(Spire.player.getMaxHp());
            }
        }
    }
    
    public void lifestealHeal(){
        Buff lifesteal = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Life Steal", "Buff"));
        if (lifesteal.getAmount() > 0){
            Spire.player.setHp(Spire.player.getHp() + damage);
            if (Spire.player.getHp() > Spire.player.getMaxHp()) {
                Spire.player.setHp(Spire.player.getMaxHp());
            }
        }
    }
    
    public void revengism(Enemy enemy, int damage){
        Buff revengism = enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Revengism", "Buff"));
        Buff revengeDamage = enemy.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Revenge Damage", "Buff"));
        if (revengism.getAmount() > 0){
            revengeDamage.setAmount(revengeDamage.getAmount()+damage);
        }
        if (enemy.getName().equals("Emil")){
            if (enemy.getCurrentIntentions().get(0).getType().equals("Defend")){
                enemy.getCurrentIntentions().set(0, enemy.getIntentions().get(1));
            }
            enemy.getCurrentIntentions().get(0).setAmount(revengeDamage.getAmount());
        }
        
    }

    public static int selectOtherCard(int cardPosition) {
        Card usedCard = Encounter.hand.get(cardPosition);
        int counter = 1;
        for (int i = 0; i < Encounter.hand.size(); i++) {
            Card c = Encounter.hand.get(i);
            if (c != usedCard) {
                System.out.print((counter) + " - ");
                System.out.println(c.getName() + " (" + c.getCost() + " Mana) " + c.getDescription());
                counter++;
            }

        }
        int cardSelect = 0;
        boolean selectValid = false;
        // Repeatedly checks if the inputted value is correct until it is
        while (selectValid == false) {
            if (cardSelect > 0 && cardSelect <= Encounter.hand.size()-1) {
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
                if (!(cardSelect > 0 && cardSelect <= Encounter.hand.size()-1)) {
                    System.out.println("Invalid Choice");
                }
            }
        }
        cardSelect--;
        if (cardSelect >= cardPosition){
            cardSelect++;
        }
        return cardSelect;

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public boolean isUseOnEnemy() {
        return useOnEnemy;
    }

    public void setUseOnEnemy(boolean useOnEnemy) {
        this.useOnEnemy = useOnEnemy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
    
}
