package com.codingzombies.hybris.components.listing;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Component;
import com.codingzombies.support.ui.Link;

public class Pagination extends Component {

    public Pagination(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    @Find(".pagination-prev > a")
    private Link previousPageLink;

    @Find(".pagination-next > a")
    private Link nextPageLink;

    @Find("li.active")
    private WebElement currentPage;

    public void gotoPreviousPage() {
        this.previousPageLink.click();
    }

    public void gotoNextPage() {
        this.nextPageLink.click();
    }

    public String getCurrentPage() {
        return currentPage.getText().replaceAll("\\D+", "");
    }

    public List<String> getAvailablePages() {
        return $$("li").stream().map(WebElement::getText).collect(Collectors.toList());
    }

}
