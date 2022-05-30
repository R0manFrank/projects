package ch.bbw.slaythespire;

import static ch.bbw.slaythespire.Encounter.scanner;
import ch.bbw.slaythespire.SecretClasses.Ingredient;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author roman
 */
public class Secret {

    Random r = new Random();

    private String name;

    public Secret(String name) {
        this.name = name;
    }

    public Secret(Secret secret) {
        this.name = secret.getName();
    }

    public void doSecret() {
        System.out.println("You have entered a Secret room.");
        int percent = r.nextInt(100) + 1;
        if (percent <= 33) {
            Encounter encounter = new Encounter("Encounter");
            encounter.doEncounter();
        }
        if (percent > 33) {
            if (name.contains("Polen")) {
                System.out.println("You crossed Poland and were encountered by a thief.");
                System.out.println("He asks you for one of 3 cards or else.");
                ArrayList<Card> choiceCards = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    int randomCard = r.nextInt(Spire.player.getDeck().size());
                    choiceCards.add(Spire.player.getDeck().get(randomCard));
                    Spire.player.getDeck().remove(randomCard);
                }
                System.out.println("Your choices:");
                for (int i = 0; i < choiceCards.size(); i++) {
                    Card c = choiceCards.get(i);
                    System.out.print((i + 1) + " - ");
                    System.out.println(c.getName() + " (" + c.getCost() + " Mana) " + c.getDescription());
                }
                int select = 0;
                boolean selectValid = false;
                // Repeatedly checks if the inputted value is correct until it is
                while (selectValid == false) {
                    if (select > 0 && select <= choiceCards.size()) {
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
                        if (!(select > 0 && select <= choiceCards.size())) {
                            System.out.println("Invalid Choice");
                        }
                    }
                }
                select--;
                System.out.println("The thief stole your " + choiceCards.get(select).getName() + ".");
                choiceCards.remove(select);
                while (!(choiceCards.isEmpty())){
                    Spire.player.getDeck().add(choiceCards.get(0));
                    choiceCards.remove(0);
                }
            }
            if (name.contains("Gorillakleber")) {
                System.out.println("You encountered a Gorilla in a cave.");
                System.out.println("Gorilla: Hey traveller, I just created a new and all powerful white sticky substance.");
                System.out.println("Gorilla: Can you please try this out for me?");
                if (Spire.player.getDeck().size() > 1) {
                    System.out.println("Select 2 cards to fuse");
                    Card selectone = new Card(Spire.cards.get(0));
                    Card selecttwo = new Card(Spire.cards.get(0));
                    for (int i = 1; i <= 2; i++) {
                        for (int j = 0; j < Spire.player.getDeck().size(); j++) {
                            Card c = Spire.player.getDeck().get(j);
                            System.out.print((j + 1) + " - ");
                            System.out.println(c.getName() + " (" + c.getCost() + " Mana) " + c.getDescription());
                        }
                        int select = 0;
                        boolean selectValid = false;
                        // Repeatedly checks if the inputted value is correct until it is
                        while (selectValid == false) {
                            if (select > 0 && select <= Spire.player.getDeck().size()) {
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
                                if (!(select > 0 && select <= Spire.player.getDeck().size())) {
                                    System.out.println("Invalid Choice");
                                }
                            }
                        }
                        select--;
                        if (i == 1) {
                            selectone = new Card(Spire.player.getDeck().get(select));
                        }
                        if (i == 2) {
                            selecttwo = new Card(Spire.player.getDeck().get(select));
                        }
                        Spire.player.getDeck().remove(select);
                    }
                    Card fusedCard = new Card(selectone);
                    fusedCard.setAddition(fusedCard.getAddition() + " " + selecttwo.getAddition());
                    fusedCard.setCost(fusedCard.getCost() + selecttwo.getCost());
                    fusedCard.setDamage(fusedCard.getDamage() + selecttwo.getDamage());
                    fusedCard.setDefense(fusedCard.getDefense() + selecttwo.getDefense());
                    fusedCard.setDescription(fusedCard.getDescription() + " " + selecttwo.getDescription());
                    fusedCard.setName(fusedCard.getName() + " " + selecttwo.getName());
                    fusedCard.setRarity("rare");
                    fusedCard.setType("Fusion");
                    if (selecttwo.isUseOnEnemy()) {
                        fusedCard.setUseOnEnemy(true);
                    }
                    Spire.player.getDeck().add(new Card(fusedCard));
                } else {
                    System.out.println("You dont have enough cards to fuse.");
                }
            }
            if (name.contains("Küche")){
                System.out.println("You find a Kitchen with a bottle and some ingredients");
                ArrayList<String> base = new ArrayList<>();
                base.add("Brokkoli");
                base.add("Apfel");
                base.add("Banane");
                base.add("Ratte");
                base.add("Aal");
                base.add("Röselikohl");
                base.add("Feder");
                base.add("Fertig Ramen Noodles");
                base.add("Abgeloffene Milch");
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for (String s: base){
                    ingredients.add(new Ingredient(s, new Buff(Spire.buffs.get(r.nextInt(Spire.buffs.size())))));
                }
                ArrayList<Ingredient> drink = new ArrayList<>();
                for (int i = 0; i < 3; i++){
                    System.out.println("You can choose one of 3 ingredients to add to the bottle");
                    ArrayList<Ingredient> tempIngredients = new ArrayList<>();
                    for (int j = 0; j < 3; j++){
                        boolean done = false;
                        while (done == false){
                            int pos = r.nextInt(ingredients.size());
                            if (!ingredients.get(pos).isUsed()){
                                tempIngredients.add(ingredients.get(pos));
                                ingredients.get(pos).setUsed(true);
                                done = true;
                            }
                        }
                    }
                    for (int j = 0; j < 3; j++){
                        System.out.print((j+1)+ " - ");
                        System.out.println(tempIngredients.get(j).getName());
                    }
                    int ingredientSelection = Spire.playerSelection(3);
                    drink.add(tempIngredients.get(ingredientSelection));
                }
                System.out.println("You mix everything together to create a tasty looking smoothie");
                System.out.println("You drank a bottle containing " + drink.get(0).getName() + ", " + drink.get(1).getName() + ", " + drink.get(2).getName());
                System.out.println("You got");
                for (Ingredient i : drink){
                    System.out.println(i.getBuff().getAmount() + " " + i.getBuff().getName());
                    Buff b = Spire.player.getBuffs().get(Spire.searchForNameInArray(Spire.objBuffsArray, i.getBuff().getName(), "Buff"));
                    b.setAmount(b.getAmount()+i.getBuff().getAmount());
                }
                System.out.println("from drinking the bottle for the next battle");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
