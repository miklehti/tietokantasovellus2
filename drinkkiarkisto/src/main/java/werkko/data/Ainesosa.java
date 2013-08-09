/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author lehtimik
 */
@Entity
@Table(name = "Ainesosa")
public class Ainesosa implements AinesosaRajapinta, Serializable{
     @Id
    @Column(name = "ainesosa_id")
    private String ainesosa_id;
    
    @Column(name = "ainesosa_name")
    @NotBlank
    @Length(min = 2, max = 15)
    private String ainesosa_name;
    
    @OneToMany(mappedBy = "ainesosa")
    private List<DrinkkiAinesosa> drinkkiAinesosa = new ArrayList<DrinkkiAinesosa>();

       public Ainesosa() {
        this.ainesosa_id = UUID.randomUUID().toString();
       
       
    }
    public void setDrinkkiAinesosa(List<DrinkkiAinesosa> drinkkiAinesosa) {
        this.drinkkiAinesosa = drinkkiAinesosa;
    }

    public List<DrinkkiAinesosa> getDrinkkiAinesosa() {
        return drinkkiAinesosa;
    }

    public String getAinesosa_id() {
        return ainesosa_id;
    }

    public String getAinesosa_name() {
        return ainesosa_name;
    }

    public void setAinesosa_id(String ainesosa_id) {
        this.ainesosa_id = ainesosa_id;
    }

    public void setAinesosa_name(String ainesosa_name) {
        this.ainesosa_name = ainesosa_name;
    }
    
    
}
