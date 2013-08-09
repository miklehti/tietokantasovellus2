/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.List;
import werkko.data.UserLogin;

/**
 *
 * @author lehtimik
 */
public interface LoginService<T> {

    T create(T object);

    T read(String id);

    T update(T object);

    void delete(String id);

    List<String> list();

    String loginOK(String username, String password);

    UserLogin getUserlogin();

    void setUserlogin(UserLogin userlogin);
      UserLogin annaUserLogin(String username);
}
