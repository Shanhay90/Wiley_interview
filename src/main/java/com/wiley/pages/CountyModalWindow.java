package com.wiley.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CountyModalWindow extends Page{
    public CountyModalWindow(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PageFactory.initElements(driver, this);
        waitUntilVisible(windowLabel);

    }

    @FindBy(xpath ="//*[@class='modal-title']")
    private WebElement windowLabel;

    @FindBy(xpath ="//button[@class='close']")
    private WebElement closeButton;

    @FindBy(xpath ="//button[.='YES']")
    private WebElement yesButton;

    @FindBy(xpath ="//button[.='NO']")
    private WebElement noButton;


    public Boolean isDisplayed(){
        return windowLabel != null;
    }


    public void closeWindow (){
        closeButton.click();
    }

    public void clickYesOrNo(String yesOrNo){
        switch (yesOrNo){
            case "Yes":
                yesButton.click();
                break;
            case "No":
                noButton.click();
                break;
            default:
                closeButton.click();
        }
    }
}
