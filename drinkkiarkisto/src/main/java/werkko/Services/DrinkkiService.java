/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import werkko.data.Ainesosa;
import werkko.data.Drinkki;
import werkko.data.DrinkkiAinesosa;
import werkko.data.DrinkkiLomake;
import werkko.data.Lomake;
import werkko.data.Tyyppi;

import werkko.repository.DrinkkiRepositoryRajapinta;

/**
 *
 * @author lehtimik
 */
@Service
public class DrinkkiService implements DrinkkiServiceRajapinta<Drinkki> {

    @Autowired
    private DrinkkiRepositoryRajapinta<Drinkki> drinkkiAinesosaRepositoryRajapinta;

    public DrinkkiService() {
    }

    public Drinkki create(Drinkki object) {
        return drinkkiAinesosaRepositoryRajapinta.create(object);
    }

    public Drinkki read(String id) {
        return drinkkiAinesosaRepositoryRajapinta.read(id);
    }

    public Drinkki update(Drinkki object) {
        return drinkkiAinesosaRepositoryRajapinta.update(object);
    }

    public void delete(String id) {
        drinkkiAinesosaRepositoryRajapinta.delete(id);

    }

    public List<Drinkki> list() {
        return drinkkiAinesosaRepositoryRajapinta.list();
    }

