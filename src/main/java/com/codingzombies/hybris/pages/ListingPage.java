package com.codingzombies.hybris.pages;

import java.util.List;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;

import com.codingzombies.hybris.components.Breadcrumbs;
import com.codingzombies.hybris.components.listing.Pagination;
import com.codingzombies.hybris.components.listing.ProductListItem;
import com.codingzombies.hybris.components.listing.SearchFacets;
import com.codingzombies.hybris.templates.DefaultTemplate;
import com.codingzombies.support.Find;
import com.codingzombies.support.Find.TransformType;
import com.codingzombies.support.ui.Page;
import com.codingzombies.support.ui.Select;

public class ListingPage extends Page<DefaultTemplate> {

    public ListingPage(WebDriver driver) {
        super(driver);
    }

    @Find(".pagination-bar.top select")
    public Select sortBy;

    @Find(".pagination-bar.top .pagination")
    public Pagination pagination;
            
    @Find(".product__listing.product__list .product__list--item")
    public Supplier<List<ProductListItem>> products;
 
    @Find("#product-facet")
    public Supplier<SearchFacets> facets;
    
    @Find(value = ".pagination-bar-results", transform = TransformType.LongOnly)
    private Long totalResults;
    
    public long getTotalResults() {
        return totalResults;
    }
    
    
}
