package com.wiley.testsClasses;

import com.wiley.pages.CountyModalWindow;
import com.wiley.pages.Page;
import com.wiley.pages.StartPage;
import com.wiley.pages.StudentsPage;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UiTests {

    private WebDriver driver;

    @Before
    public  void openBrowser(){
        Page.initDriver();
        this.driver = Page.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://www.wiley.com/WileyCDA/");
        CountyModalWindow countyModalWindow = new CountyModalWindow();
        if (countyModalWindow.isDisplayed())
            countyModalWindow.clickYesOrNo("No");
    }

    @Test
    public void checkLinksOnMainPage(){
        StartPage startPage = new StartPage();
        List<String> linksNames = Arrays.asList("Resources", "Subjects","About");
        startPage.checkLinks(startPage.getHeaderLinks(), linksNames);
    }

    @Test
    public void checkItemsUnderResources(){
        StartPage startPage = new StartPage();
        WebElement link = startPage.findLink(startPage.getHeaderLinks(),"Resources");
        startPage.moveToElement(link);
        startPage.initUnderHeaderLinks(link);
        List<String> linksNames = Arrays.asList(
                "Students","Instructors", "Researchers", "Professionals", "Librarians", "Institutions", "Authors", "Resellers", "Corporations", "Societies"
                );
        startPage.checkLinks(startPage.getUnderHeaderLinks(), linksNames);
    }

    @Test
    public void checkClickStudentsItem(){
        StartPage startPage = new StartPage();
        WebElement link = startPage.findLink(startPage.getHeaderLinks(),"Resources");
        startPage.initUnderHeaderLinks(link);
        startPage.moveToElement(link);
        startPage.findLink(startPage.getUnderHeaderLinks(),"Students").click();
        StudentsPage studentsPage = new StudentsPage();
        Assert.assertEquals( "https://www.wiley.com/en-ru/students" ,driver.getCurrentUrl());
        Assert.assertTrue(studentsPage.getStudentsLabel().isDisplayed());
        Assert.assertTrue(studentsPage.getWileyPlusLink().isDisplayed());
        Assert.assertTrue(studentsPage.checkElementHRef(studentsPage.getWileyPlusLink(), "http://wileyplus.wiley.com/"));
    }


    @After
    public void closeBrowser(){
        Page.driver.close();
        Page.driver.quit();
        Page.driver = null;
    }
}
