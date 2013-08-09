/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author lehtimik
 */
@Entity
@Table(name = "Tyyppi")
public class Tyyppi implements TyyppiRajapinta, Serializable {
    @Id
    @Column(name = "tyyppi_id")
    private String tyyppi_id;
    
    @Column(name = "tyyppi_name")
    @NotBlank
    @Length(min = 2, max = 15)
    private String tyyppi_name;
    
    @ManyToMany(mappedBy="tyypit")
   private List<Drinkki> drinkit;

    public Tyyppi() {
        this.tyyppi_id = UUID.randomUUID().toString();
       
       
    }
    public String getTyyppi_id() {
        return tyyppi_id;
    }

    public String getTyyppi_name() {
        return tyyppi_name;
    }

    public List<Drinkki> getDrinkit() {
        return drinkit;
    }

    public void setTyyppi_id(String tyyppi_id) {
        this.tyyppi_id = tyyppi_id;
    }

    public void setTyyppi_name(String tyyppi_name) {
        this.tyyppi_name = tyyppi_name;
    }

    public void setDrinkit(List<Drinkki> drinkit) {
        this.drinkit = drinkit;
    }
    

}
