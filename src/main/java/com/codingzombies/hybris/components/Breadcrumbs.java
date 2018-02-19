package com.codingzombies.hybris.components;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.Component;

public class Breadcrumbs extends Component {

    public Breadcrumbs(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public List<String> getAllValues() {
        return $$("li > a").stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void click(String linkName) {
        $(linkName).click();
    }

}
