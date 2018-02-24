package com.codingzombies.support.ui;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codingzombies.support.EnhancedWebDriver;
import com.codingzombies.support.PageFactory;
import com.codingzombies.support.Selector;

public abstract class Page<T extends PageTemplate> implements CanTakeScreenshot {

    protected final EnhancedWebDriver driver;
    protected final T template;
    
    @SuppressWarnings("unchecked")
    public Page(WebDriver driver) {
        this.driver = (EnhancedWebDriver) driver;
        this.driver.getWait().until(x -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.template = PageFactory.newPageTemplate(driver, (Class<T>)type);
    }
    
    public String getTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public File getScreenshot() throws WebDriverException {
        return driver.getScreenshotAs(OutputType.FILE);
    }

    public T getTemplate() {
        return template;
    }

    protected By $by(String selector) {
        return Selector.$by(selector);
    }

    protected WebElement $(String selector) {
        return driver.$(selector);
    }

    protected <E> E $(String selector, Class<E> clazz) {
        return driver.$(selector, clazz);
    }

    protected List<WebElement> $$(String selector) {
        return driver.$$(selector);
    }

    protected <E> List<E> $$(String selector, Class<E> clazz) {
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
}
