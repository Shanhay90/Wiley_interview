package com.wiley.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


@Getter
public class EducationPage extends Page {

    public EducationPage() {
        PageFactory.initElements(driver, this);
        waitUntilVisible(educationLogo);
    }

    @FindBy(xpath = "//div[@class='wiley-slogan']")
    private WebElement educationLogo;

    @FindBy(xpath = "//a[@title = 'Wiley']")
    private WebElement footerLogo;

    @FindBy(xpath = "//div[@class='side-panel']/header")
    private WebElement subjectsLogo;

    @FindBys(@FindBy(xpath = "//div[@class='side-panel']/ul//a"))
    private List<WebElement> underSubjectsLinks;

    @FindBy(xpath = "//section[@class='preview-articles']")
    private WebElement recentlyReleasedProducts;


}
