package com.wiley.testsClasses;

import com.wiley.pages.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UiTests {

    private static WebDriver driver;

    @BeforeClass
    public static void openBrowser(){
        Page.initDriver();
        driver = Page.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Before
    public void start(){
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
    public void checkStudentsPage(){
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

    @Test
    public void checkEducationPage(){
        StartPage startPage = new StartPage();
        WebElement link = startPage.findLink(startPage.getHeaderLinks(),"Subjects");
        startPage.initUnderHeaderLinks(link);
        startPage.moveToElement(link);
        WebElement subHeaderLink = startPage.findLink(startPage.getUnderHeaderLinks(),"E-L");
        startPage.initSubHeaderLinks(subHeaderLink);
        startPage.moveToElement(subHeaderLink);
        WebElement newLink = startPage.findLink(startPage.getSubHeaderLinks(),"Education");
        startPage.moveToElement(newLink);
        newLink.click();
        EducationPage educationPage = new EducationPage();
        Assert.assertTrue(educationPage.getEducationLogo().isDisplayed());
        WebElement subjects = educationPage.getSubjectsLogo();
        WebElement productsBloc = educationPage.getRecentlyReleasedProducts();
        Assert.assertTrue(subjects.isDisplayed());
        Assert.assertTrue(subjects.getLocation().getX() < productsBloc.getLocation().getX());
        Assert.assertEquals(13, educationPage.getUnderSubjectsLinks().size());
        List<String> linksNames = Arrays.asList(
                "Information & Library Science","Education & Public Policy", "K-12 General", "Higher Education General",
                "Vocational Technology", "Conflict Resolution & Mediation (School settings)", "Curriculum Tools- General",
                "Special Educational Needs", "Theory of Education", "Education Special Topics",
                "Educational Research & Statistics", "Literacy & Reading", "Classroom Management"
        );
        educationPage.checkLinks(educationPage.getUnderSubjectsLinks(), linksNames);
    }

    @Test
    public void checkStartPage(){
        StartPage startPage = new StartPage();
        Assert.assertEquals("https://www.wiley.com/en-ru", driver.getCurrentUrl());
        WebElement link = startPage.findLink(startPage.getHeaderLinks(),"Resources");
        startPage.initUnderHeaderLinks(link);
        startPage.moveToElement(link);
        startPage.findLink(startPage.getUnderHeaderLinks(),"Students").click();
        StudentsPage studentsPage = new StudentsPage();
        Assert.assertEquals( "https://www.wiley.com/en-ru/students" ,driver.getCurrentUrl());
        startPage.getHeaderLogo().click();
        StartPage newStartPage = new StartPage();
        Assert.assertEquals("https://www.wiley.com/en-ru", driver.getCurrentUrl());
    }

    @Test
    public void checkEmptySearchInput(){
        StartPage startPage = new StartPage();
        Assert.assertEquals("https://www.wiley.com/en-ru", driver.getCurrentUrl());
        startPage.getSearchButton().click();
        Assert.assertEquals("https://www.wiley.com/en-ru", driver.getCurrentUrl());
    }

    @Test
    public void checkMathInInput(){
        StartPage startPage = new StartPage();
        startPage.sendTextToElement(startPage.getInputFieldSearch(), "Math");
        startPage.waitUntilVisible(startPage.getSearchWidget());
        SearchWidget searchWidget = new SearchWidget();
        Assert.assertTrue(searchWidget.getSearchList().get(0).getLocation().getY() > searchWidget.getInputFieldSearch().getLocation().getY());
        Assert.assertTrue(searchWidget.getSearchList().get(0).getLocation().getX() < searchWidget.getSearchContentList().get(0).getLocation().getX());
        List<String> searchValue = Arrays.asList("Math");
        searchWidget.checkListTextByType(searchWidget.getSearchList(), "startWith", searchValue);
        searchWidget.checkListTextByType(searchWidget.getSearchContentList(), "contains", searchValue);
        searchWidget.getSearchButton().click();
        SearchResultPage searchResultPage = new SearchResultPage();
        Assert.assertEquals(10, searchResultPage.getProductsItemList().size());
        searchResultPage.checkAddToCardInProducts();
        searchResultPage.checkListTextByType(searchResultPage.getProductsTitle(),"contains", searchValue);

    }

    @Test
    public void checkMathInInputAndSearch(){
        StartPage startPage = new StartPage();
        startPage.sendTextToElement(startPage.getInputFieldSearch(), "Math");
        startPage.getSearchButton().click();
        List<String> searchValue = Arrays.asList("Math");
        SearchResultPage searchResultPage = new SearchResultPage();
        Assert.assertEquals(10, searchResultPage.getProductsItemList().size());
        searchResultPage.checkListTextByType(searchResultPage.getProductsTitle(),"contains", searchValue);
    }





    @AfterClass
    public static void closeBrowser(){
        driver.quit();
        driver = null;
    }
}
