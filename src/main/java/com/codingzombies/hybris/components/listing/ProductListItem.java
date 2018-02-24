package com.codingzombies.hybris.components.listing;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Button;
import com.codingzombies.support.ui.Component;
import com.codingzombies.support.ui.Link;

public class ProductListItem extends Component {

    @Find(".product__list--name")
    private Link name;
    
    @Find(".product__listing--description")
    private Optional<String> description;
    
    @Find(".product__listing--price")
    private String price;
    
    @Find("form.add_to_cart_form button")
    private Button addtocartButton;
    
    public ProductListItem(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public String getName() {
        return this.name.getText();
    }

    public String getDescription() {
        return this.description.orElse("");
    }
    
    public String getPrice() {
        return this.price;
    }
    
    public void addToCart() {
        this.addtocartButton.click();
        // wait until pop-up mini cart is visible to block next actions
        this.driver.getWait().until(ExpectedConditions.visibilityOfElementLocated($by("#colorbox")));
    }
    
    public void clickName() {
        this.name.click();
    }
}
