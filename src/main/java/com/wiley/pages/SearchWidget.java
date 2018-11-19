package com.wiley.pages;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SearchWidget extends Page {
    public SearchWidget() {
        PageFactory.initElements(driver, this);
        waitUntilVisible(searchWidget);
    }

    @FindBys(@FindBy(xpath = "//div[@class= 'search-list']/div/a"))
    private List<WebElement> searchList;

    @FindBys(@FindBy(xpath = "//div[@class= 'related-content-products']//*[@class = 'product-title']"))
    private List<WebElement> searchContentList;



}
