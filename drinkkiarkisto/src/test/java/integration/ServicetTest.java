/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import werkko.Services.AinesosaServiceRajapinta;
import werkko.Services.DrinkkiAinesosaServiceRajapinta;
import werkko.Services.DrinkkiServiceRajapinta;
import werkko.Services.TyyppiServiceRajapinta;
import werkko.data.Ainesosa;
import werkko.data.Drinkki;
import werkko.data.DrinkkiAinesosa;
import werkko.data.EhdotusLomake;
import werkko.data.Tyyppi;

/**
 *
 * @author lehtimik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/front-controller-servlet.xml"})
public class ServicetTest {

    @Autowired
    private AinesosaServiceRajapinta ainesosaservice;
    @Autowired
    private DrinkkiServiceRajapinta drinkkiservice;
    @Autowired
    private DrinkkiAinesosaServiceRajapinta drinkkiainesosaservice;
    @Autowired
    private TyyppiServiceRajapinta tyyppiservice;

    @Test
    public void haeTest() {
        HashMap<String, String> osoitteita = new HashMap<String, String>();
        osoitteita = drinkkiservice.etsiDrinkkeja("Gin");
        Assert.assertEquals(1, osoitteita.size());

        HashMap<String, String> osoitteita2 = new HashMap<String, String>();
        osoitteita2 = drinkkiservice.etsiDrinkkeja("gin");
        Assert.assertEquals(1, osoitteita2.size());

        HashMap<String, String> osoitteita3 = new HashMap<String, String>();
        osoitteita3 = drinkkiservice.etsiDrinkkeja("blaa");
        Assert.assertEquals(0, osoitteita3.size());
    }

    @Test
    public void drinkkiTest() {
        Drinkki drinkki = drinkkiservice.haeDrinkkiNimella("gin tonic");
        HashMap<String, Integer> ainesosa = ainesosaservice.annaAinesosatJaMaarat(drinkki);
        Assert.assertEquals(2, ainesosa.size());
        Assert.assertEquals("gin tonic", drinkki.getDrinkki_name());
        List<Tyyppi> tyyppi = drinkki.getTyypit();
        Assert.assertEquals("cocktaili", tyyppi.get(0).getTyyppi_name());
        Assert.assertTrue(ainesosa.containsKey("gin"));
        Assert.assertTrue(ainesosa.containsKey("tonic"));
        Assert.assertTrue(ainesosa.containsValue(2));
        Assert.assertTrue(ainesosa.containsValue(4));
         Drinkki drinkki2 = drinkkiservice.haeDrinkkiNimella("blaa");
          Assert.assertEquals(null, drinkki2);
    }
    
        @Test
    public void ehdotusTest() {
            EhdotusLomake ehdotuslomake = new EhdotusLomake();
            ehdotuslomake.setDrinkki_name("testi");
            
            ehdotuslomake.setAinesosa_name("rommi");
            ehdotuslomake.setMaara(3);
            
            ehdotuslomake.setAinesosa2("vesi");
            ehdotuslomake.setMaara2(5);
            
            ehdotuslomake.setAinesosa3("maito");
            ehdotuslomake.setMaara3(7);
            
            ehdotuslomake.setAinesosa4("vodka");
            ehdotuslomake.setMaara4(9);
            
            ehdotuslomake.setAinesosa5("gin");
            ehdotuslomake.setMaara5(11);
            
             ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(ehdotuslomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(ehdotuslomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(ehdotuslomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(ehdotuslomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            
          Assert.assertEquals("ok", viesti);
          
    }
}