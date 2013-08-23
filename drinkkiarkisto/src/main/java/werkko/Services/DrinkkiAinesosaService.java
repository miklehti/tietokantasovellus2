/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import werkko.data.Ainesosa;
import werkko.data.DrinkkiAinesosa;
import werkko.data.Lomake;
import werkko.repository.AinesosaRepositoryRajapinta;
import werkko.repository.DrinkkiAinesosaRepositoryRajapinta;

/**
 *
 * @author lehtimik
 */
@Service
public class DrinkkiAinesosaService implements DrinkkiAinesosaServiceRajapinta<DrinkkiAinesosa> {

    @Autowired
    private DrinkkiAinesosaRepositoryRajapinta<DrinkkiAinesosa> drinkkiAinesosaRepositoryRajapinta;

    public DrinkkiAinesosa create(DrinkkiAinesosa object) {
        return drinkkiAinesosaRepositoryRajapinta.create(object);
    }

    public DrinkkiAinesosa read(String id) {
        return drinkkiAinesosaRepositoryRajapinta.read(id);
    }

    public DrinkkiAinesosa update(DrinkkiAinesosa object) {
        return drinkkiAinesosaRepositoryRajapinta.update(object);
    }

    public void delete(String id) {
        drinkkiAinesosaRepositoryRajapinta.delete(id);;
    }

    public List<DrinkkiAinesosa> list() {
        return drinkkiAinesosaRepositoryRajapinta.list();
    }

    public ArrayList<DrinkkiAinesosa> luoDrinkinDrinkkiainesosat(Lomake lomake, ArrayList<Ainesosa> ainesosat) {
        ArrayList<DrinkkiAinesosa> drinkkiainesosat = new ArrayList<DrinkkiAinesosa>();
        DrinkkiAinesosa drinkkiainesosa = new DrinkkiAinesosa();
        drinkkiainesosa.setMaara(lomake.getMaara());
        drinkkiainesosa.setAinesosa(ainesosat.get(0));
        drinkkiainesosat.add(drinkkiainesosa);
        if(ainesosat.size()>1){
            DrinkkiAinesosa drinkkiainesosa2 = new DrinkkiAinesosa();
        drinkkiainesosa2.setMaara(lomake.getMaara2());
        drinkkiainesosa2.setAinesosa(ainesosat.get(1));
        drinkkiainesosat.add(drinkkiainesosa2);
        }
         if(ainesosat.size()>2){
            DrinkkiAinesosa drinkkiainesosa3 = new DrinkkiAinesosa();
        drinkkiainesosa3.setMaara(lomake.getMaara3());
        drinkkiainesosa3.setAinesosa(ainesosat.get(2));
        drinkkiainesosat.add(drinkkiainesosa3);
        }
          if(ainesosat.size()>3){
            DrinkkiAinesosa drinkkiainesosa4 = new DrinkkiAinesosa();
        drinkkiainesosa4.setMaara(lomake.getMaara4());
        drinkkiainesosa4.setAinesosa(ainesosat.get(3));
        drinkkiainesosat.add(drinkkiainesosa4);
        }
           if(ainesosat.size()>4){
            DrinkkiAinesosa drinkkiainesosa5 = new DrinkkiAinesosa();
        drinkkiainesosa5.setMaara(lomake.getMaara5());
        drinkkiainesosa5.setAinesosa(ainesosat.get(4));
        drinkkiainesosat.add(drinkkiainesosa5);
        }
           return drinkkiainesosat;
    }
    
    public boolean onkoAinesosaDrinkkiAinesosassa(Ainesosa ainesosa){
        List<DrinkkiAinesosa> drinkkiainesosat = list();
        for(int i = 0; i<drinkkiainesosat.size();i++){
            if(drinkkiainesosat.get(i).getAinesosa().getAinesosa_id().equals(ainesosa.getAinesosa_id())){
                return true;
            }
        }
        return false;
    }
}
