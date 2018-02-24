package com.codingzombies.support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Checkbox extends EnhancedElement {

    public Checkbox(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    public boolean isChecked() {
        String checked = this.delegate.getAttribute("checked");
        return checked != null; // TODO - will this be enough or we need to check if the value is 'checked'?
    }

    public void select() {
        this.delegate.click();
    }

}
