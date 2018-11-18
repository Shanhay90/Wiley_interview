package com.wiley.pages;


import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


@Getter
public class StartPage extends Page {

    public StartPage() {
        PageFactory.initElements(driver, this);
        waitUntilVisible(footerLogo);
    }

    @FindBy(xpath = "//div//img[@title = 'Wiley']")
    private WebElement headerLogo;

    @FindBy(xpath = "//a[@title = 'Wiley']")
    private WebElement footerLogo;

    @FindBys(@FindBy(xpath = "//ul[@class= 'navigation-menu-items']/li"))
    private List<WebElement> headerLinks;

    private List<WebElement> underHeaderLinks;

    public void initUnderHeaderLinks (WebElement label){
        underHeaderLinks = label.findElements(By.xpath(".//h3"));
    }

}
