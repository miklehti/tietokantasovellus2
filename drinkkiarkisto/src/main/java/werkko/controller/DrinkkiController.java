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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        UserLogin userlogin2 = new UserLogin();
        userlogin2.setAuthority("user");
        userlogin2.setName("lasse");
        userlogin2.setPassword("secret");
        userlogin2.setEmail("lasse@lasse");
        loginservice.create(userlogin2);

        UserLogin userlogin3 = new UserLogin();
        userlogin3.setAuthority("superuser");
        userlogin3.setName("jouni");
        userlogin3.setPassword("secret");
        userlogin3.setEmail("jouni@jouni");
        loginservice.create(userlogin3);

        Ainesosa gin = new Ainesosa();
        gin.setAinesosa_name("gin");

        Ainesosa tonic = new Ainesosa();
        tonic.setAinesosa_name("tonic");


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
        gt.setDrinkki_name("gin tonic");
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

        String viesti = loginservice.loginOK(StringEscapeUtils.escapeHtml4(username), StringEscapeUtils.escapeHtml4(password));

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
            yllapitolinkki.put("Yll�pito", "http://localhost:8080/drinkkiarkisto/app/admin");
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
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Haun tulokset:";
            HashMap<String, String> osoitteita = new HashMap<String, String>();
            osoitteita = drinkkiservice.etsiDrinkkeja(StringEscapeUtils.escapeHtml4(hae));
            hakuEiTuottanutTulosta(osoitteita, redirectAttributes);
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
            List<Tyyppi> tyypit = drinkki.getTyypit();
            model.addAttribute("drinkinNimi", drinkinNimi);
            model.addAttribute("sana", sana);
            model.addAttribute("ainesosa", ainesosa);
            model.addAttribute("tyyppi", tyypit.get(0).getTyyppi_name());
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
             List<Tyyppi> tyypit = drinkki.getTyypit();
            List<DrinkkiAinesosa> drinkkiainesosat = drinkki.getDrinkkiAinesosa();
            model.addAttribute("id", drinkki.getDrinkki_id());
            model.addAttribute("tyyppi_name", tyypit.get(0).getTyyppi_name());
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
            session.setAttribute("nameError", "");
            return "ehdota";
        }
    }

    @RequestMapping(value = "rekisteroidy", method = RequestMethod.POST)
    public String lisaaKayttajia(
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute UserLogin userlogin,
            BindingResult bindingResult,
            @RequestParam(value = "password2", required = true) String password2,
            HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            ObjectError objecterror = errors.get(0);
            String errorviesti = objecterror.getDefaultMessage();
            List<FieldError> fielderrors = bindingResult.getFieldErrors();
            FieldError field = fielderrors.get(0);
            String kentta = field.getField();
            String nameError = loginservice.annaVirheenTulkinta(errorviesti, kentta);
            session.setAttribute("name", userlogin.getName());
            Object[] objects = objecterror.getArguments();

            session.setAttribute("email", userlogin.getEmail());
            session.setAttribute("nameError", nameError);

            return "form";
        }

        if (!userlogin.getPassword().equals(password2)) {
            String nameError = "Antamasi salasanat eiv�t ole samoja";
            session.setAttribute("nameError", nameError);
            return "form";
        }

        userlogin.setAuthority("user");

        UserLogin uusiKayttaja = (UserLogin) loginservice.create(userlogin);

        if (uusiKayttaja.getStatus().equals("ok")) {
            session.setAttribute("username", userlogin.getName());
            session.setAttribute("password", userlogin.getPassword());
            loginservice.setUserlogin(uusiKayttaja);
            redirectAttributes.addFlashAttribute("viesti", "Tervetuloa uusi k�ytt�j�!");

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
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            ehdotuslomake.tutkiVirheita();
            if (bindingResult.hasErrors() || !ehdotuslomake.getErrorViesti().equals("")) {
                asetaVirheSessioon(ehdotuslomake, bindingResult, session);
                return "ehdota";
            }
            ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(ehdotuslomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(ehdotuslomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(ehdotuslomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(ehdotuslomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                redirectAttributes.addFlashAttribute("viesti", "Kiitos ehdotuksesta!");
                session.setAttribute("nameError", "");
                return "redirect:ehdota";
            } else {
                session.setAttribute("nameError", "Ehdottamallasi nimell�  on jo drinkki olemassa");
                asetaArvotSessioon(ehdotuslomake, session);
                return "ehdota";
            }
        }

    }

    @RequestMapping(value = "hae-aakkoset", method = RequestMethod.POST)
    public String haeAakkosissa(
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae-aakkoset", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Aakkosissa drinkkej�:";
            TreeMap<String, String> osoitteita = drinkkiservice.annaDrinkitAakkosissa();
            hakuEiTuottanutTulosta(osoitteita, redirectAttributes);

            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", osoitteita);
            return "redirect:haku";
        }
    }

    @RequestMapping(value = "hae-tyyppi", method = RequestMethod.POST)
    public String haeTyyppi(
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae-tyyppi", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Tyypin mukaan tulleita tuloksia:";
            HashMap<String, String> osoitteita = drinkkiservice.annaDrinkitTyypinMukaan(hae);

            hakuEiTuottanutTulosta(osoitteita, redirectAttributes);


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
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Haun tulokset:";
            HashMap<String, String> admin_osoitteita = drinkkiservice.etsiDrinkkejaAdmin(hae);

            hakuEiTuottanutTulosta(admin_osoitteita, redirectAttributes);


            session.setAttribute("sana", sana);

            session.setAttribute("admin_osoitteita", admin_osoitteita);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "hae-ehdotuksia-admin", method = RequestMethod.POST)
    public String haeEhdotuksiaAdmin(
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae-ehdotuksia-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Ehdotuksia:";
            HashMap<String, String> ehdotuksia = drinkkiservice.annaEhdotukset();

            if (ehdotuksia.isEmpty()) {
                redirectAttributes.addFlashAttribute("eiEhdotuksia", "Ei yht��n ehdotusta t�ll� hetkell�");
            }
            session.setAttribute("sana", sana);

            session.setAttribute("ehdotuksia", ehdotuksia);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "hae-tyyppi-admin", method = RequestMethod.POST)
    public String haeTyyppiAdmin(
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae-tyyppi-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Tyypin mukaan tulleita tuloksia:";
            HashMap<String, String> admin_osoitteita = drinkkiservice.annaDrinkitTyypinMukaanAdmin(hae);


            hakuEiTuottanutTulosta(admin_osoitteita, redirectAttributes);

            session.setAttribute("sana", sana);

            session.setAttribute("osoitteita", admin_osoitteita);
            return "redirect:admin";
        }
    }

    @RequestMapping(value = "hae-aakkoset-admin", method = RequestMethod.POST)
    public String AakkosissaAdmin(
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "hae-aakkoset-admin", required = false) String hae,
            HttpSession session) {

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            String sana = "Aakkosissa drinkkej�:";
            TreeMap<String, String> admin_osoitteita = drinkkiservice.annaDrinkitAakkosissaAdmin();

            hakuEiTuottanutTulosta(admin_osoitteita, redirectAttributes);

            session.setAttribute("sana", sana);

            session.setAttribute("admin_osoitteita", admin_osoitteita);
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
            String errorviesti = objecterror.getDefaultMessage();

            List<FieldError> fielderrors = bindingResult.getFieldErrors();
            FieldError field = fielderrors.get(0);
            String kentta = field.getField();
            String nameError = drinkkiservice.annaLomakeVirheenTulkinta(errorviesti, kentta);
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
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute DrinkkiLomake drinkkilomake,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            drinkkilomake.tutkiVirheita();
            if (bindingResult.hasErrors() || !drinkkilomake.getErrorViesti().equals("")) {
                asetaVirheSessioon(drinkkilomake, bindingResult, session);
                return "luoDrinkki";
            }
            ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(drinkkilomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(drinkkilomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(drinkkilomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(drinkkilomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                redirectAttributes.addFlashAttribute("onnistunutViesti", "Uusi drinkki luotu tietokantaan!");
                session.setAttribute("nameError", "");
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
            String sana = "K�ytt�ji�:";
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

            session.setAttribute("nameError", "admin k�ytt�j�� ei voi poistaa");
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

            session.setAttribute("nameError", "vain user k�ytt�j�n voi ylent�� super useriksi");
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
            model.addAttribute("id", ehdotus.getDrinkki_id());
            model.addAttribute("drinkki_name", ehdotus.getDrinkki_name());
            model.addAttribute("ainesosa_name", drinkkiainesosat.get(0).getAinesosa().getAinesosa_name());
            model.addAttribute("maara", drinkkiainesosat.get(0).getMaara());
            model.addAttribute("tyyppi_name", "");
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

            String value = "http://localhost:8080/drinkkiarkisto/app/" + ehdotusName + "/hylkaa";
            model.addAttribute("poista", value);
            return "ehdotus";
        }
    }

    @RequestMapping(value = "luo-drinkki-ehdotuksesta", method = RequestMethod.POST)
    public String luoDrinkkiEhdotuksesta(
            @RequestParam(required = false, value = "Hyv�ksy") String hyvaksy,
            @RequestParam(required = false, value = "Hylk��") String hylkaa,
            @RequestParam(required = false, value = "id") String id,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute DrinkkiLomake drinkkilomake,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {
        Drinkki alkuperainenEhdotus = (Drinkki) drinkkiservice.read(id);

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {

            if (hylkaa != null) {
                 poistaDrinkki(redirectAttributes, id, session, alkuperainenEhdotus);
                          redirectAttributes.addFlashAttribute("onnistunutViesti", "Ehdotus poistettu");
            session.removeAttribute("ehdotuksia");
            return "redirect:admin";
            } else {
                if (drinkkilomake.getTyyppi_name().equals("ehdotus")) {
                    session.setAttribute("nameError", "tyyppi ei voi olla ehdotus");
                    return "ehdotus";
                }
                drinkkilomake.tutkiVirheita();
                if (bindingResult.hasErrors() || !drinkkilomake.getErrorViesti().equals("")) {

                    asetaVirheSessioon(drinkkilomake, bindingResult, session);
                    model.addAttribute("id", alkuperainenEhdotus.getDrinkki_id());
                    return "ehdotus";
                }

                poistaDrinkki(redirectAttributes, id, session, alkuperainenEhdotus);


                ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(drinkkilomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(drinkkilomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(drinkkilomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(drinkkilomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                redirectAttributes.addFlashAttribute("onnistunutViesti", "Uusi drinkki luotu tietokantaan!");
                session.setAttribute("nameError", "");
                session.setAttribute("ehdotuksia", "");
                return "redirect:admin";
            } else {

                session.setAttribute("nameError", viesti);
                asetaArvotSessioon(drinkkilomake, session);
                return "ehdotus";
            }

               


            }
        }


    }

    

    

    public void poistaDrinkki(
            RedirectAttributes redirectAttributes,
            String id, HttpSession session, Drinkki drinkki) {


        {
            //poista tyyppi jos ainut
            HashMap<String, String> tyypinEsiintyminen = drinkkiservice.annaDrinkitTyypinMukaan(drinkki.getTyypit().get(0).getTyyppi_name());
            if (tyypinEsiintyminen.size() == 1) {
                tyyppiservice.delete(drinkki.getTyypit().get(0).getTyyppi_id());
            }
            //poista drinkki
            drinkkiservice.delete(id);
            //poista drinkkiainesosat
            List<DrinkkiAinesosa> drinkkiainesosat = drinkki.getDrinkkiAinesosa();
            int koko = drinkkiainesosat.size();
            for (int i = 0; i < koko; i++) {
                drinkkiainesosaservice.delete(drinkkiainesosat.get(i).getDrinkkiainesosa_id());
            }
            //poista ainesosat, eli jos drinkin ainesosa id ei drinkkiainesosassa voi poistaa

            List<Ainesosa> ainesosat = ainesosaservice.list();
            int ainesosaKoko = ainesosat.size();
            for (int i = 0; i < ainesosaKoko; i++) {
                if (drinkkiainesosaservice.onkoAinesosaDrinkkiAinesosassa(ainesosat.get(i)) == false) {
                    ainesosaservice.delete(ainesosat.get(i).getAinesosa_id());
                }
            }

   
        }



    }
    
     @RequestMapping(value = "paivita-poista-drinkki", method = RequestMethod.POST)
    public String paivitaPoistaDrinkki(
            @RequestParam(required = false, value = "Tallenna") String hyvaksy,
            @RequestParam(required = false, value = "Poista") String hylkaa,
            @RequestParam(required = false, value = "id") String id,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute DrinkkiLomake drinkkilomake,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {
        Drinkki alkuperainenEhdotus = (Drinkki) drinkkiservice.read(id);

        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {

            if (hylkaa != null) {
                 poistaDrinkki(redirectAttributes, id, session, alkuperainenEhdotus);
                          redirectAttributes.addFlashAttribute("onnistunutViesti", "Drinkki poistettu");
            session.removeAttribute("admin_osoitteita");
            return "redirect:admin";
            } else {
                if (drinkkilomake.getTyyppi_name().equals("ehdotus")) {
                    session.setAttribute("nameError", "tyyppi ei voi olla ehdotus");
                    return "adminDrinkki";
                }
                drinkkilomake.tutkiVirheita();
                if (bindingResult.hasErrors() || !drinkkilomake.getErrorViesti().equals("")) {

                    asetaVirheSessioon(drinkkilomake, bindingResult, session);
                    model.addAttribute("id", alkuperainenEhdotus.getDrinkki_id());
                    return "adminDrinkki";
                }

                poistaDrinkki(redirectAttributes, id, session, alkuperainenEhdotus);


                ArrayList<Tyyppi> tyypit = tyyppiservice.luoDrinkinTyyppi(drinkkilomake.getTyyppi_name());
            ArrayList<Ainesosa> ainesosat = ainesosaservice.luoDrinkinAinesosat(drinkkilomake);
            ArrayList<DrinkkiAinesosa> drinkkiainesosat = drinkkiainesosaservice.luoDrinkinDrinkkiainesosat(drinkkilomake, ainesosat);
            String viesti = drinkkiservice.luoUusiDrinkki(drinkkilomake.getDrinkki_name(), tyypit, drinkkiainesosat);
            if (viesti.equals("ok")) {
                redirectAttributes.addFlashAttribute("onnistunutViesti", "Muutokset tallennettu tietokantaan!");
                session.setAttribute("nameError", "");
                return "redirect:admin";
            } else {

                session.setAttribute("nameError", viesti);
                asetaArvotSessioon(drinkkilomake, session);
                return "adminDrinkki";
            }

               


            }
        }


    }

    @RequestMapping(value = "{paivita}/paivita", method = RequestMethod.POST)
    public String paivitaEhdotus(
            RedirectAttributes redirectAttributes,
            Model model, @PathVariable String ehdotusName, HttpSession session) {


        if (onkoIstuntoVoimassa(session) == false) {
            return "redirect:login";
        } else {
            Drinkki paivitettava = drinkkiservice.haeDrinkkiNimella(ehdotusName);
            drinkkiservice.update(paivitettava.getDrinkki_id());
            redirectAttributes.addFlashAttribute("onnistunutViesti", "Tiedot p�ivitetty");

            return "redirect:admin";
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

    private void hakuEiTuottanutTulosta(Map<String, String> osoitteita, RedirectAttributes redirectAttributes) {
        if (osoitteita.isEmpty()) {
            redirectAttributes.addFlashAttribute("eiLoydy", "Haku ei tuottanut yht��n tulosta");

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
