package com.codingzombies.support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputText extends EnhancedElement {

    public InputText(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    public void type(String text) {
        this.delegate.clear();
        this.delegate.sendKeys(text);
    }

    public String getValue() {
        return this.delegate.getAttribute("value");
    }
}
