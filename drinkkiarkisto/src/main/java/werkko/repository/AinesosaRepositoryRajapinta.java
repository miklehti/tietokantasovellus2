/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.repository;

import java.util.List;

/**
 *
 * @author lehtimik
 */
public interface AinesosaRepositoryRajapinta<T> {
         T create(T object);
    T read(String id);
    T update(T object);
    void delete(String id);
    List<T> list();
}
