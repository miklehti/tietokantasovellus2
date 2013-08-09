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
import werkko.data.Ainesosa;
import werkko.data.Login;
import werkko.data.Ainesosa;

/**
 *
 * @author lehtimik
 */
@Repository
public class AinesosaRepository implements AinesosaRepositoryRajapinta<Ainesosa>{
     @PersistenceContext
    private EntityManager entityManager;
     
       @Transactional(readOnly = false)
    public Ainesosa create(Ainesosa object) {
        return entityManager.merge(object);
    }

 @Transactional(readOnly = true)
    public Ainesosa read(String id) {
        return entityManager.find(Ainesosa.class, id);
    }
    @Transactional(readOnly = false)
    public Ainesosa update(Ainesosa object) {
        return entityManager.merge(object);
    }
 @Transactional(readOnly = false)
    public void delete(String id) {
        Ainesosa craft = read(id);
        if(craft != null) {
            entityManager.remove(craft);
        }
    }
        @Transactional(readOnly = true)
     public List<Ainesosa> list() {
        String queryString = "SELECT a FROM Ainesosa a";
        Query query = entityManager.createQuery(queryString);
        List<Ainesosa> messages = query.getResultList();
        return messages;
    }
}
