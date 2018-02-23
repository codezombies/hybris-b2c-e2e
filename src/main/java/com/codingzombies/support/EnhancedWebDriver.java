package com.codingzombies.support;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnhancedWebDriver implements WebDriver, JavascriptExecutor, TakesScreenshot {

    final WebDriver delegate;
    final WebDriverWait wait;
    final Actions actions;
    final JavascriptExecutor javascript;

    public EnhancedWebDriver(WebDriver driver) {
        this.delegate = driver;
        this.wait = new WebDriverWait(driver, 5);
        this.actions = new Actions(driver);
        this.javascript = (JavascriptExecutor) driver;

        // set timeouts TODO - move to a different config location
        Timeouts timeouts = driver.manage().timeouts();
        timeouts.implicitlyWait(10, TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(1, TimeUnit.MINUTES);
        timeouts.setScriptTimeout(30, TimeUnit.SECONDS);
    }

    public By $by(String selector) {
        return Selector.$by(selector);
    }

    public WebElement $(String selector) {
        return $(selector, WebElement.class);
    }

    public <T> T $(String selector, Class<T> clazz) {
        WebElement element = delegate.findElement(Selector.$by(selector));
        return transform(clazz, element);
    }

    public WebElement $(SearchContext context, String selector) {
        return $(context, selector, WebElement.class);
    }

    public <T> T $(SearchContext context, String selector, Class<T> clazz) {
        WebElement element = context.findElement(Selector.$by(selector));
        return transform(clazz, element);
    }

    public List<WebElement> $$(String selector) {
        return $$(selector, WebElement.class);
    }

    public <T> List<T> $$(String selector, Class<T> clazz) {
        List<WebElement> elements = delegate.findElements(Selector.$by(selector));
        return elements.stream().map(element -> transform(clazz, element)).collect(Collectors.<T>toList());
    }

    public List<WebElement> $$(SearchContext context, String selector) {
        return $$(context, selector, WebElement.class);
    }

    public <T> List<T> $$(SearchContext context, String selector, Class<T> clazz) {
        List<WebElement> elements = context.findElements(Selector.$by(selector));
        return elements.stream().map(element -> transform(clazz, element)).collect(Collectors.<T>toList());
    }

    private <T> T transform(Class<T> clazz, WebElement element) {
        return ClassSupport.newInstance(clazz, delegate, element);
    }

    public String getTitle() {
        return this.delegate.getTitle();
    }

    public WebDriver getDriver() {
        return delegate;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Actions getActions() {
        return actions;
    }

    public JavascriptExecutor getJavascript() {
        return javascript;
    }

    @Override
    public void get(String url) {
        delegate.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return delegate.getCurrentUrl();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return delegate.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return delegate.findElement(by);
    }

    @Override
    public String getPageSource() {
        return delegate.getPageSource();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public void quit() {
        delegate.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return delegate.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return delegate.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return delegate.switchTo();
    }

    @Override
    public Navigation navigate() {
        return delegate.navigate();
    }

    @Override
    public Options manage() {
        return delegate.manage();
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) delegate).executeAsyncScript(script, args);
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) delegate).executeScript(script, args);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        if (delegate instanceof TakesScreenshot) {
            return ((TakesScreenshot) delegate).getScreenshotAs(target);
        }
        throw new UnsupportedOperationException("driver does not support taking screenshots");
    }

}
