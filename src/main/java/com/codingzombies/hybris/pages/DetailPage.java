package com.codingzombies.hybris.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.hybris.templates.DefaultTemplate;
import com.codingzombies.support.Find;
import com.codingzombies.support.Find.TransformType;
import com.codingzombies.support.ui.Button;
import com.codingzombies.support.ui.InputText;
import com.codingzombies.support.ui.Page;

public class DetailPage extends Page<DefaultTemplate> {

    @Find(".product-details.page-title > .name")
    private String name;
    
    @Find(".product-main-info .product-details > .price")
    private String price;
    
    @Find(".product-main-info .product-details > .description")
    private String shortDescription;

    @Find(value = ".product-details.page-title > .rating", transform = TransformType.LongOnly)
    private Long totalReviews;
    
    @Find(".product-details.page-title .rating-stars")
    private WebElement starRating;

    @Find(value = ".addtocart-component .stock-wrapper", transform = TransformType.LongOnly)
    private Long totalStock;
    
    @Find(".addtocart-component .btn.js-qty-selector-plus")
    private Button incrementButton;
    
    @Find(".addtocart-component .btn.js-qty-selector-minus")
    private Button decrementButton;

    @Find("#addToCartButton")
    private Button addtocartButton;
    
    @Find("#pdpAddtoCartInput")
    private InputText quantityInput;
    
    public DetailPage(WebDriver driver) {
        super(driver);
    }
    
    public String getName() {
        return this.name.replaceAll("ID\\d+", "");
    }
    
    public String getPrice() {
        return this.price;
    }
    
    public String getShortDescription() {
        return this.shortDescription;
    }
    
    public long getTotalReviews() {
        return totalReviews;
    }
    
    public double getReviewRating() {
        String rating = (String) getJavascript().executeScript("return JSON.parse(arguments[0].dataset.rating).rating", starRating);
        return Double.parseDouble(rating);
    }
    
    public long getTotalInStock() {
        return totalStock;
    }
    
    public void addToCart() {
        addtocartButton.click();
        // wait until pop-up mini cart is visible to block next actions
        this.driver.getWait().until(ExpectedConditions.visibilityOfElementLocated($by("#colorbox")));
    }
    
    public int getQuantity() {
        return Integer.parseInt(quantityInput.getValue());
    }
    
    public void changeQuantity(int quantity) {        
        this.quantityInput.type("" + quantity);
    }
    
    public void clickAddQuantity() {
        incrementButton.click();
    }
    
    public void clickSubtractQuantity() {
        decrementButton.click();        
    }
    
}
