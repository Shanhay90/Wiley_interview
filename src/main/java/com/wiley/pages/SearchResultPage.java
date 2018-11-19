package com.wiley.pages;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SearchResultPage extends Page {
    public SearchResultPage(){
        PageFactory.initElements(driver, this);
        waitUntilVisible(driver.findElement(By.xpath("//*[.='Refine search']")));
    }

    @FindBys(@FindBy(xpath = "//div[@class='products-list']/section[@class = 'product-item ']"))
    private List<WebElement> productsItemList;



    public List<WebElement> getProductsTitle() {
        List<WebElement> titles = productsItemList
                .stream()
                .map(item -> item.findElement(By.xpath(".//*[@class='product-title']/a")))
                .collect(Collectors.toList());
        return titles;
    }


    public void checkAddToCardInProducts() {
        for (WebElement product : productsItemList){
            List<WebElement> addToCardButtons = product.findElements(By.xpath(".//button[text()='Add to cart']"));
            Assert.assertTrue(String.format("In the block with the text '%s' there is no Add To Card button", getProductName(product)), addToCardButtons!=null && addToCardButtons.size()>=1);
        }

    }

    private String getProductName(WebElement product) {
        return product.findElement(By.xpath("//*[@class='product-title']/a")).getText().trim();
    }
}



