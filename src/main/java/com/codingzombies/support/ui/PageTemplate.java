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
    
    protected WebElement $(String selector) {
        return driver.$(selector);
    }

    protected <T> T $(String selector, Class<T> clazz) {
        return driver.$(selector, clazz);
    }

    protected List<WebElement> $$(String selector) {
        return driver.$$(selector);
    }

    protected <T> List<T> $$(String selector, Class<T> clazz) {
        return driver.$$(selector, clazz);
    }

    protected WebDriver getDelegate() {
        return driver.getDriver();
    }

    protected WebDriverWait getWait() {
        return driver.getWait();
    }

    protected Actions getActions() {
        return driver.getActions();
    }

    protected JavascriptExecutor getJavascript() {
        return driver.getJavascript();
    }

    protected String getTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
