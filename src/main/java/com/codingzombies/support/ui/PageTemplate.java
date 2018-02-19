package com.codingzombies.support.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codingzombies.support.EnhancedWebDriver;
import com.codingzombies.support.Selector;

public class PageTemplate {

    protected final EnhancedWebDriver driver;

    public PageTemplate(WebDriver driver) {
        this.driver = (EnhancedWebDriver) driver;
    }

    protected By $by(String selector) {
        return Selector.$by(selector);
    }
    
    public WebElement $(String selector) {
        return driver.$(selector);
    }

    public <T> T $(String selector, Class<T> clazz) {
        return driver.$(selector, clazz);
    }

    public List<WebElement> $$(String selector) {
        return driver.$$(selector);
    }

    public <T> List<T> $$(String selector, Class<T> clazz) {
        return driver.$$(selector, clazz);
    }
    
    public WebDriver getDelegate() {
        return driver.getDriver();
    }

    public WebDriverWait getWait() {
        return driver.getWait();
    }

    public Actions getActions() {
        return driver.getActions();
    }

    public JavascriptExecutor getJavascript() {
        return driver.getJavascript();
    }
    
    public String getTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
