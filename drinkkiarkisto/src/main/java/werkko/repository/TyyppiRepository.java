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
import werkko.data.Tyyppi;

/**
 *
 * @author lehtimik
 */
@Repository
public class TyyppiRepository implements TyyppiRepositoryRajapinta<Tyyppi>{
          @PersistenceContext
    private EntityManager entityManager;
     
       @Transactional(readOnly = false)
    public Tyyppi create(Tyyppi object) {
        return entityManager.merge(object);
    }

 @Transactional(readOnly = true)
    public Tyyppi read(String id) {
        return entityManager.find(Tyyppi.class, id);
    }
    @Transactional(readOnly = false)
    public Tyyppi update(Tyyppi object) {
        return entityManager.merge(object);
    }
 @Transactional(readOnly = false)
    public void delete(String id) {
        Tyyppi craft = read(id);
        if(craft != null) {
            entityManager.remove(craft);
        }
    }
        @Transactional(readOnly = true)
     public List<Tyyppi> list() {
        String queryString = "SELECT a FROM Tyyppi a";
        Query query = entityManager.createQuery(queryString);
        List<Tyyppi> messages = query.getResultList();
        return messages;
    }
}
