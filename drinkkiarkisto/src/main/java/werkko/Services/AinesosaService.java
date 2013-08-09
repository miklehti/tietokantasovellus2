/*
 * Ainesosao change this template, choose Ainesosaools | Ainesosaemplates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import werkko.data.Ainesosa;
import werkko.data.Drinkki;
import werkko.data.DrinkkiAinesosa;
import werkko.data.Lomake;
import werkko.repository.AinesosaRepositoryRajapinta;

/**
 *
 * @author lehtimik
 */
@Service
public class AinesosaService implements AinesosaServiceRajapinta<Ainesosa> {

    @Autowired
    private AinesosaRepositoryRajapinta<Ainesosa> ainesosaRepositoryRajapinta;

    public Ainesosa create(Ainesosa object) {
        return ainesosaRepositoryRajapinta.create(object);

    }

    public Ainesosa read(String id) {
        return ainesosaRepositoryRajapinta.read(id);
    }

    public Ainesosa update(Ainesosa object) {
        return ainesosaRepositoryRajapinta.update(object);
    }

    public void delete(String id) {
        ainesosaRepositoryRajapinta.delete(id);
    }

    public List<Ainesosa> list() {
        return ainesosaRepositoryRajapinta.list();
    }

    public HashMap<String, Integer> annaAinesosatJaMaarat(Drinkki drinkki) {
        HashMap<String, Integer> ainesosat = new HashMap<String, Integer>();
        List<DrinkkiAinesosa> drinkkiainesosa = drinkki.getDrinkkiAinesosa();
        for (int i = 0; i < drinkkiainesosa.size(); i++) {
            String lisattavaNimi = drinkkiainesosa.get(i).getAinesosa().getAinesosa_name();
            Integer maara = drinkkiainesosa.get(i).getMaara();
            ainesosat.put(lisattavaNimi, maara);
        }
        return ainesosat;
    }

    public Ainesosa annaAinesosaNimenPerusteella(String nimi) {
        List<Ainesosa> ainesosat = this.list();
        for (int i = 0; i < ainesosat.size(); i++) {
            if (ainesosat.get(i).getAinesosa_name().equals(nimi.toLowerCase())) {
                return ainesosat.get(i);
            }

        }
        return null;
    }

    public boolean onkoAinesosaOlemassa(String ainesosa_name) {
        List<Ainesosa> ainesosat = this.list();
        for (int i = 0; i < ainesosat.size(); i++) {
            if (ainesosat.get(i).getAinesosa_name().equals(ainesosa_name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Ainesosa> luoDrinkinAinesosat(Lomake lomake) {
        ArrayList<Ainesosa> ainesosat = new ArrayList<Ainesosa>();

        if (onkoAinesosaOlemassa(lomake.getAinesosa_name()) == false) {
            Ainesosa paa_ainesosa = new Ainesosa();
            paa_ainesosa.setAinesosa_name(lomake.getAinesosa_name().toLowerCase());
            ainesosat.add(paa_ainesosa);
        } else {
            Ainesosa paa_ainesosa = annaAinesosaNimenPerusteella(lomake.getAinesosa_name());
            ainesosat.add(paa_ainesosa);
        }

        if (!lomake.getAinesosa2().isEmpty()) {
            if (onkoAinesosaOlemassa(lomake.getAinesosa2()) == false) {
                Ainesosa a2 = new Ainesosa();
                a2.setAinesosa_name(lomake.getAinesosa2().toLowerCase());
                ainesosat.add(a2);

            } else {
                Ainesosa a2 = annaAinesosaNimenPerusteella(lomake.getAinesosa2());
                ainesosat.add(a2);
            }
        }
         if (!lomake.getAinesosa3().isEmpty()) {
            if (onkoAinesosaOlemassa(lomake.getAinesosa3()) == false) {
                Ainesosa a3 = new Ainesosa();
                a3.setAinesosa_name(lomake.getAinesosa2().toLowerCase());
                ainesosat.add(a3);

            } else {
                Ainesosa a3 = annaAinesosaNimenPerusteella(lomake.getAinesosa3());
                ainesosat.add(a3);
            }
        }
          if (!lomake.getAinesosa4().isEmpty()) {
            if (onkoAinesosaOlemassa(lomake.getAinesosa4()) == false) {
                Ainesosa a4 = new Ainesosa();
                a4.setAinesosa_name(lomake.getAinesosa4().toLowerCase());
                ainesosat.add(a4);

            } else {
                Ainesosa a4 = annaAinesosaNimenPerusteella(lomake.getAinesosa2());
                ainesosat.add(a4);
            }
        }
           if (!lomake.getAinesosa5().isEmpty()) {
            if (onkoAinesosaOlemassa(lomake.getAinesosa5()) == false) {
                Ainesosa a5 = new Ainesosa();
                a5.setAinesosa_name(lomake.getAinesosa2().toLowerCase());
                ainesosat.add(a5);

            } else {
                Ainesosa a5 = annaAinesosaNimenPerusteella(lomake.getAinesosa2());
                ainesosat.add(a5);
            }
        }
       return ainesosat;
    }
    

}
