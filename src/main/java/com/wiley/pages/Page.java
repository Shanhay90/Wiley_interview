package com.wiley.pages;


import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class Page {

    public static WebDriver driver;

    protected WebDriverWait webDriverWait;

    protected Actions actionHelper;

    public Page() {
        webDriverWait = new WebDriverWait(driver, 5);
        actionHelper = new Actions(driver);
    }

    public static void initDriver(){
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
    }

    public static void initDriver(DriverManagerType drivers){
        if (driver == null) {
            WebDriverManager.getInstance(drivers).setup();
            switch (drivers) {
                case CHROME:
                    driver = new ChromeDriver();
                    break;
                case FIREFOX:
                    driver = new FirefoxDriver();
                    break;
                case OPERA:
                    driver = new OperaDriver();
                    break;
                case IEXPLORER:
                    driver = new InternetExplorerDriver();
                    break;
                case EDGE:
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Select Webdriver type");
            }
        }
    }


    public static WebDriver getDriver() {
        return driver;
    }

    public void waitUntilVisible (WebElement element){
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void moveToElement(WebElement element){
        actionHelper.moveToElement(element).perform();
    }


    public void checkLinks (List<WebElement> links, List<String> names){
        List <String> linksText  = links.stream()
                .map(link -> link.getAttribute("outerText").trim().toLowerCase())
                .collect(Collectors.toList());
        names.forEach(name ->
                Assert.assertTrue(String.format("Link with text: '%s', not displayed", name ),linksText.contains(name.toLowerCase()))
        );
    }

    public WebElement findLink (List<WebElement> links, String name){


        return links
                .stream()
                .filter(link -> link.getAttribute("outerText").trim().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("Link with text: '%s', not displayed", name )));
    }


    public boolean checkElementHRef (WebElement element, String href){
        String elemetHref = element.getAttribute("href");
        return elemetHref.equals(href);
    }
}
