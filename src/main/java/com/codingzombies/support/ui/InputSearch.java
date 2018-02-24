package com.codingzombies.support.ui;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputSearch extends EnhancedElement {

    public InputSearch(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    public void search(String text) {
        this.delegate.sendKeys(text);
        this.delegate.sendKeys(Keys.RETURN);
    }

}
