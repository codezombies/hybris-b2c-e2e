package com.codingzombies.hybris.templates;

import org.openqa.selenium.WebDriver;

import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Image;
import com.codingzombies.support.ui.PageTemplate;

public class CheckoutTemplate extends PageTemplate {

    public CheckoutTemplate(WebDriver driver) {
        super(driver);
    }

    @Find(".js-site-logo img")
    public Image logo;
    
}
