package com.codingzombies.support;

import java.io.File;
import java.util.function.Consumer;

import com.codingzombies.support.driver.DriverFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.CanTakeScreenshot;
import com.codingzombies.support.ui.Page;
import com.codingzombies.support.ui.PageTemplate;

public final class Browser implements AutoCloseable {

    private final EnhancedWebDriver driver;
    private Object page;
    
    public Browser(EnhancedWebDriver driver){
        this.driver = driver;
    }

    public static Browser chrome(){
        return new Browser(DriverFactory.chrome());
    }

    public static Browser firefox(){
        return new Browser(DriverFactory.firefox());
    }

    public static Browser headless(){
        return new Browser(DriverFactory.headless());
    }

    public void resize(String size) {
        if(size.equalsIgnoreCase("desktop")) {
            this.maximize();
        }
        else if (size.equalsIgnoreCase("tablet")) {
            this.tablet();
        }
        else if (size.equalsIgnoreCase("mobile")) {
            this.mobile();
        }
    }
    
    public void maximize() {
        this.driver.manage().window().maximize();
    }

    public void mobile() {
        this.driver.manage().window().setSize(new Dimension(480, 1024));
    }

    public void tablet() {
        this.driver.manage().window().setSize(new Dimension(768, 1024));
    }
    
    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public void close() {
        System.out.println("Closing browser");
        driver.quit();
    }
    
    public void get(String url) {
        driver.get(url);
        this.driver.getWait().until(x -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
    
    public String getPageSource() {
        return driver.getPageSource();
    }
    
    @SuppressWarnings("unchecked")
    public <T extends PageTemplate, P extends Page<T>> P go(final String url, final Class<P> clazz) {
        this.driver.get(url);
        this.page = PageFactory.newPage(driver, clazz);
        return (P) this.page;
    }
    
    public <T extends PageTemplate, P extends Page<T>> void setCurrentPage(P page) {
        this.page = page;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends PageTemplate, P extends Page<T>> P getCurrentPage() {
        return (P) this.page;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends PageTemplate> T getCurrentPageTemplate() {
        return (T) getCurrentPage().getTemplate();
    }
    
    public <E extends WebElement> File takeScreenshot(E element) {
        return element.getScreenshotAs(OutputType.FILE);
    }

    public File takeScreenshot() {
        return driver.getScreenshotAs(OutputType.FILE);
    }
    
    public <C extends CanTakeScreenshot> File takeScreenshot(C component) {
        return component.getScreenshot();
    }

    public void start (Consumer<Browser> consumer) {
        consumer.accept(this);
    }

}
