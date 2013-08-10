/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import werkko.Services.AinesosaServiceRajapinta;

import werkko.Services.DrinkkiAinesosaServiceRajapinta;

import werkko.Services.DrinkkiServiceRajapinta;
import werkko.Services.LoginService;
import werkko.Services.TyyppiServiceRajapinta;
import werkko.data.Ainesosa;
import werkko.data.Drinkki;
import werkko.data.DrinkkiAinesosa;
import werkko.data.DrinkkiLomake;
import werkko.data.EhdotusLomake;
import werkko.data.Lomake;
import werkko.data.Tyyppi;

import werkko.data.UserLogin;

/**
 *
 * @author lehtimik
 */
@Controller
public class DrinkkiController {

    @Autowired
    private LoginService loginservice;
    @Autowired
    private AinesosaServiceRajapinta ainesosaservice;
    @Autowired
    private DrinkkiServiceRajapinta drinkkiservice;
    @Autowired
    private DrinkkiAinesosaServiceRajapinta drinkkiainesosaservice;
    @Autowired
    private TyyppiServiceRajapinta tyyppiservice;

    @PostConstruct
    private void init() {
        UserLogin userlogin = new UserLogin();
        userlogin.setAuthority("admin");
        userlogin.setName("mikko");
        userlogin.setPassword("secret");
        userlogin.setEmail("mikko@mikko");
        loginservice.create(userlogin);

        Ainesosa gin = new Ainesosa();
        gin.setAinesosa_name("Gin");

        Ainesosa tonic = new Ainesosa();
        tonic.setAinesosa_name("Tonic");


        DrinkkiAinesosa drinkkiainesosa = new DrinkkiAinesosa();
        drinkkiainesosa.setMaara(4);
        drinkkiainesosa.setAinesosa(gin);

        DrinkkiAinesosa drinkkiainesosa2 = new DrinkkiAinesosa();
        drinkkiainesosa2.setMaara(2);
        drinkkiainesosa2.setAinesosa(tonic);

        ArrayList<DrinkkiAinesosa> drinkkiainesosat = new ArrayList<DrinkkiAinesosa>();

        drinkkiainesosat.add(drinkkiainesosa);
        drinkkiainesosat.add(drinkkiainesosa2);

        Tyyppi uusiTyyppi = new Tyyppi();
        uusiTyyppi.setTyyppi_name("cocktaili");
        ArrayList<Tyyppi> tyyppi = new ArrayList<Tyyppi>();
        tyyppi.add(uusiTyyppi);

        Drinkki gt = new Drinkki();
        gt.setTyypit(tyyppi);
        gt.setDrinkki_name("Gin Tonic");
        gt.setDrinkkiAinesosa(drinkkiainesosat);

        drinkkiservice.create(gt);

//
//        UserLogin userlogin2 = new UserLogin();
//        userlogin2.setAuthority("user");
//        userlogin2.setName("pekka");
//        userlogin2.setPassword("secret");
//        userlogin.setEmail("pekka@pekka");
//        loginservice.create(userlogin2);
    }

    public boolean onkoIstuntoVoimassa(HttpSession session) {
        String passwordKannassa = loginservice.getUserlogin().getPassword();
        String passwordSessiossa = (String) session.getAttribute("password");
        String usernameKannassa = loginservice.getUserlogin().getName();
        String usernameSessiossa = (String) session.getAttribute("username");
        if (passwordKannassa.equals(passwordSessiossa) && usernameKannassa.equals(usernameSessiossa)) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(ModelMap model) {

        return "login";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            HttpSession session) {

        String viesti = loginservice.loginOK(username, password);

        if (viesti.equals("ok")) {
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            return "redirect:haku";
        } else {
            session.setAttribute("nameError", viesti);
            return "login";
        }

    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpSession session) {
        session.invalidate();
        loginservice.setUserlogin(null);
        return "login";

    }

    private void lNaytetaankoYllapitoLinkki(Model model) {
        if (loginservice.getUserlogin().getAuthority().equals("admin")) {
            HashMap<String, String> yllapitolinkki = new HashMap<String, String>();
            yllapitolinkki.put("Yll‰pito", "http://localhost:8080/drinkkiarkisto/app/admin");
            model.addAttribute("yllapitolinkki", yllapitolinkki);

        }
    }

    private void naytetaankoLuoDrinkkiLinkki(Model model) {
        if (loginservice.getUserlogin().getAuthority().equals("admin") || loginservice.getUserlogin().getAuthority().equals("superuser")) {

            HashMap<String, String> luoDrinkki = new HashMap<String, String>();
            luoDrinkki.put("Luo Drinkki", "http://localhost:8080/drinkkiarkisto/app/luo-drinkki");
            model.addAttribute("luoDrinkki", luoDrinkki);

        }
    }

    @RequestMapping("haku")
    public String viewHakuPage(Model model, HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            lNaytetaankoYllapitoLinkki(model);
            naytetaankoLuoDrinkkiLinkki(model);

            return "haku";
        }

    }

