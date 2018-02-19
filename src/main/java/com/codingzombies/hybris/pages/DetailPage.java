package com.codingzombies.hybris.pages;

import org.openqa.selenium.WebDriver;

import com.codingzombies.hybris.templates.DefaultTemplate;
import com.codingzombies.support.ui.Page;

public class DetailPage extends Page<DefaultTemplate> {

    public DetailPage(WebDriver driver) {
        super(driver);
    }
    
    public String getName() {
        return "";
    }
    
    public String getShortDescription() {
        return "";
    }
    
    public String getLongDescription() {
        return "";
    }
    
    public long getTotalReviews() {
        return 0L;
    }
    
    public double getReviewRating() {
        return 0.0;
    }
    
    public long getTotalInStock() {
        return 0L;
    }
    
    public void addToCart() {
        
    }
    
    public void changeQuantity(int quantity) {
        
    }
    
    public void clickAddQuantity() {
        
    }
    
    public void clickSubtractQuantity() {
        
    }
    
}
