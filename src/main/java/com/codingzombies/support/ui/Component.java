package com.codingzombies.support.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.EnhancedWebDriver;
import com.codingzombies.support.Selector;

import ru.yandex.qatools.ashot.AShot;

public class Component implements CanTakeScreenshot {
    
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
    
    public File getScreenshot() throws WebDriverException {
        try {
            return delegate.getScreenshotAs(OutputType.FILE);
        } catch (UnsupportedCommandException e) {
            BufferedImage image = new AShot().takeScreenshot(this.driver, this.delegate).getImage();
            try {
                File temp = File.createTempFile("comp", "screenshot");
                ImageIO.write(image, "png", temp);
                return temp;
            } catch (IOException io) {
                throw new RuntimeException(io);
            }
        }
    }
    
}
