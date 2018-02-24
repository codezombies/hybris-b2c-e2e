package com.codingzombies.hybris.components.listing;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Component;

public class SearchFacets extends Component {

    public SearchFacets(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    @Find(".facet.js-facet")
    private List<SearchFacet> facets;
    
    public SearchFacet getFacet(String name) {
        return facets.stream().filter(it -> it.getName().equalsIgnoreCase(name))
            .findFirst().get();
    }

    public Optional<SearchFacet> getAppliedFacet() {
        return facets.stream().filter(it -> it.getName().equalsIgnoreCase("Applied Facets"))
                .findFirst();
    }
    
    public List<String> getAllFacets() {
        return facets.stream().map(it -> it.getName()).collect(Collectors.toList());
    }
    
}
