import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import page.classes.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Bojan on 3/7/2015.
 */
public class ClickableLinks {
    private WebDriver driver;
    private String baseURL;

    @Before
    public void before() throws Exception {
        driver = new FirefoxDriver();
        baseURL = "http://www.expedia.com/";
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void findClickableLinks() throws Exception {
        driver.get(baseURL);
        driver.findElement(By.id("tab-flight-tab")).click();
        Thread.sleep(2000);

        List<WebElement> linksList = clickableLinks();
        for (WebElement link:linksList) {
            String hrefLink = link.getAttribute("href");
//            Primer za novi objekat iz klase URL
//            URL url = new URL("http://www.android.com/");
//            Zato sam u zagradi stavio "hrefLink", jer on sadrzi URL-ove linkova na koje moze da se klikne
            URL newUrl = new URL(hrefLink);

            try {
                System.out.println("URL: " + hrefLink + " status is: " + linkStatus(newUrl));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Number of links is: " + linksList.size());
    }

    public List<WebElement> clickableLinks() throws Exception {
        List<WebElement> linksToClick = new ArrayList<WebElement>();
        List<WebElement> allElements = new ArrayList<WebElement>();
        List<WebElement> elements_a = driver.findElements(By.tagName("a"));
        List<WebElement> elements_img = driver.findElements(By.tagName("img"));
//        U "ArrayListu" pod imenom "allElements" dodajemo sve elemente sa tagName "a"
        allElements.addAll(elements_a);
//        U "ArrayListu" pod imenom "allElements" pored svih elemenata sa tagName "a",
//        sto smo uradili u prethodnom koraku, dodajemo sve elemente sa tagName "img".
        allElements.addAll(elements_img);

        for (WebElement elements:allElements) {
            if (elements.getAttribute("href") != null){
                linksToClick.add(elements);
            }
        }
        return linksToClick;
    }

    public static String linkStatus(URL url) {
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();

            String responseMessage = http.getResponseMessage();
            http.disconnect();
            return responseMessage;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(6000);
        driver.quit();
    }
}
