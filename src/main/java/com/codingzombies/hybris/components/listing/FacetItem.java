package com.codingzombies.hybris.components.listing;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.Component;

public class FacetItem extends Component {

    public FacetItem(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public String getName() {
        return getText().replaceAll("\\(\\d+\\)", "").trim();
    }
    
    public long getCount() {
        return Long.parseLong(getText().replaceAll("\\D+", "").trim());
    }

    public void click() {
        try {
            $(".facet__text a").click();            
        }
        catch (NotFoundException e) {
            $(".facet__list__checkbox").click();
        }
    }
    
    public void clickLink() {
        $(".facet__text a").click();            
    }
    
    public void clickCheckBox() {
        $(".js-facet-checkbox").click();
    }
    
    public void clickRemove() {
        $("a").click();
    }
}
