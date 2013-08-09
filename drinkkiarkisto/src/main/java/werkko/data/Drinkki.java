/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author lehtimik
 */
@Entity
@Table(name = "Drinkki")
public class Drinkki implements DrinkkiRajapinta, Serializable, Comparable<Drinkki> {
    @Id
    @Column(name = "drinkki_id")
    private String drinkki_id;
    
    @Column(name = "drinkki_name")
    @NotBlank
    @Length(min = 2, max = 15)
    private String drinkki_name;
    
     @OneToMany(mappedBy = "drinkki")
    private List<DrinkkiAinesosa> drinkkiAinesosa = new ArrayList<DrinkkiAinesosa>();
     
      @ManyToMany
   @JoinTable(name="DrinkkiTyypit",  
              joinColumns=@JoinColumn(name="Drinkki_id"), 
              inverseJoinColumns=@JoinColumn(name="Tyyppi_id"))
   private List<Tyyppi> tyypit;

    public List<DrinkkiAinesosa> getDrinkkiAinesosa() {
        return drinkkiAinesosa;
    }

       public Drinkki() {
        this.drinkki_id = UUID.randomUUID().toString();
       
       
    }
    public String getDrinkki_id() {
        return drinkki_id;
    }

    public String getDrinkki_name() {
        return drinkki_name;
    }

    public void setDrinkkiAinesosa(List<DrinkkiAinesosa> drinkkiAinesosa) {
        this.drinkkiAinesosa = drinkkiAinesosa;
    }

    public void setDrinkki_id(String drinkki_id) {
        this.drinkki_id = drinkki_id;
    }

    public void setDrinkki_name(String drinkki_name) {
        this.drinkki_name = drinkki_name;
    }

    public void setTyypit(List<Tyyppi> tyypit) {
        this.tyypit = tyypit;
    }

    public List<Tyyppi> getTyypit() {
        return tyypit;
    }
    
    
    public int compareTo(Drinkki verrattavaDrinkki) {
     return this.drinkki_name.toLowerCase().compareTo(verrattavaDrinkki.getDrinkki_name().toLowerCase());
    }
    
     
}