    @RequestMapping(value = "hae", method = RequestMethod.POST)
    public String haeDrinkkeja(
            @RequestParam(value = "hae", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Haun tulokset:";
            HashMap<String, String> osoitteita = new HashMap<String, String>();
            osoitteita = drinkkiservice.etsiDrinkkeja(hae);
            hakuEiTuottanutTulosta(osoitteita, session);
            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", osoitteita);
            return "redirect:haku";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{drinkName}/drinkki")
    public String read(Model model, @PathVariable String drinkName, HttpSession session) {
        session.getAttribute("password");
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            naytetaankoTakaisinAdminSivulle(model);
            String sana = "Ainesosat:";
            Drinkki drinkki = drinkkiservice.haeDrinkkiNimella(drinkName);
            HashMap<String, Integer> ainesosa = ainesosaservice.annaAinesosatJaMaarat(drinkki);
            String drinkinNimi = drinkki.getDrinkki_name();
            model.addAttribute("drinkinNimi", drinkinNimi);
            model.addAttribute("sana", sana);
            model.addAttribute("ainesosa", ainesosa);
            return "drinkki";
        }
    }
    
        @RequestMapping(method = RequestMethod.GET, value = "{drinkName}/admindrinkki")
    public String katsoAdminDrinkkia(Model model, @PathVariable String drinkName, HttpSession session) {
        session.getAttribute("password");
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            naytetaankoTakaisinAdminSivulle(model);
            Drinkki drinkki = drinkkiservice.haeDrinkkiNimella(drinkName);
            List<DrinkkiAinesosa> drinkkiainesosat = drinkki.getDrinkkiAinesosa();
            model.addAttribute("drinkki_name", drinkki.getDrinkki_name());
            model.addAttribute("ainesosa_name", drinkkiainesosat.get(0).getAinesosa().getAinesosa_name());
            model.addAttribute("maara", drinkkiainesosat.get(0).getMaara());
            if (drinkkiainesosat.size() > 1) {
                model.addAttribute("ainesosa2", drinkkiainesosat.get(1).getAinesosa().getAinesosa_name());
                model.addAttribute("maara2", drinkkiainesosat.get(1).getMaara());
            }
            if (drinkkiainesosat.size() > 2) {
                model.addAttribute("ainesosa3", drinkkiainesosat.get(2).getAinesosa().getAinesosa_name());
                model.addAttribute("maara3", drinkkiainesosat.get(2).getMaara());
            }
            if (drinkkiainesosat.size() > 3) {
                model.addAttribute("ainesosa3", drinkkiainesosat.get(2).getAinesosa().getAinesosa_name());
                model.addAttribute("maara3", drinkkiainesosat.get(2).getMaara());
            }
            if (drinkkiainesosat.size() > 4) {
                model.addAttribute("ainesosa4", drinkkiainesosat.get(4).getAinesosa().getAinesosa_name());
                model.addAttribute("maara4", drinkkiainesosat.get(4).getMaara());
            }
            
            String value = "http://localhost:8080/drinkkiarkisto/app/" + drinkName + "/poista";
            model.addAttribute("poista", value);
            
             String paivita = "http://localhost:8080/drinkkiarkisto/app/" + drinkName + "/paivita";
            model.addAttribute("paivita", paivita);
            return "adminDrinkki";
        
  
        }
    }

