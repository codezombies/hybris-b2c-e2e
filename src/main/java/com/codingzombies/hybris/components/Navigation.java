package com.codingzombies.hybris.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.support.ui.Component;

public class Navigation extends Component {

    public Navigation(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    public void clickLink(String targetLinkName) {
        this.$(targetLinkName).click();
    }

    public void clickLink(String parentLinkName, String targetLinkName) {        
        WebElement parentElement = $(parentLinkName);
        driver.getActions().moveToElement(parentElement).build().perform();

        WebElement targetElement = $(targetLinkName);
        driver.getWait().until(ExpectedConditions.visibilityOf(targetElement)).click();
    }
}
