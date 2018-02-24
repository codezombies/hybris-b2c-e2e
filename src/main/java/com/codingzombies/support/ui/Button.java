package com.codingzombies.support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Button extends EnhancedElement {

    public Button(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public void submit() {
        this.click();
    }
}
