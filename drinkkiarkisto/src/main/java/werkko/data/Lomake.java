/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.data;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author lehtimik
 */
public abstract class Lomake {

    @NotBlank
    @Length(min = 2, max = 15)
    String drinkki_name;
    String tyyppi_name;
    @NotBlank
    @Length(min = 2, max = 15)
    String ainesosa_name;
    @NotNull
    Integer maara;
    String ainesosa2;
    Integer maara2;
    String ainesosa3;
    Integer maara3;
    String ainesosa4;
    Integer maara4;
    String ainesosa5;
    Integer maara5;
    String errorViesti;
    Drinkki luotavaDrinkki;

    public Lomake() {
        this.errorViesti = "";
       
    }

    public void setLuotavaDrinkki(Drinkki luotavaDrinkki) {
        this.luotavaDrinkki = luotavaDrinkki;
    }

    public Drinkki getLuotavaDrinkki() {
        return luotavaDrinkki;
    }

    public String getAinesosa2() {
        return ainesosa2;
    }

    public Integer getMaara2() {
        return maara2;
    }

    public String getAinesosa3() {
        return ainesosa3;
    }

    public Integer getMaara3() {
        return maara3;
    }

    public String getAinesosa4() {
        return ainesosa4;
    }

    public Integer getMaara4() {
        return maara4;
    }

    public String getAinesosa5() {
        return ainesosa5;
    }

    public Integer getMaara5() {
        return maara5;
    }

    public void setAinesosa2(String ainesosa2) {
        this.ainesosa2 = ainesosa2;
    }

    public void setMaara2(Integer maara2) {
        this.maara2 = maara2;
    }

    public void setAinesosa3(String ainesosa3) {
        this.ainesosa3 = ainesosa3;
    }

    public void setMaara3(Integer maara3) {
        this.maara3 = maara3;
    }

    public void setAinesosa4(String ainesosa4) {
        this.ainesosa4 = ainesosa4;
    }

    public void setMaara4(Integer maara4) {
        this.maara4 = maara4;
    }

    public void setAinesosa5(String ainesosa5) {
        this.ainesosa5 = ainesosa5;
    }

    public void setMaara5(Integer maara5) {
        this.maara5 = maara5;
    }

    public void setErrorViesti(String errorViesti) {
        this.errorViesti = errorViesti;
    }

    public String getErrorViesti() {
        return errorViesti;
    }

    public boolean onkoMaaraAinesosaOK(String ainesosa, Integer maara) {
        if (ainesosa == null && maara == null) {
            return true;
        }
        if (ainesosa != null && maara != null) {
            return true;
        }
         if (ainesosa.isEmpty() && maara == null) {
            return true;
        }
        if (ainesosa.isEmpty() && maara != null) {
            return true;
        }
        return false;
    }



    public String getDrinkki_name() {
        return drinkki_name;
    }

    public String getTyyppi_name() {
        return tyyppi_name;
    }

    public String getAinesosa_name() {
        return ainesosa_name;
    }

    public Integer getMaara() {
        return maara;
    }

    public void setDrinkki_name(String drinkki_name) {
        this.drinkki_name = drinkki_name;
    }

    public void setTyyppi_name(String tyyppi_name) {
        this.tyyppi_name = tyyppi_name;
    }

    public void setAinesosa_name(String ainesosa_name) {
        this.ainesosa_name = ainesosa_name;
    }

    public void setMaara(Integer maara) {
        this.maara = maara;
    }

    public abstract void tutkiVirheita();
}
