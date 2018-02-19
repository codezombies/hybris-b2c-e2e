package com.codingzombies.support.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;

public class Select extends EnhancedElement implements ISelect {
    
    private org.openqa.selenium.support.ui.Select delegateSelect;

    public Select(WebDriver driver, WebElement element) {
        super(driver, element);
        this.delegateSelect = new org.openqa.selenium.support.ui.Select(element);
    }

    public List<String> getAllOptionTexts() {
        return this.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getAllOptionValues() {
        return this.getOptions().stream().map(it -> it.getAttribute("value")).collect(Collectors.toList());
    }

    @Override
    public boolean isMultiple() {
        return delegateSelect.isMultiple();
    }

    @Override
    public List<WebElement> getOptions() {
        return delegateSelect.getOptions();
    }

    @Override
    public List<WebElement> getAllSelectedOptions() {
        return delegateSelect.getAllSelectedOptions();
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return delegateSelect.getFirstSelectedOption();
    }

    @Override
    public void selectByVisibleText(String text) {
        delegateSelect.selectByVisibleText(text);
    }

    @Override
    public void selectByIndex(int index) {
        delegateSelect.selectByIndex(index);
    }

    @Override
    public void selectByValue(String value) {
        delegateSelect.selectByValue(value);
    }

    @Override
    public void deselectAll() {
        delegateSelect.deselectAll();
    }

    @Override
    public void deselectByValue(String value) {
        delegateSelect.deselectByValue(value);
    }

    @Override
    public void deselectByIndex(int index) {
        delegateSelect.deselectByIndex(index);
    }

    @Override
    public void deselectByVisibleText(String text) {
        delegateSelect.deselectByVisibleText(text);
    }
    
    
}
