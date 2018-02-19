package com.codingzombies.support.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.EnhancedWebDriver;
import com.codingzombies.support.Selector;

public class Component {
    
    protected final EnhancedWebDriver driver;
    private final WebElement delegate;

    public Component(WebDriver driver, WebElement delegate) {
        super();
        this.driver = (EnhancedWebDriver) driver;
        this.delegate = delegate;
    }

    protected final WebElement getDelegate() {
        return this.delegate;
    }
    
    protected By $by(String selector) {
        return Selector.$by(selector);
    }
    
    protected WebElement $(String selector) {
        return driver.$(delegate, selector);
    }

    protected <E> E $(String selector, Class<E> clazz) {
        return driver.$(delegate, selector, clazz);
    }

    protected List<WebElement> $$(String selector) {
        return driver.$$(delegate, selector);
    }

    protected <E> List<E> $$(String selector, Class<E> clazz) {
        return driver.$$(delegate, selector, clazz);
    }
    
    public String getText() {
        return this.delegate.getText();
    }
    
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return delegate.getScreenshotAs(target);
    }
    
}
