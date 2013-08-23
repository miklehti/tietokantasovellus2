/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.List;
import werkko.data.Ainesosa;
import werkko.data.DrinkkiAinesosa;
import werkko.data.Lomake;

/**
 *
 * @author lehtimik
 */
public interface DrinkkiAinesosaServiceRajapinta<T> {
         T create(T object);

    T read(String id);

    T update(T object);

    void delete(String id);

    List<T> list();
    public ArrayList<DrinkkiAinesosa> luoDrinkinDrinkkiainesosat (Lomake lomake, ArrayList<Ainesosa> ainesosat);
    boolean onkoAinesosaDrinkkiAinesosassa(Ainesosa ainesosa);
}
