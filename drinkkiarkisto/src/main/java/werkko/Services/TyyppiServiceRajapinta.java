/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.List;
import werkko.data.Tyyppi;

/**
 *
 * @author lehtimik
 */
public interface TyyppiServiceRajapinta<T> {
             T create(T object);

    T read(String id);

    T update(T object);

    void delete(String id);

    List<T> list();
    Tyyppi annaTyyppiNimenPerusteella(String nimi);
    ArrayList<Tyyppi> luoDrinkinTyyppi(String tyyppi_name);
}
