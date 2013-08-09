/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import werkko.data.Ainesosa;
import werkko.data.Drinkki;
import werkko.data.Lomake;

/**
 *
 * @author lehtimik
 */
public interface AinesosaServiceRajapinta<T> {
     T create(T object);

    T read(String id);

    T update(T object);

    void delete(String id);

    List<T> list();
    public HashMap<String,Integer> annaAinesosatJaMaarat(Drinkki drinkki);
    Ainesosa annaAinesosaNimenPerusteella(String nimi);
    ArrayList<Ainesosa> luoDrinkinAinesosat(Lomake lomake);
}
