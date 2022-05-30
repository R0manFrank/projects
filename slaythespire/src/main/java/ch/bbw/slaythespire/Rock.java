/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbw.slaythespire;

/**
 *
 * @author roman
 */
public class Rock {
    
    private String name;
    private String pool;
    
    public Rock(String name, String pool){
        this.name = name;
        this.pool = pool;
    }
    
    public Rock(Rock rock){
        this.name = rock.getName();
        this.pool = rock.getPool();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
    
    
}
