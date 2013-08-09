/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import werkko.data.Tyyppi;
import werkko.repository.TyyppiRepositoryRajapinta;

/**
 *
 * @author lehtimik
 */
@Service
public class TyyppiService implements TyyppiServiceRajapinta<Tyyppi> {
    
    @Autowired
        private TyyppiRepositoryRajapinta<Tyyppi> tyyppiRepositoryRajapinta;

    public Tyyppi create(Tyyppi object) {
        return tyyppiRepositoryRajapinta.create(object);
    }

    public Tyyppi read(String id) {
        return tyyppiRepositoryRajapinta.read(id);
    }

    public Tyyppi update(Tyyppi object) {
        return tyyppiRepositoryRajapinta.update(object);
    }

    public void delete(String id) {
        tyyppiRepositoryRajapinta.delete(id);
     
    }

    public List<Tyyppi> list() {
       return tyyppiRepositoryRajapinta.list();
    }
    
    public Tyyppi annaTyyppiNimenPerusteella(String nimi){
        List<Tyyppi> lista = this.list();
        for (int i = 0; i<lista.size();i++){
        if(lista.get(i).getTyyppi_name().equals(nimi.toLowerCase())){
            return lista.get(i);
        }
    }
        return null;
    }
    
     public boolean onkoTyyppiJoOlemassa(String tyyppi) {
        List<Tyyppi> tyypit = this.list();
        for (int i = 0; i < tyypit.size(); i++) {
            if (tyypit.get(i).getTyyppi_name().equals(tyyppi.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Tyyppi> luoDrinkinTyyppi(String tyyppi_name){
        ArrayList<Tyyppi> tyyppi = new ArrayList<Tyyppi>();
        if (onkoTyyppiJoOlemassa(tyyppi_name) == false) {
            Tyyppi uusiTyyppi = new Tyyppi();
            uusiTyyppi.setTyyppi_name(tyyppi_name.toLowerCase());
            tyyppi.add(uusiTyyppi);
        } else {
            tyyppi.add(annaTyyppiNimenPerusteella(tyyppi_name));
        }
        return tyyppi;
    }
}
