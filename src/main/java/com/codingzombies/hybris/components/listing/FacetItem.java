package com.codingzombies.hybris.components.listing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Checkbox;
import com.codingzombies.support.ui.Component;
import com.codingzombies.support.ui.Link;

public class FacetItem extends Component {

    @Find(".facet__text a")
    private Link facetLink;

    @Find("a")
    private Link removeFacetLink;
    
    @Find(".js-facet-checkbox")
    private Checkbox facetCheckbox;
    
    public FacetItem(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }
    
    public String getName() {
        return getText().replaceAll("\\(\\d+\\)", "").trim();
    }
    
    public long getCount() {
        return Long.parseLong(getText().replaceAll("\\D+", "").trim());
    }
    
    public void clickLink() {
        facetLink.click();            
    }
    
    public void clickCheckBox() {
        facetCheckbox.click();
    }
    
    public void clickRemove() {
        removeFacetLink.click();
    }
}
