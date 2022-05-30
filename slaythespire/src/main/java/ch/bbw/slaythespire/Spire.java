/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.slaythespire;

import static ch.bbw.slaythespire.Encounter.scanner;
import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Spire {

    /* Plans
    
    revengism on player
    splitting on player
    shop fixen
    in shop try to steal, for drink 50% chance to get curse, card 66%, relic 75%
    
    Rocks:
    
    Cards:
    name: void, status -1 mana etherial unplayable
    
    Drinks:
    
    Enemies:
    (elite) name: svpler hp:50 moves:foreigners out 1 (block 5 + throw 1 random card out that wasnt in starting deck), damage the economy 1 (13 dmg, gives 1 void in deck when deal unblocked damage),  buure spawne
    
    Secret room:
    
    
     */
    public static Player player = new Player();

    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Card> cards = new ArrayList<>();
    public static ArrayList<Rock> rocks = new ArrayList<>();
    public static ArrayList<Drink> drinks = new ArrayList<>();
    public static ArrayList<Buff> buffs = new ArrayList<>();
    public static ArrayList<Secret> secrets = new ArrayList<>();

    public static ArrayList<Object> objBuffsArray = new ArrayList();
    public static ArrayList<Object> objRocksArray = new ArrayList();

    public static void main(String[] args) {

        rocks.add(new Rock("Stein des Auges", "shop reward"));
        rocks.add(new Rock("Wachsender Stein", ""));
        rocks.add(new Rock("Stein von Lazarus", ""));
        
        drinks.add(new Drink("Zigüner Drink", "shop reward"));
        drinks.add(new Drink("Smoke Drink", "shop reward"));

        secrets.add(new Secret("Gorillakleber"));
        secrets.add(new Secret("Polen"));
        secrets.add(new Secret("Küche"));

        buffs.add(new Buff("Strength", true)); // deal 1 more damage for every strength
        buffs.add(new Buff("Dexterity", true)); // gain 1 more defense for every dexterity
        buffs.add(new Buff("Frail", false)); // lowers your defense gained by 33%
        buffs.add(new Buff("Stealing", true)); // steal x amount of money every attack
        buffs.add(new Buff("Splitting", true)); // splits into x amount of a smaller version if below half health
        buffs.add(new Buff("Vampirism", true)); // heal x amount every time you deal damage
        buffs.add(new Buff("Life Steal", true)); // heal the amount of damage you dealt
        buffs.add(new Buff("Shares", true)); // gain x amount more mana every turn
        buffs.add(new Buff("Entangled", false)); // makes you unable to attack
        buffs.add(new Buff("Intangible", true)); // reduces all damage to 1
        buffs.add(new Buff("Scared", false)); // gives you entangled the next turn
        buffs.add(new Buff("Barricade", true)); // makes you block retain between turns
        buffs.add(new Buff("Revengism", true)); // deal damage back for every damage taken
        buffs.add(new Buff("Revenge Damage", true)); // counts the damage you will deal back

        Spire.objBuffsArray = new ArrayList<>(buffs);

        enemies.add(new Enemy("Schleim", 30, "Enemy"));
        enemies.get(0).getIntentions().add(new Intention("Attack", 5));
        enemies.get(0).getIntentions().add(new Intention("Defend", 10));

        enemies.add(new Enemy("Zigüner", 40, "Enemy"));
        enemies.get(1).getIntentions().add(new Intention("Attack", 10));
        enemies.get(1).getIntentions().add(new Intention("Defend", 15));
        enemies.get(1).getIntentions().add(new Intention("Run away", 1));

        enemies.add(new Enemy("Fledermaus", 30, "Enemy"));
        enemies.get(2).getIntentions().add(new Intention("Attack", 11));
        enemies.get(2).getIntentions().add(new Intention("Buff Strength", 3));
        enemies.get(2).getIntentions().add(new Intention("Debuff Frail", 2));
        
        enemies.add(new Enemy("Emil", 50, "Elite"));
        enemies.get(3).getIntentions().add(new Intention("Defend", 6));
        enemies.get(3).getIntentions().add(new Intention("Attack", 0));
        enemies.get(3).setBlock(20);

        Card strike = new Card("Schlag", 6, 1, 0, "Attack", "none", "common", true, "starting", "");
        Card defend = new Card("Verteidigen", 0, 1, 5, "Skill", "none", "common", false, "starting", "");

        cards.add(strike);
        cards.add(defend);
        cards.add(new Card("Autismus", 0, 1, 0, "Power", "none", "uncommon", false, "shop reward", "Gives 3 strength."));
        cards.add(new Card("Sozialkapitalismus", 0, 0, 0, "Skill", "exhaust etherial", "rare", false, "shop reward", "Gives you an enemy's buffs and gives them your debuffs. Exhaust. Etherial."));
        cards.add(new Card("Schlaganfall", 2, 1, 0, "Attack", "multiuse", "common", true, "shop reward", "Hits 4 times."));
        cards.add(new Card("Schleim", 0, 1, 0, "Status", "exhaust", "common", false, "", "Exhaust."));
        cards.add(new Card("Vampirismus", 0, 2, 0, "Power", "none", "rare", false, "shop reward", "Makes every Attack heal 1 hp."));
        cards.add(new Card("Aktienkauf", 0, 3, 0, "Power", "none", "rare", false, "shop reward", "Gives 1 extra Mana every turns."));
        cards.add(new Card("Daumen Drehen", 0, 0, 0, "Skill", "none", "common", false, "shop reward", "Draws 1 Card."));
        cards.add(new Card("Angsthase", 0, 2, 0, "Skill", "none", "uncommon", false, "shop reward", "Gives 1 intangible. Can't attack next turn."));
        cards.add(new Card("Schützengraben", 0, 1, 6, "Skill", "useallmana", "uncommon", false, "shop reward", "Uses X times"));
        cards.add(new Card("Karte essen", 0, 0, 0, "Skill", "exhaust", "uncommon", false, "shop reward", "Select a card to exhaust and gain the amount of energy it costs. Exhaust."));
        cards.add(new Card("Blendgranate", 5, 1, 0, "Attack", "none", "uncommon", true, "shop reward", "Draws a card, if that card is an attack, reroll the enemy's intention."));
        cards.add(new Card("Versorgung", 0, 1, 0, "Skill", "exhaust", "uncommon", false, "shop reward", "Draws 1-3 rare cards that exhaust after 1 use."));
        cards.add(new Card("Friedhof", 0, 1, 0, "Skill", "exhaust", "common", false, "shop reward", "Allows you to take a card out of the graveyard."));
        cards.add(new Card("Feuerzeug", 15, 1, 0, "Attack", "exhaust", "uncommon", true, "", "Gains 3 damage for every killing blow it deals. Deals 3 damage to you. Exhaust."));
        
        player.getDeck().add(new Card(strike));
        player.getDeck().add(new Card(strike));
        player.getDeck().add(new Card(strike));
        player.getDeck().add(new Card(strike));
        player.getDeck().add(new Card(defend));
        player.getDeck().add(new Card(defend));
        player.getDeck().add(new Card(defend));
        player.getDeck().add(new Card(defend));
        
        player.getDrinks().add(new Drink(drinks.get(1)));

        player.setBuffs(buffs);
        for (Enemy e : enemies) {
            for (Buff b : buffs) {
                e.getBuffs().add(new Buff(b));
            }
        }
        enemies.get(0).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Splitting", "Buff")).setAmount(2);

        enemies.get(1).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Stealing", "Buff")).setAmount(15);

        enemies.get(2).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Life Steal", "Buff")).setAmount(1);
        
        enemies.get(3).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Barricade", "Buff")).setAmount(1);
        enemies.get(3).getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, "Revengism", "Buff")).setAmount(1);
        
        
        Path path = new Path(20);
        for (int i = 0; i < path.getLength(); i++) {
            if (player.getHp() != 0) {
                path.nextF(i+1);
            }
        }
        if (player.getHp() != 0) {
            System.out.println("you're just fucking lucky");
        }

    }

    public static int searchForNameInArray(ArrayList<Object> array, String name, String className) {
        int position = 0;
        if (className.equals("Buff")) {
            for (int i = 0; i < array.size(); i++) {
                Buff buff = Buff.class.cast(array.get(i));
                if (buff.getName().equals(name)) {
                    position = i;
                }
            }
        } else if (className.equals("Rock")) {
            for (int i = 0; i < array.size(); i++) {
                Rock rock = Rock.class.cast(array.get(i));
                if (rock.getName().equals(name)) {
                    position = i;
                }
            }
        } else if (className.equals("Drink")) {
            for (int i = 0; i < array.size(); i++) {
                Drink drink = Drink.class.cast(array.get(i));
                if (drink.getName().equals(name)) {
                    position = i;
                }
            }
        } else if (className.equals("Card")) {
            for (int i = 0; i < array.size(); i++) {
                Card card = Card.class.cast(array.get(i));
                if (card.getName().equals(name)) {
                    position = i;
                }
            }
        } else if (className.equals("Enemy")) {
            for (int i = 0; i < array.size(); i++) {
                Enemy enemy = Enemy.class.cast(array.get(i));
                if (enemy.getName().equals(name)) {
                    position = i;
                }
            }
        }
        return position;
    }

    public static boolean isNameInArray(ArrayList<Object> array, String name, String className) {
        boolean exists = false;
        if (className.equals("Buff")) {
            for (int i = 0; i < array.size(); i++) {
                Buff buff = Buff.class.cast(array.get(i));
                if (buff.getName().equals(name)) {
                    exists = true;
                }
            }
        } else if (className.equals("Rock")) {
            for (int i = 0; i < array.size(); i++) {
                Rock rock = Rock.class.cast(array.get(i));
                if (rock.getName().equals(name)) {
                    exists = true;
                }
            }
        } else if (className.equals("Drink")) {
            for (int i = 0; i < array.size(); i++) {
                Drink drink = Drink.class.cast(array.get(i));
                if (drink.getName().equals(name)) {
                    exists = true;
                }
            }
        } else if (className.equals("Card")) {
            for (int i = 0; i < array.size(); i++) {
                Card card = Card.class.cast(array.get(i));
                if (card.getName().equals(name)) {
                    exists = true;
                }
            }
        } else if (className.equals("Enemy")) {
            for (int i = 0; i < array.size(); i++) {
                Enemy enemy = Enemy.class.cast(array.get(i));
                if (enemy.getName().equals(name)) {
                    exists = true;
                }
            }
        }
        return exists;
    }

    public static int searchForIdInEnemyArray(ArrayList<Enemy> array, int id) {
        int position = 0;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getId() == id) {
                position = i;
            }
        }
        return position;
    }

    public static int playerSelection(int max) {
        int select = 0;
        boolean selectValid = false;
        // Repeatedly checks if the inputted value is correct until it is
        while (selectValid == false) {
            if (select > 0 && select <= max) {
                selectValid = true;
            }
            if (selectValid == false) {
                // Takes the next input and tries to make an int out of it
                String input = scanner.nextLine();
                try {
                    select = Integer.parseInt(input);
                } catch (NumberFormatException x) {

                }
                // if the Number is not between 1 and the number of options, tells the player lets him choose again
                if (!(select > 0 && select <= max)) {
                    System.out.println("Invalid Choice");
                }
            }
        }
        select--;
        return select;
    }
}
