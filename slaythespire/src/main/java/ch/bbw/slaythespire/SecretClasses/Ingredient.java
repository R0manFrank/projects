package ch.bbw.slaythespire.SecretClasses;

import ch.bbw.slaythespire.Buff;
import java.util.Random;

/**
 *
 * @author roman
 */
public class Ingredient {
    
    Random r = new Random();
    
    private String name;
    private Buff buff;
    private boolean used;
    
    public Ingredient(String name, Buff buff){
        this.name = name;
        this.buff = buff;
        this.buff.setAmount(r.nextInt(3)+1);
        this.used = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
    
}
