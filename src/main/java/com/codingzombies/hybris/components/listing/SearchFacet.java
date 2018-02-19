package com.codingzombies.hybris.components.listing;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.hybris.FacetItemType;
import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Component;

public class SearchFacet extends Component {

    @Find(".facet__list li")
    public List<FacetItem> items;
    
    public SearchFacet(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public String getName() {
        return $(".facet__name.js-facet-name").getText().trim();
    }
    
    public void click(String name) {
        items.stream()
            .filter(it -> it.getName().trim().equalsIgnoreCase(name))
            .findFirst().ifPresent(it -> it.click());
    }
    
    public void click(String name, FacetItemType facetItemType) {
        items.stream()
            .filter(it -> it.getName().equalsIgnoreCase(name))
            .findFirst().ifPresent(it -> { 
                if(facetItemType == FacetItemType.LINK)  { 
                    it.clickLink();
                }
                else if (facetItemType == FacetItemType.CHECKBOX){
                    it.clickCheckBox();
                }
                else if (facetItemType == FacetItemType.REMOVE){
                    it.clickRemove();
                }
            });
    }
    
    public Map<String, Long> getFacetItemNameCount() {
        return items.stream().collect(Collectors.toMap(FacetItem::getName, FacetItem::getCount));
    }
    
    public List<String> getAllAvailableFilters() {
        return items.stream().map(it -> it.getText().trim()).collect(Collectors.toList());
    }
}
