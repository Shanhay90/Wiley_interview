package com.wiley.pages;


import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


@Getter
public class StartPage extends Page {

    public StartPage() {
        PageFactory.initElements(driver, this);
        waitUntilVisible(footerLogo);
    }


    @FindBy(xpath = "//a[@title = 'Wiley']")
    private WebElement footerLogo;

}