    @RequestMapping(value = "rekisteroidy", method = RequestMethod.GET)
    public String viewRekisteroidy(@ModelAttribute("userlogin") UserLogin userlogin) {
        return "form";
    }

    @RequestMapping("ehdota")
    public String viewEhdota(Model model, HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            return "ehdota";
        }
    }

    @RequestMapping(value = "rekisteroidy", method = RequestMethod.POST)
    public String lisaaKayttajia(
            @Valid @ModelAttribute UserLogin userlogin,
            BindingResult bindingResult,
            @RequestParam(value = "password2", required = true) String password2,
            HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            ObjectError objecterror = errors.get(0);
            String nameError = objecterror.getDefaultMessage();
            session.setAttribute("name", userlogin.getName());
            session.setAttribute("email", userlogin.getEmail());
            session.setAttribute("nameError", nameError);

            return "form";
        }

        if (!userlogin.getPassword().equals(password2)) {
            String nameError = "Antamasi salasanat eiv‰t ole samoja";
            session.setAttribute("nameError", nameError);
            return "form";
        }

        userlogin.setAuthority("user");

        UserLogin uusiKayttaja = (UserLogin) loginservice.create(userlogin);

        if (uusiKayttaja.getStatus().equals("ok")) {
            session.setAttribute("username", userlogin.getName());
            session.setAttribute("password", userlogin.getPassword());
            loginservice.setUserlogin(uusiKayttaja);
            return "redirect:haku";
        } else {
            session.setAttribute("nameError", uusiKayttaja.getStatus());
            return "form";
        }


    }

    @RequestMapping(value = "ehdota-drinkkia", method = RequestMethod.POST)
    public String ehdotaDrinkkia(
            @Valid @ModelAttribute EhdotusLomake ehdotuslomake,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            if (bindingResult.hasErrors() || !ehdotuslomake.getErrorViesti().equals("")) {
                asetaVirheSessioon(ehdotuslomake, bindingResult, session);
                return "luoDrinkki";
            }
            ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(ehdotuslomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(ehdotuslomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(ehdotuslomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(ehdotuslomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                String onnistunutViesti = "Kiitos ehdotuksesta!";
                session.setAttribute("onnistunutViesti", onnistunutViesti);
                return "redirect:ehdota";
            } else {
                session.setAttribute("nameError", viesti);
                asetaArvotSessioon(ehdotuslomake, session);
                return "ehdota";
            }
        }

    }

    @RequestMapping(value = "hae-aakkoset", method = RequestMethod.POST)
    public String haeAakkosissa(
            @RequestParam(value = "hae-aakkoset", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Aakkosissa drinkkej‰:";
            TreeMap<String, String> osoitteita = drinkkiservice.annaDrinkitAakkosissa();
            hakuEiTuottanutTulosta(osoitteita, session);

            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", osoitteita);
            return "redirect:haku";
        }
    }

    @RequestMapping(value = "hae-tyyppi", method = RequestMethod.POST)
    public String haeTyyppi(
            @RequestParam(value = "hae-tyyppi", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Tyypin mukaan tulleita tuloksia:";
            HashMap<String, String> osoitteita = drinkkiservice.annaDrinkitTyypinMukaan(hae);

            hakuEiTuottanutTulosta(osoitteita, session);


            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", osoitteita);
            return "redirect:haku";
        }
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String adminView(ModelMap model, HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            session.removeAttribute("nameError");
            return "admin";
        }

    }

    @RequestMapping(value = "hae-admin", method = RequestMethod.POST)
    public String haeAdminDrinkkejaAdmin(
            @RequestParam(value = "hae-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Haun tulokset:";
            HashMap<String, String> admin_osoitteita = drinkkiservice.etsiDrinkkejaAdmin(hae);

            hakuEiTuottanutTulosta(admin_osoitteita, session);


            session.setAttribute("sana", sana);

            session.setAttribute("admin_osoitteita", admin_osoitteita);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "hae-ehdotuksia-admin", method = RequestMethod.POST)
    public String haeEhdotuksiaAdmin(
            @RequestParam(value = "hae-ehdotuksia-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Ehdotuksia:";
            HashMap<String, String> admin_osoitteita = drinkkiservice.annaEhdotukset();

            hakuEiTuottanutTulosta(admin_osoitteita, session);
            session.setAttribute("sana", sana);

            session.setAttribute("admin_osoitteita", admin_osoitteita);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "hae-tyyppi-admin", method = RequestMethod.POST)
    public String haeTyyppiAdmin(
            @RequestParam(value = "hae-tyyppi-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Tyypin mukaan tulleita tuloksia:";
            HashMap<String, String> admin_osoitteita = drinkkiservice.annaDrinkitTyypinMukaanAdmin(hae);


            hakuEiTuottanutTulosta(admin_osoitteita, session);

            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", admin_osoitteita);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "hae-aakkoset-admin", method = RequestMethod.POST)
    public String haeAakkosissaAdmin(
            @RequestParam(value = "hae-aakkoset-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Aakkosissa drinkkej‰:";
            TreeMap<String, String> osoitteita = drinkkiservice.annaDrinkitAakkosissaAdmin();

            hakuEiTuottanutTulosta(osoitteita, session);

            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", osoitteita);
            return "redirect:admin";
        }
    }

    public void asetaArvotSessioon(Lomake drinkkilomake, HttpSession session) {
        String drinkki_name = drinkkilomake.getDrinkki_name();
        session.setAttribute("drinkki_name", drinkki_name);

        String tyyppi_name = drinkkilomake.getTyyppi_name();
        session.setAttribute("tyyppi_name", tyyppi_name);

        String ainesosa_name = drinkkilomake.getAinesosa_name();
        session.setAttribute("ainesosa_name", ainesosa_name);

        Integer maara = drinkkilomake.getMaara();
        session.setAttribute("maara", maara);

        String ainesosa2 = drinkkilomake.getAinesosa2();
        session.setAttribute("ainesosa2", ainesosa2);

        Integer maara2 = drinkkilomake.getMaara2();
        session.setAttribute("maara2", maara2);

        String ainesosa3 = drinkkilomake.getAinesosa3();
        session.setAttribute("ainesosa3", ainesosa3);

        Integer maara3 = drinkkilomake.getMaara3();
        session.setAttribute("maara3", maara3);

        String ainesosa4 = drinkkilomake.getAinesosa4();
        session.setAttribute("ainesosa4", ainesosa4);

        Integer maara4 = drinkkilomake.getMaara4();
        session.setAttribute("maara4", maara4);

        String ainesosa5 = drinkkilomake.getAinesosa5();
        session.setAttribute("ainesosa5", ainesosa5);

        Integer maara5 = drinkkilomake.getMaara5();
        session.setAttribute("maara5", maara5);
    }

    public void asetaVirheSessioon(Lomake drinkkilomake, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            ObjectError objecterror = errors.get(0);
            String nameError = objecterror.getDefaultMessage();
            session.setAttribute("nameError", nameError);
            asetaArvotSessioon(drinkkilomake, session);
            return;
        }
        asetaArvotSessioon(drinkkilomake, session);
        String nameError = drinkkilomake.getErrorViesti();
        session.setAttribute("nameError", nameError);

    }

    @RequestMapping(value = "luo-drinkki", method = RequestMethod.POST)
    public String luoDrinkki(
            @Valid @ModelAttribute DrinkkiLomake drinkkilomake,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            if (bindingResult.hasErrors() || !drinkkilomake.getErrorViesti().equals("")) {
                asetaVirheSessioon(drinkkilomake, bindingResult, session);
                return "luoDrinkki";
            }
            ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(drinkkilomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(drinkkilomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(drinkkilomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(drinkkilomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                String onnistunutViesti = "Uusi drinkki luotu tietokantaan";
                session.setAttribute("onnistunutViesti", onnistunutViesti);
                return "redirect:luo-drinkki";
            } else {

                session.setAttribute("nameError", viesti);
                asetaArvotSessioon(drinkkilomake, session);
                return "luoDrinkki";
            }


        }


    }

    @RequestMapping(value = "hae-kayttaja", method = RequestMethod.POST)
    public String haeKayttaja(
            @RequestParam(value = "hae-kayttaja", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "K‰ytt‰ji‰:";
            HashMap<String, String> kayttajia = new HashMap<String, String>();
            List<String> users = loginservice.list();

            for (int i = 0; i < users.size(); i++) {
                String key = users.get(i);
                String value = "http://localhost:8080/drinkkiarkisto/app/" + key + "/kayttaja";
                kayttajia.put(key, value);

            }

            session.setAttribute("sana", sana);

            session.setAttribute("kayttajia", kayttajia);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "poista-kayttaja", method = RequestMethod.POST)
    public String poistaKayttaja(
            @RequestParam(value = "poista-kayttaja", required = false) String hae,
            HttpSession session, UserLogin userlogin, Model model) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        }
        UserLogin username = (UserLogin) session.getAttribute("userlogin");
        if (username.getAuthority().equals("admin")) {

            session.setAttribute("nameError", "admin k‰ytt‰j‰‰ ei voi poistaa");
            session.setAttribute("usernameKanta", username.getName());
            session.setAttribute("authorityKanta", username.getAuthority());
            session.setAttribute("idKanta", username.getId());
            session.setAttribute("statusKanta", username.getStatus());
            session.setAttribute("passwordKanta", username.getPassword());
            session.setAttribute("emailKanta", username.getEmail());
            return "kayttaja";
        }
        loginservice.delete(username.getId());
        session.removeAttribute("kayttajia");
        return "redirect:admin";

    }

    @RequestMapping(value = "ylenna-kayttaja", method = RequestMethod.POST)
    public String ylennaKayttaja(
            @RequestParam(value = "ylenna-kayttaja", required = false) String hae,
            HttpSession session, UserLogin userlogin, Model model) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        }
        UserLogin username = (UserLogin) session.getAttribute("userlogin");
        if (username.getAuthority().equals("admin") || username.getAuthority().equals("superuser")) {

            session.setAttribute("nameError", "vain user k‰ytt‰j‰n voi ylent‰‰ super useriksi");
            session.setAttribute("authorityKanta", username.getAuthority());
            session.setAttribute("usernameKanta", username.getName());
            session.setAttribute("idKanta", username.getId());
            session.setAttribute("statusKanta", username.getStatus());
            session.setAttribute("passwordKanta", username.getPassword());
            session.setAttribute("emailKanta", username.getEmail());
            return "kayttaja";
        }

        username.setAuthority("superuser");
        loginservice.update(username);
        session.removeAttribute("kayttajia");
        return "redirect:admin";

    }

    @RequestMapping(method = RequestMethod.GET, value = "{ehdotusName}/ehdotus")
    public String readEhdotus(Model model, @PathVariable String ehdotusName, HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            Drinkki ehdotus = drinkkiservice.haeDrinkkiNimella(ehdotusName);
            List<DrinkkiAinesosa> drinkkiainesosat = ehdotus.getDrinkkiAinesosa();
            model.addAttribute("drinkki_name", ehdotus.getDrinkki_name());
            model.addAttribute("ainesosa_name", drinkkiainesosat.get(0).getAinesosa().getAinesosa_name());
            model.addAttribute("maara", drinkkiainesosat.get(0).getMaara());
            if (drinkkiainesosat.size() > 1) {
                model.addAttribute("ainesosa2", drinkkiainesosat.get(1).getAinesosa().getAinesosa_name());
                model.addAttribute("maara2", drinkkiainesosat.get(1).getMaara());
            }
            if (drinkkiainesosat.size() > 2) {
                model.addAttribute("ainesosa3", drinkkiainesosat.get(2).getAinesosa().getAinesosa_name());
                model.addAttribute("maara3", drinkkiainesosat.get(2).getMaara());
            }
            if (drinkkiainesosat.size() > 3) {
                model.addAttribute("ainesosa3", drinkkiainesosat.get(2).getAinesosa().getAinesosa_name());
                model.addAttribute("maara3", drinkkiainesosat.get(2).getMaara());
            }
            if (drinkkiainesosat.size() > 4) {
                model.addAttribute("ainesosa4", drinkkiainesosat.get(4).getAinesosa().getAinesosa_name());
                model.addAttribute("maara4", drinkkiainesosat.get(4).getMaara());
            }
            
            String value = "http://localhost:8080/drinkkiarkisto/app/" + ehdotusName + "/poista";
            model.addAttribute("poista", value);
            return "ehdotus";
        }
    }
    
     @RequestMapping(value = "luo-drinkki-ehdotuksesta", method = RequestMethod.POST)
    public String luoDrinkkiEhdotuksesta(
            @Valid @ModelAttribute DrinkkiLomake drinkkilomake,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            if (bindingResult.hasErrors() || !drinkkilomake.getErrorViesti().equals("")) {
                asetaVirheSessioon(drinkkilomake, bindingResult, session);
                return "ehdotus";
            }
            ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(drinkkilomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(drinkkilomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(drinkkilomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(drinkkilomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                String onnistunutViesti = "Uusi drinkki luotu tietokantaan";
                session.setAttribute("onnistunutViesti", onnistunutViesti);
                return "redirect:luo-drinkki-ehdotuksesta";
            } else {

                session.setAttribute("nameError", viesti);
                asetaArvotSessioon(drinkkilomake, session);
                return "ehdotus";
            }


        }


    }
     
     @RequestMapping(value = "{poista}/hylkaa", method = RequestMethod.POST)
    public String poistaEhdotus(
            Model model, @PathVariable String ehdotusName, HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            Drinkki poistettava = drinkkiservice.haeDrinkkiNimella(ehdotusName);
            drinkkiservice.delete(poistettava.getDrinkki_id());
            String onnistunutViesti = "Ehdotus poistettu";
                session.setAttribute("onnistunutViesti", onnistunutViesti);
                return "admin";
            }


        


    }
     
        @RequestMapping(value = "{paivita}/paivita", method = RequestMethod.POST)
    public String paivitaEhdotus(
            Model model, @PathVariable String ehdotusName, HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            Drinkki paivitettava = drinkkiservice.haeDrinkkiNimella(ehdotusName);
            drinkkiservice.update(paivitettava.getDrinkki_id());
            String onnistunutViesti = "Tiedot p‰ivitetty";
                session.setAttribute("onnistunutViesti", onnistunutViesti);
                return "ehdotus";
            }


        


    }
     
     


    @RequestMapping(method = RequestMethod.GET, value = "{kayttajaName}/kayttaja")
    public String readKayttaja(Model model, @PathVariable String kayttajaName, HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            UserLogin userlogin = loginservice.annaUserLogin(kayttajaName);
            session.setAttribute("userlogin", userlogin);
            model.addAttribute("usernameKanta", userlogin.getName());
            model.addAttribute("authorityKanta", userlogin.getAuthority());
            model.addAttribute("idKanta", userlogin.getId());
            model.addAttribute("statusKanta", userlogin.getStatus());
            model.addAttribute("passwordKanta", userlogin.getPassword());
            model.addAttribute("emailKanta", userlogin.getEmail());
            return "kayttaja";
        }
    }

    @RequestMapping(value = "luo-drinkki", method = RequestMethod.GET)
    public String luoDrinkki(ModelMap model, HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "login";
        } else {
            if (loginservice.getUserlogin().getAuthority().equals("admin")) {
                HashMap<String, String> admin = new HashMap<String, String>();
                admin.put("<-takaisin admin sivulle", "http://localhost:8080/drinkkiarkisto/app/admin");
                model.addAttribute("admin", admin);

            }
            session.removeAttribute("nameError");
            return "luoDrinkki";
        }

    }

    @RequestMapping(value = "tulostus", method = RequestMethod.GET)
    public String tulostus(ModelMap model, HttpSession session) {
        List<Drinkki> drinkit = drinkkiservice.list();
        HashMap<String, String> drinkki_id = new HashMap<String, String>();
        for (int i = 0; i < drinkit.size(); i++) {
            drinkki_id.put("drinkki" + i, drinkit.get(i).getDrinkki_id());
        }
        HashMap<String, String> drinkki_name = new HashMap<String, String>();
        for (int i = 0; i < drinkit.size(); i++) {
            drinkki_name.put("drinkki_nimi" + i, drinkit.get(i).getDrinkki_name());
        }
        session.setAttribute("drinkki_id", drinkki_id);
        session.setAttribute("drinkki_name", drinkki_name);

        HashMap<String, Integer> drinkkiAinesosa = new HashMap<String, Integer>();
        for (int i = 0; i < drinkit.size(); i++) {

            List<DrinkkiAinesosa> drinkkiainesosat = drinkit.get(i).getDrinkkiAinesosa();
            for (int j = 0; j < drinkkiainesosat.size(); j++) {
                drinkkiAinesosa.put("drinkkiAinesosa" + j, drinkkiainesosat.get(j).getMaara());
            }

        }
        session.setAttribute("drinkkiAinesosa", drinkkiAinesosa);
        //***********************************
        List<DrinkkiAinesosa> drinkkiainesosa = drinkkiainesosaservice.list();
        HashMap<String, Integer> drinkkiAinesosa_maara = new HashMap<String, Integer>();
        for (int i = 0; i < drinkkiainesosa.size(); i++) {
            drinkkiAinesosa_maara.put("drinkkiAinesosa" + i, drinkkiainesosa.get(i).getMaara());
        }
        session.setAttribute("drinkkiAinesosa_maara", drinkkiAinesosa_maara);
        //***********************************
        List<Ainesosa> aineet = ainesosaservice.list();

        HashMap<String, String> ainesosa_id = new HashMap<String, String>();
        for (int i = 0; i < aineet.size(); i++) {
            ainesosa_id.put("ainesosa_id" + i, aineet.get(i).getAinesosa_id());
        }
        session.setAttribute("ainesosa_id", ainesosa_id);

        HashMap<String, String> ainesosa_name = new HashMap<String, String>();
        for (int i = 0; i < aineet.size(); i++) {
            ainesosa_name.put("ainesosa_name" + i, aineet.get(i).getAinesosa_name());
        }
        session.setAttribute("ainesosa_name", ainesosa_name);
        //***********************************

        List<Tyyppi> tyyppi = tyyppiservice.list();
        HashMap<String, String> tyyppi_id = new HashMap<String, String>();
        for (int i = 0; i < tyyppi.size(); i++) {
            tyyppi_id.put("tyyppi_id" + i, tyyppi.get(i).getTyyppi_id());
        }
        session.setAttribute("tyyppi_id", tyyppi_id);

        HashMap<String, String> tyyppi_name = new HashMap<String, String>();
        for (int i = 0; i < tyyppi.size(); i++) {
            tyyppi_name.put("tyyppi_name" + i, tyyppi.get(i).getTyyppi_name());
        }
        session.setAttribute("tyyppi_name", tyyppi_name);
        return "tulostus";

    }

    private void hakuEiTuottanutTulosta(Map<String, String> osoitteita, HttpSession session) {
        if (osoitteita.isEmpty()) {
            String eiLoydy = "Haku ei tuottanut yht‰‰n tulosta";
            session.setAttribute("eiLoydy", eiLoydy);
        }
    }

    private void naytetaankoTakaisinAdminSivulle(Model model) {
        if (loginservice.getUserlogin().getAuthority().equals("admin")) {
            HashMap<String, String> admin = new HashMap<String, String>();
            admin.put("<-takaisin admin sivulle", "http://localhost:8080/drinkkiarkisto/app/admin");
            model.addAttribute("admin", admin);

        }
    }
}
