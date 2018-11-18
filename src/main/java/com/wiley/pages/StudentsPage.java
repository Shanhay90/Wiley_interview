package com.wiley.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class StudentsPage extends Page {
    public StudentsPage(){
        PageFactory.initElements(driver, this);
        waitUntilVisible(studentsLabel);
    }

    @FindBy(xpath = "//p[@class='sg-title-h1'][contains(.,'Students')]")
    private WebElement studentsLabel;

    @FindBy(xpath = "//a[.= 'WileyPLUS']")
    private WebElement wileyPlusLink;

    @FindBy(xpath = "//a[.= 'Learn more.']")
    private WebElement learnMoreLink;
}
