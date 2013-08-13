/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import werkko.Services.LoginService;
import werkko.Services.UserLoginService;
import werkko.data.UserLogin;

/**
 *
 * @author lehtimik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/front-controller-servlet.xml"})
public class Login {

    @Autowired
    LoginService userloginservice;

    @Test
    public void testaaOnkoAdminOikeinKannassaTest() {
        Assert.assertEquals("mikko", userloginservice.annaUserLogin("mikko").getName());
        Assert.assertEquals("secret", userloginservice.annaUserLogin("mikko").getPassword());
        Assert.assertEquals("admin", userloginservice.annaUserLogin("mikko").getAuthority());
    }

    @Test
    public void luoUusiKayttajaTest() {
        UserLogin userlogin = new UserLogin();
        userlogin.setName("pekka");
        userlogin.setPassword("secret2");
        userlogin.setEmail("a@a");
        userlogin.setAuthority("user");
        UserLogin uusiKayttaja = (UserLogin) userloginservice.create(userlogin);
        Assert.assertEquals("pekka", userloginservice.annaUserLogin("pekka").getName());
        Assert.assertEquals("secret2", userloginservice.annaUserLogin("pekka").getPassword());
        Assert.assertEquals("user", userloginservice.annaUserLogin("pekka").getAuthority());
        Assert.assertEquals(2, userloginservice.list().size());
    }

    @Test
    public void testaaVaariaSalasanojaJaKayttajanimiaTest() {
        String vastaus = userloginservice.loginOK("mikko", "secret");
        Assert.assertEquals("ok", vastaus);

        vastaus = userloginservice.loginOK("pekka", "secret2");
        Assert.assertEquals("ok", vastaus);
        
        vastaus = userloginservice.loginOK("pekka", "secret");
        Assert.assertEquals("Väärä salasana", vastaus);
        
           vastaus = userloginservice.loginOK("mikko", "secret2");
        Assert.assertEquals("Väärä salasana", vastaus);
        
            vastaus = userloginservice.loginOK("pekka", "Secret2");
        Assert.assertEquals("Väärä salasana", vastaus);
        
           vastaus = userloginservice.loginOK("mikko", "Secret");
        Assert.assertEquals("Väärä salasana", vastaus);
        
          vastaus = userloginservice.loginOK("mikko2", "secret");
        Assert.assertEquals("Käyttäjää ei löytynyt", vastaus);
        
          vastaus = userloginservice.loginOK("pekka2", "secret2");
        Assert.assertEquals("Käyttäjää ei löytynyt", vastaus);
        
           vastaus = userloginservice.loginOK("Mikko", "secret");
        Assert.assertEquals("Käyttäjää ei löytynyt", vastaus);
        
          vastaus = userloginservice.loginOK("Pekka", "secret2");
        Assert.assertEquals("Käyttäjää ei löytynyt", vastaus);

    }

    @Test
    public void paivitaKayttajaTest() {
        UserLogin userlogin = userloginservice.annaUserLogin("pekka");
        userlogin.setAuthority("superuser");
        userlogin.setEmail("b@b");
        userlogin.setPassword("secret3");
        userloginservice.update(userlogin);
        Assert.assertEquals("b@b", userloginservice.annaUserLogin("pekka").getEmail());
        Assert.assertEquals("secret3", userloginservice.annaUserLogin("pekka").getPassword());
        Assert.assertEquals("superuser", userloginservice.annaUserLogin("pekka").getAuthority());
        Assert.assertEquals(2, userloginservice.list().size());
    }

    @Test
    public void poistaKayttajaTest() {
        UserLogin poistettava = userloginservice.annaUserLogin("pekka");
        userloginservice.delete(poistettava.getId());
        Assert.assertEquals(1, userloginservice.list().size());
    }
}