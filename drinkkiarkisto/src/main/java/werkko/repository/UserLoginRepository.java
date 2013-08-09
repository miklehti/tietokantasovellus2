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
import werkko.data.*;
/**
 *
 * @author lehtimik
 */
@Repository
public class UserLoginRepository implements LoginRepository<UserLogin> {
    @PersistenceContext
    private EntityManager entityManager;
    
     @Transactional(readOnly = false)
    public UserLogin create(UserLogin object) {
        return entityManager.merge(object);
    }

 @Transactional(readOnly = true)
    public UserLogin read(String id) {
        return entityManager.find(UserLogin.class, id);
    }
    @Transactional(readOnly = false)
    public UserLogin update(UserLogin object) {
        return entityManager.merge(object);
    }
 @Transactional(readOnly = false)
    public void delete(String id) {
        Login craft = read(id);
        if(craft != null) {
            entityManager.remove(craft);
        }
    }
        @Transactional(readOnly = true)
     public List<UserLogin> list() {
        String queryString = "SELECT a FROM UserLogin a";
        Query query = entityManager.createQuery(queryString);
        List<UserLogin> messages = query.getResultList();
        return messages;
    }
}
