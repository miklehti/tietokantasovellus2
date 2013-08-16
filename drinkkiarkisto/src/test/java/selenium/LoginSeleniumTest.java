/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import com.google.common.base.Throwables;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author lehtimik
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class LoginSeleniumTest {

    private WebDriver driver;
    private String baseAddress;
    private static ChromeDriverService service;

    public LoginSeleniumTest() {
    }

    @BeforeClass
    public static void createAndStartService() {
        service = new ChromeDriverService.Builder()
                .usingChromeDriverExecutable(new File("C:\\Drivers\\chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        service.stop();
    }

    @Before
    public void setUp() {
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        this.baseAddress = "localhost:8080/drinkkiarkisto";

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void LoginSuccessChrome() {


        // Go to the Google Suggest home page
        driver.get(baseAddress);

        // haetaan kentt‰ nimelt‰ tunnus
        WebElement element = driver.findElement(By.name("username"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mikko");

        // haetaan kentt‰ nimelt‰ tunnus
        element = driver.findElement(By.name("password"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret");

        // l‰hetet‰‰n lomake
        element.submit();

        // haetaan kentt‰ nimelt‰ "age"
        element = driver.findElement(By.name("hae-tyyppi"));

        Assert.assertNotNull("Element \"age\" not found.", element);

    }

    @Test
    public void LoginFailureChrome() {


        // Go to the Google Suggest home page
        driver.get(baseAddress);

        // haetaan kentt‰ nimelt‰ tunnus
        WebElement element = driver.findElement(By.name("username"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mikko2");

        // haetaan kentt‰ nimelt‰ tunnus
        element = driver.findElement(By.name("password"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret");

        // l‰hetet‰‰n lomake
        element.submit();

        // haetaan kentt‰ nimelt‰ "age"
        boolean loytyyko = driver.getPageSource().contains("K‰ytt‰j‰‰ ei lˆytynyt");


        Assert.assertTrue(loytyyko);

    }

    @Test
    public void LoginFailure2Chrome() {


        // Go to the Google Suggest home page
        driver.get(baseAddress);

        // haetaan kentt‰ nimelt‰ tunnus
        WebElement element = driver.findElement(By.name("username"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mikko");

        // haetaan kentt‰ nimelt‰ tunnus
        element = driver.findElement(By.name("password"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret2");

        // l‰hetet‰‰n lomake
        element.submit();

        // haetaan kentt‰ nimelt‰ "age"
        boolean loytyyko = driver.getPageSource().contains("V‰‰r‰ salasana");


        Assert.assertTrue(loytyyko);

    }

    @Test
    public void rekisteroidy2ChromeTest() {
        driver.get("http://localhost:8080/drinkkiarkisto/app/rekisteroidy");

        // haetaan kentt‰ nimelt‰ tunnus
        WebElement element = driver.findElement(By.name("name"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mirkku");

        element = driver.findElement(By.name("password"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret");

        element = driver.findElement(By.name("password2"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret");

        element = driver.findElement(By.name("email"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mirkku@mirkku");

        // l‰hetet‰‰n lomake
        element.submit();

        // haetaan kentt‰ nimelt‰ "age"
        boolean loytyyko = driver.getPageSource().contains("Tervetuloa uusi k‰ytt‰j‰!");

        Assert.assertTrue(loytyyko);
        driver.navigate().to("http://localhost:8080/drinkkiarkisto/app/ehdota");
        driver.navigate().to("http://localhost:8080/drinkkiarkisto/app/hae");
        loytyyko = driver.getPageSource().contains("Tervetuloa uusi k‰ytt‰j‰!");
        Assert.assertFalse(loytyyko);


    }
    
        @Test
    public void rekisteroidyVirheHerjatChromeTest() {
        driver.get("http://localhost:8080/drinkkiarkisto/app/rekisteroidy");

        // haetaan kentt‰ nimelt‰ tunnus
        WebElement element = driver.findElement(By.name("name"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mirkku");

        element = driver.findElement(By.name("password"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret");

        element = driver.findElement(By.name("password2"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret2");

        element = driver.findElement(By.name("email"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mirkku@mirkku");

        // l‰hetet‰‰n lomake
        element.submit();

        // haetaan kentt‰ nimelt‰ "age"
        boolean loytyyko = driver.getPageSource().contains("Antamasi salasanat eiv‰t ole samoja");

        Assert.assertTrue(loytyyko);        


    }
         @Test
    public void rekisteroidyVirheHerjat2ChromeTest() {
        driver.get("http://localhost:8080/drinkkiarkisto/app/rekisteroidy");

        // haetaan kentt‰ nimelt‰ tunnus
        WebElement element = driver.findElement(By.name("name"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mirkku");

        element = driver.findElement(By.name("password"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret");

        element = driver.findElement(By.name("password2"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("secret2");

        element = driver.findElement(By.name("email"));

        // asetetaan kentt‰‰n arvo
        element.sendKeys("mirkku@mirkku");

        // l‰hetet‰‰n lomake
        element.submit();

        // haetaan kentt‰ nimelt‰ "age"
        boolean loytyyko = driver.getPageSource().contains("Antamasi salasanat eiv‰t ole samoja");

        Assert.assertTrue(loytyyko);        


    }
}
