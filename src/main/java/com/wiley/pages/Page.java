package com.wiley.pages;


import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public abstract class Page {

    public static WebDriver driver;

    protected WebDriverWait webDriverWait;

    protected Actions actionHelper;

    public Page() {
        webDriverWait = new WebDriverWait(driver, 5);
        actionHelper = new Actions(driver);
    }


    /** Header for all Pages
     *  1. Header links
     *  2. input field
     *  3. search widget
     */

    /**
     * 1. Page headers elements and methods
     */
    @FindBy(xpath = "//div[contains(@class, 'navbar-static')]//img[@title = 'Wiley']")
    private WebElement headerLogo;

    @FindBys(@FindBy(xpath = "//ul[@class= 'navigation-menu-items']/li"))
    private List<WebElement> headerLinks;

    private List<WebElement> underHeaderLinks;

    private List<WebElement> subHeaderLinks;

    public void initUnderHeaderLinks (WebElement label){
        underHeaderLinks = label.findElements(By.xpath(".//h3/a"));
    }

    public void initSubHeaderLinks (WebElement label){
        subHeaderLinks = label.findElements(By.xpath("./following-sibling::ul/li"));
    }

    /**
     * 2. input field
     */

    @FindBy(xpath = "//div[@class='main-navigation-search']//div[@class='input-group']/input[@type='search']")
    private WebElement inputFieldSearch;

    @FindBy(xpath = "//div[@class='main-navigation-search']//div[@class='input-group']//button[@type='submit']")
    private WebElement searchButton;


    public static void initDriver(){
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
    }

    @FindBy(xpath = "//div[contains(@class ,'main-navigation-search-autocomplete')][contains(@class,'ui-widget-content')]")
    protected WebElement searchWidget;






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
                .map(link -> link.getText().trim().toLowerCase())
                .collect(Collectors.toList());
        names.forEach(name ->
                Assert.assertTrue(String.format("Link with text: '%s', not displayed", name ),linksText.contains(name.toLowerCase()))
        );
    }

    public WebElement findLink (List<WebElement> links, String name){
        return links
                .stream()
                .filter(link -> link
                        .getAttribute("outerText").trim()
                        .equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("Link with text: '%s', not displayed", name )));
    }

    public boolean checkElementHRef (WebElement element, String href){
        String elemetHref = element.getAttribute("href");
        return elemetHref.equals(href);
    }

    public void sendTextToElement(WebElement element, String text){
        waitUntilVisible(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void standartWait(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void checkListTextByType(List<WebElement> list, String type, List<String> searchValue) {
        List<String> textInLinks = list
                .stream()
                .map(link -> link.getText().trim().toLowerCase())
                .collect(Collectors.toList());
        switch (type) {
            case "contains":
                searchValue.forEach(value -> {
                    textInLinks.forEach(
                            linkText ->
                            Assert.assertTrue(String.format("Element with text '%s', not contains '%s'", linkText, searchValue), linkText.contains(value.toLowerCase())));
                });
                break;
            case "startWith":
                searchValue.forEach(value -> {
                    textInLinks.forEach(linkText ->
                            Assert.assertTrue(String.format("Element with text '%s', not start with '%s'", linkText, searchValue),linkText.startsWith(value.toLowerCase())));
                });
                break;
            case "endsWith":
                searchValue.forEach(value -> {
                    textInLinks.forEach(linkText ->
                            Assert.assertTrue(String.format("Element with text '%s', not ends with '%s'", linkText, searchValue),linkText.endsWith(value.toLowerCase())));
                });
                break;
            default:
                throw new IllegalArgumentException("Wrong type to search");
        }
    }
}
