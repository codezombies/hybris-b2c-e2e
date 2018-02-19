package com.codingzombies.support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputButton extends EnhancedElement {

    public InputButton(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public void submit() {
        this.click();
    }
}
