# hybris-b2c-e2e
Selenium Test Template for OOTB Hybris Electronics Storefront

This project extended Selenium's Page Object Model creation to make it easier to define Pages, PageTemplates and Components. Given the following page below. The header and footer are almost always reusable in other pages as well and thus we should be defining them in a page template. All the content between the header and footer can then be said to really belong to the page. 

![page template](https://github.com/codezombies/hybris-b2c-e2e/blob/master/docs/images/listingpage_template.png)

We need to define the page template to define all the necessary components inside it.
```java
public class DefaultTemplate extends PageTemplate {

    public DefaultTemplate(WebDriver driver) {
        super(driver);
    }

    @Find("#js-site-search-input")
    public InputSearch searchInput;

    @Find(".js_search_button")
    public Button searchButton;

    @Find("nav.navigation.navigation--bottom")
    public Navigation headerNavigation;

    @Find(".breadcrumb-section .breadcrumb")
    public Optional<Breadcrumbs> breadcrumbs; // breadcrumbs may not always be visible on all the pages, e.g. homepage

}
```

To use the template defined above, we just need to define it in our page construction as part of the generic `Page<T>` parent object
```java
public class ListingPage extends Page<DefaultTemplate> {
    public ListingPage(WebDriver driver) {
        super(driver);
    }
}

```

You might have already noticed from our `PageTemplate` that we can inject components inside them, we can use `Optional<T>` to indicate that an element can or can't exist and we can also use `Supplier<T>` to promote lazy-loading of components. Given the component definition from our page like below.
![listing page components](https://github.com/codezombies/hybris-b2c-e2e/blob/master/docs/images/listingpage_components.png)

We can then define our page like this.
```java
public class ListingPage extends Page<DefaultTemplate> {

    public ListingPage(WebDriver driver) {
        super(driver);
    }

    @Find(".pagination-bar.top select")
    public Select sortBy;

    @Find(".pagination-bar.top .pagination")
    public Optional<Pagination> pagination; // pagination is optional
            
    @Find(".product__listing.product__list .product__list--item")
    public Supplier<List<ProductListItem>> products; // lazy-load products
 
    @Find("#product-facet")
    public Supplier<SearchFacets> facets; //lazy-load facets
    
    @Find(value = ".pagination-bar-results", transform = TransformType.LongOnly)
    private Long totalResults;
    
    public long getTotalResults() {
        return totalResults;
    }   
}

// Pagination
public class Pagination extends Component {

    @Find(".pagination-prev > a")
    private Link previousPageLink;
    
    @Find(".pagination-next > a")
    private Link nextPageLink;
    
    @Find(value = "li.active", transform = TransformType.IntegerOnly)
    private Integer currentPage;

    public Pagination(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public void gotoPreviousPage() {
        this.previousPageLink.click();
    }

    public void gotoNextPage() {
        this.nextPageLink.click();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<String> getAvailablePages() {
        return $$("li").stream().map(WebElement::getText).collect(Collectors.toList());
    }
}

// ProductListItem
public class ProductListItem extends Component {

    @Find(".product__list--name")
    private Link name;
    
    @Find(".product__listing--description")
    private Optional<String> description;
    
    @Find(".product__listing--price")
    private String price;
    
    @Find("form.add_to_cart_form button")
    private Button addtocartButton;
    
    public ProductListItem(WebDriver driver, WebElement delegate) {
        super(driver, delegate);
    }

    public String getName() {
        return this.name.getText();
    }

    public String getDescription() {
        return this.description.orElse("");
    }
    
    public String getPrice() {
        return this.price;
    }
    
    public void addToCart() {
        this.addtocartButton.click();
        // wait until pop-up mini cart is visible to block next actions
        this.driver.getWait().until(ExpectedConditions.visibilityOfElementLocated($by("#colorbox")));
    }
    
    public void clickName() {
        this.name.click();
    }
}

```
