package com.codingzombies.support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Link extends EnhancedElement {

    public Link(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    public String getHref() {
        return this.delegate.getAttribute("href");
    }

    public String getText() {
        return this.delegate.getText();
    }

    @Override
    public String toString() {
        return getText();
    }
}
