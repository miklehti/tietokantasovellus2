/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import werkko.data.Drinkki;
import werkko.data.Login;
import werkko.data.Drinkki;

/**
 *
 * @author lehtimik
 */
@Repository
public class DrinkkiRepository implements DrinkkiRepositoryRajapinta<Drinkki>{
     @PersistenceContext
    private EntityManager entityManager;
     
       @Transactional(readOnly = false)
    public Drinkki create(Drinkki object) {
        return entityManager.merge(object);
    }

 @Transactional(readOnly = true)
    public Drinkki read(String id) {
        return entityManager.find(Drinkki.class, id);
    }
    @Transactional(readOnly = false)
    public Drinkki update(Drinkki object) {
        return entityManager.merge(object);
    }
 @Transactional(readOnly = false)
    public void delete(String id) {
        Drinkki craft = read(id);
        if(craft != null) {
            entityManager.remove(craft);
        }
    }
        @Transactional(readOnly = true)
     public List<Drinkki> list() {
        String queryString = "SELECT a FROM Drinkki a";
        Query query = entityManager.createQuery(queryString);
        List<Drinkki> messages = query.getResultList();
        return messages;
    }
}
