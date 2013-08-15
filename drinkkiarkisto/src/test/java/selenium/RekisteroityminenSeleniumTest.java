/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author lehtimik
 */
public class RekisteroityminenSeleniumTest {

    private WebDriver driver;
    private String baseAddress;

    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
        this.baseAddress = "http://localhost:8080/drinkkiarkisto/app/rekisteroidy";
    }

//    @Test
//    public void rekisteroidy() {
//
//    }
}