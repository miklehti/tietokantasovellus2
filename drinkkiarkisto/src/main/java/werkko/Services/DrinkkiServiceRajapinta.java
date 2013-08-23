/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import werkko.data.Ainesosa;
import werkko.data.Drinkki;
import werkko.data.DrinkkiAinesosa;
import werkko.data.DrinkkiLomake;
import werkko.data.Lomake;
import werkko.data.Tyyppi;

/**
 *
 * @author lehtimik
 */
public interface DrinkkiServiceRajapinta<T> {
         T create(T object);

    T read(String id);

    T update(T object);

    void delete(String id);

    List<T> list();
   String luoUusiDrinkki(String nimi, List<Tyyppi> tyyppi,List<DrinkkiAinesosa> drinkkiainesosa);
    HashMap<String, String> etsiDrinkkeja(String hakukriteeri);
    Drinkki haeDrinkkiNimella(String drink_name);
     TreeMap<String, String> annaDrinkitAakkosissa();
     HashMap<String, String> annaDrinkitTyypinMukaan(String tyyppi_nimi);
      HashMap<String, String> etsiDrinkkejaAdmin(String hakukriteeri);
      HashMap<String, String> annaEhdotukset();
      HashMap<String, String> annaDrinkitTyypinMukaanAdmin(String tyyppi_nimi);
      TreeMap<String, String> annaDrinkitAakkosissaAdmin();
       String annaLomakeVirheenTulkinta(String errorviesti, String field);
      
}
