package com.codingzombies.support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Image extends EnhancedElement {

    public Image(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    public String getSrc() {
        return this.delegate.getAttribute("src");
    }

    public String getAlt() {
        return this.delegate.getAttribute("alt");
    }

    public String getTitle() {
        return this.delegate.getAttribute("title");
    }

    public long getWidth() {
        return this.$js("return arguments[0].width", this.delegate);
    }

    public long getHeight() {
        return this.$js("return arguments[0].height", this.delegate);
    }

    public boolean isVisible() {
        return this.delegate.isDisplayed() && getWidth() > 0 && getHeight() > 0;
    }
}
