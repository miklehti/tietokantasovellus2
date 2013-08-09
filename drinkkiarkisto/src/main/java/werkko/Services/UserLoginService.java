/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.Services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werkko.data.UserLogin;
import werkko.repository.LoginRepository;

/**
 *
 * @author lehtimik
 */
@Service
public class UserLoginService implements LoginService<UserLogin> {

    @Autowired
    private LoginRepository<UserLogin> loginrepository;
    private UserLogin userlogin;

    public UserLogin getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(UserLogin userlogin) {
        this.userlogin = userlogin;
    }

    @Transactional(readOnly = false)
    public UserLogin create(UserLogin object) {
        if (onkoKayttajaaOlemassa(object.getName())) {
            UserLogin userlogin = new UserLogin();
            userlogin.setStatus("Käyttäjä on jo olemassa");
            return userlogin;
        }
        UserLogin luotavaTunnus = new UserLogin();
        luotavaTunnus = loginrepository.create(object);
        luotavaTunnus.setStatus("ok");
        return luotavaTunnus;
    }

    @Transactional(readOnly = true)
    public UserLogin read(String id) {
        return loginrepository.read(id);
    }

    @Transactional(readOnly = true)
    public UserLogin update(UserLogin object) {
        return loginrepository.update(object);
    }

    @Transactional(readOnly = true)
    public void delete(String id) {
        loginrepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<String> list() {
        List<UserLogin> users = loginrepository.list();
        ArrayList<String> kayttajat = new ArrayList<String>();
        for (int i = 0; i < users.size(); i++) {
            kayttajat.add(users.get(i).getName());
        }
        return kayttajat;


    }

    @Transactional(readOnly = true)
    public String loginOK(String username, String password) {

        if (onkoKayttajaaOlemassa(username)) {
            if (onkoSalasanaOikea(username, password)) {
                this.setUserlogin(annaUserLogin(username));
                return "ok";
            }
            return "Väärä salasana";
        }
        return "käyttäjää ei löytynyt";
    }

    public boolean onkoKayttajaaOlemassa(String username) {
        List<UserLogin> users = loginrepository.list();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean onkoSalasanaOikea(String username, String password) {
        List<UserLogin> users = loginrepository.list();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(username)) {
                if (users.get(i).getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public UserLogin annaUserLogin(String username) {
        List<UserLogin> users = loginrepository.list();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(username)) {
                return users.get(i);
            }
        }
        UserLogin userlogin = new UserLogin();
        userlogin.setStatus("Käyttäjää ei löydy");
        return userlogin;
    }
}