    public boolean onkoSamanNiminenDrinkkiJoOlemassa(String drinkinNimi) {
        List<Drinkki> lista = this.list();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDrinkki_name().equals(drinkinNimi)) {
                return true;
            }

        }
        return false;
    }

    public String luoUusiDrinkki(String nimi, List<Tyyppi> tyyppi, List<DrinkkiAinesosa> drinkkiainesosa) {

        if (onkoSamanNiminenDrinkkiJoOlemassa(nimi) == false) {
            Drinkki luotavaDrinkki = new Drinkki();
            luotavaDrinkki.setDrinkki_name(nimi);
            luotavaDrinkki.setTyypit(tyyppi);
            luotavaDrinkki.setDrinkkiAinesosa(drinkkiainesosa);
            create(luotavaDrinkki);
            return "ok";
        }
        return "Antamasi drinkki on jo olemassa!";
    }

    public void poistaDrinkitJoissaTyyppiOnEhdotus(List<Drinkki> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Drinkki tutkittavaDrinkki = lista.get(i);
            List<Tyyppi> tutkittavanDrinkinTyypit = tutkittavaDrinkki.getTyypit();

            for (int j = 0; j < tutkittavanDrinkinTyypit.size(); j++) {
                if (tutkittavanDrinkinTyypit.get(j).getTyyppi_name().equals("ehdotus")) {
                    lista.remove(new Integer(i));
                }

            }
        }
    }

    public HashMap<String, String> etsiDrinkkeja(String hakukriteeri) {
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        List<Drinkki> lista = this.list();
        poistaDrinkitJoissaTyyppiOnEhdotus(lista);
        for (int i = 0; i < lista.size(); i++) {
            String tutkittava = lista.get(i).getDrinkki_name().toLowerCase();
            if (tutkittava.contains(hakukriteeri.toLowerCase())) {
                String osoite = lista.get(i).getDrinkki_name().trim();
                drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/drinkki");
            }
        }
        return drinkkeja;
    }

    public HashMap<String, String> annaEhdotukset() {
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        List<Drinkki> lista = this.list();
        for (int i = 0; i < lista.size(); i++) {
            Drinkki tutkittava = lista.get(i);
            List<Tyyppi> tutkittavanDrinkinTyypit = tutkittava.getTyypit();
            for (int j = 0; j < tutkittavanDrinkinTyypit.size(); j++) {
                if (tutkittavanDrinkinTyypit.get(j).getTyyppi_name().equals("ehdotus")) {
                    String osoite = lista.get(i).getDrinkki_name().trim();
                    drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/ehdotus");
                }
            }
        }

        return drinkkeja;
    }

    public HashMap<String, String> etsiDrinkkejaAdmin(String hakukriteeri) {
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        List<Drinkki> lista = this.list();
        poistaDrinkitJoissaTyyppiOnEhdotus(lista);
        for (int i = 0; i < lista.size(); i++) {
            String tutkittava = lista.get(i).getDrinkki_name().toLowerCase();
            if (tutkittava.contains(hakukriteeri.toLowerCase())) {

                String osoite = lista.get(i).getDrinkki_name().trim();
                drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/admindrinkki");
            }        
    }
    return drinkkeja ;
}
public Drinkki haeDrinkkiNimella(String drink_name) {
        List<Drinkki> lista = this.list();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDrinkki_name().equals(drink_name)) {
                return lista.get(i);
            }
        }
        return null;
    

}

    class MyComparator implements Comparator<String> {

    public int compare(String o1, String o2) {
        // Your logic for comparing the key strings
        return o1.toLowerCase().compareTo(o2.toLowerCase());
    }
}
public TreeMap<String, String> annaDrinkitAakkosissa() {
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        List<Drinkki> lista = this.list();
         poistaDrinkitJoissaTyyppiOnEhdotus(lista);
        for (int i = 0; i < lista.size(); i++) {
            String osoite = lista.get(i).getDrinkki_name().trim();
            //  String uusiOsoite = osoite.replace(" ", "");
            drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/drinkki");
        }
        TreeMap<String, String> aakkosissaDrinkit = new TreeMap<String, String>(new MyComparator());
        aakkosissaDrinkit.putAll(drinkkeja);
        return aakkosissaDrinkit;
    }

    public HashMap<String, String> annaDrinkitTyypinMukaan(String tyyppi_nimi) {
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        if(tyyppi_nimi==null){
            return drinkkeja;
        }
        
        List<Drinkki> lista = this.list();
        for (int i = 0; i < lista.size(); i++) {
            Drinkki tutkittava = lista.get(i);
            List<Tyyppi> tutkittavanDrinkinTyypit = tutkittava.getTyypit();
            for (int j = 0; j < tutkittavanDrinkinTyypit.size(); j++) {
                if (tutkittavanDrinkinTyypit.get(j).getTyyppi_name().equals(tyyppi_nimi.toLowerCase())) {
                    String osoite = lista.get(i).getDrinkki_name().trim();
                    drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/drinkki");
                }
            }
        }

        return drinkkeja;
    }
    public  HashMap<String, String> annaDrinkitTyypinMukaanAdmin(String tyyppi_nimi){
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        if(tyyppi_nimi==null){
            return drinkkeja;
        }
        List<Drinkki> lista = this.list();
        for (int i = 0; i < lista.size(); i++) {
            Drinkki tutkittava = lista.get(i);
            List<Tyyppi> tutkittavanDrinkinTyypit = tutkittava.getTyypit();
            for (int j = 0; j < tutkittavanDrinkinTyypit.size(); j++) {
                if (tutkittavanDrinkinTyypit.get(j).getTyyppi_name().equals(tyyppi_nimi.toLowerCase())) {
                    String osoite = lista.get(i).getDrinkki_name().trim();
                    drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/admindrinkki");
                }
            }
        }

        return drinkkeja;
    }
    
    public TreeMap<String, String> annaDrinkitAakkosissaAdmin() {
        HashMap<String, String> drinkkeja = new HashMap<String, String>();
        List<Drinkki> lista = this.list();
         poistaDrinkitJoissaTyyppiOnEhdotus(lista);
        for (int i = 0; i < lista.size(); i++) {
            String osoite = lista.get(i).getDrinkki_name().trim();
            //  String uusiOsoite = osoite.replace(" ", "");
            drinkkeja.put(lista.get(i).getDrinkki_name(), "http://localhost:8080/drinkkiarkisto/app/" + osoite + "/admindrinkki");
        }
        TreeMap<String, String> aakkosissaDrinkit = new TreeMap<String, String>(new MyComparator());
        aakkosissaDrinkit.putAll(drinkkeja);
        return aakkosissaDrinkit;
    }
}
