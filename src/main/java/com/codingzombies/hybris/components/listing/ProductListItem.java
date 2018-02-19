package com.codingzombies.hybris.components.listing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.support.ui.Component;

public class ProductListItem extends Component {

    public ProductListItem(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public String getName() {
        return $(".product__list--name").getText();
    }

    public String getDescription() {
        return $(".product__listing--description").getText();
    }
    
    public String getPrice() {
        return $(".product__listing--price").getText();
    }
    
    // TODO - pop-up cart
    public void addToCart() {
        $("form.add_to_cart_form button").click();
        // wait until pop-up mini cart is visible to block next actions
        this.driver.getWait().until(ExpectedConditions.visibilityOfElementLocated($by("#colorbox")));
    }
    
    public void clickName() {
        $(".product__list--name").click();
    }
}
