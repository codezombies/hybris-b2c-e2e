package com.codingzombies.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.Page;
import com.codingzombies.support.ui.PageTemplate;

public final class PageFactory {

    public static <T extends PageTemplate> T newPageTemplate(WebDriver driver, Class<T> clazz) {
        driver = decorate(driver);
        T template = instantiateType(driver, clazz);
        org.openqa.selenium.support.PageFactory.initElements(new PageFieldDecorator(driver), template);
        return template;
    }
    
    public static <T extends PageTemplate, P extends Page<T>> P newPage(WebDriver driver, Class<P> clazz) {
        driver = decorate(driver);
        P page = instantiateType(driver, clazz);
        org.openqa.selenium.support.PageFactory.initElements(new PageFieldDecorator(driver), page);
        return page;
    }

    public static <T> T newComponent(WebDriver driver, WebElement element, Class<T> clazz) {
        driver = decorate(driver);
        T component = instantiateComponent(driver, element, clazz);
        org.openqa.selenium.support.PageFactory.initElements(new ComponentFieldDecorator(driver, element), component);
        return component;
    }

    private static WebDriver decorate(WebDriver driver) {
        if(driver instanceof EnhancedWebDriver) {
            return driver;
        }
        return new EnhancedWebDriver(driver);
    }
    
    private static <T> T instantiateComponent(WebDriver driver, WebElement element, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class, WebElement.class);
                return constructor.newInstance(driver, element);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static <T> T instantiateType(WebDriver driver, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
                return constructor.newInstance(driver);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
