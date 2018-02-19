package com.codingzombies.support.ui;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.EnhancedWebDriver;

public class EnhancedElement {

    protected final EnhancedWebDriver driver;
    protected final WebElement delegate;

    public EnhancedElement(WebDriver driver, WebElement delegate) {
        super();
        this.driver = (EnhancedWebDriver)driver;
        this.delegate = delegate;
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return delegate.getScreenshotAs(target);
    }

    public void click() {
        delegate.click();
    }

    @SuppressWarnings("unchecked")
    public <T> T $js(String script, Object... args) {
        return (T) this.driver.getJavascript().executeScript(script, args);
    }
}
