package com.wiley.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CountyModalWindow extends Page {
    public CountyModalWindow() {
        standartWait();
        PageFactory.initElements(driver, this);
        waitUntilVisible(windowLabel);

    }

    @FindBy(xpath = "//*[@class='modal-title']")
    private WebElement windowLabel;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement closeButton;

    @FindBy(xpath = "//button[.='YES']")
    private WebElement yesButton;

    @FindBy(xpath = "//button[.='NO']")
    private WebElement noButton;


    public Boolean isDisplayed() {
        return windowLabel != null;
    }


    public void closeWindow() {
        closeButton.click();
    }

    public void clickYesOrNo(String yesOrNo) {
        switch (yesOrNo) {
            case "Yes":
                moveToElement(yesButton);
                yesButton.click();
                break;
            case "No":
                moveToElement(noButton);
                noButton.click();
                break;
            default:
                closeButton.click();
        }
    }
}
