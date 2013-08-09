/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.data;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lehtimik
 */
@Entity
@Table(name = "DrinkkiAinesosa")
public class DrinkkiAinesosa implements DrinkkiAinesosaRajapinta, Serializable {
    
     @Id
    @Column(name = "drinkkiainesosa_id")
    private String drinkkiainesosa_id;
     
    @ManyToOne
    @JoinColumn(name = "drinkki_id")
    private Drinkki drinkki;

    @ManyToOne
    @JoinColumn(name = "ainesosa_id")
    private Ainesosa ainesosa;

    @Column(name = "maara")
    private int maara;

    
     public DrinkkiAinesosa() {
        this.drinkkiainesosa_id = UUID.randomUUID().toString();
       
       
    }
    public Ainesosa getAinesosa() {
        return ainesosa;
    }

    public Drinkki getDrinkki() {
        return drinkki;
    }

    public int getMaara() {
        return maara;
    }

    public void setDrinkki(Drinkki drinkki) {
        this.drinkki = drinkki;
    }

    public void setAinesosa(Ainesosa ainesosa) {
        this.ainesosa = ainesosa;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }

    public String getDrinkkiainesosa_id() {
        return drinkkiainesosa_id;
    }

    public void setDrinkkiainesosa_id(String drinkkiainesosa_id) {
        this.drinkkiainesosa_id = drinkkiainesosa_id;
    }
    
    
}
