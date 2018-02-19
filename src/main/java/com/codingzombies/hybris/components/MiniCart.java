package com.codingzombies.hybris.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.Component;

public class MiniCart extends Component {

    public MiniCart(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public String getPrice() {
        return $(".mini-cart-price").getText().trim();
    }
    
    public String getItemCount() {
        return $(".mini-cart-count").getText().replaceAll("\\D+", "").trim();
    }
}
