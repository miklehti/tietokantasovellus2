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
import werkko.data.DrinkkiAinesosa;
import werkko.data.Login;
import werkko.data.DrinkkiAinesosa;

/**
 *
 * @author lehtimik
 */
@Repository
public class DrinkkiAinesosaRepository implements DrinkkiAinesosaRepositoryRajapinta<DrinkkiAinesosa> {
     @PersistenceContext
    private EntityManager entityManager;
     
       @Transactional(readOnly = false)
    public DrinkkiAinesosa create(DrinkkiAinesosa object) {
        return entityManager.merge(object);
    }

 @Transactional(readOnly = true)
    public DrinkkiAinesosa read(String id) {
        return entityManager.find(DrinkkiAinesosa.class, id);
    }
    @Transactional(readOnly = false)
    public DrinkkiAinesosa update(DrinkkiAinesosa object) {
        return entityManager.merge(object);
    }
 @Transactional(readOnly = false)
    public void delete(String id) {
        DrinkkiAinesosa craft = read(id);
        if(craft != null) {
            entityManager.remove(craft);
        }
    }
        @Transactional(readOnly = true)
     public List<DrinkkiAinesosa> list() {
        String queryString = "SELECT a FROM DrinkkiAinesosa a";
        Query query = entityManager.createQuery(queryString);
        List<DrinkkiAinesosa> messages = query.getResultList();
        return messages;
    }
}
