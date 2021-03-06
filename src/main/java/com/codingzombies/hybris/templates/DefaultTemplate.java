package com.codingzombies.hybris.templates;

import com.codingzombies.hybris.components.Breadcrumbs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.hybris.components.MiniCart;
import com.codingzombies.hybris.components.Navigation;
import com.codingzombies.support.Find;
import com.codingzombies.support.ui.Image;
import com.codingzombies.support.ui.Button;
import com.codingzombies.support.ui.InputSearch;
import com.codingzombies.support.ui.Link;
import com.codingzombies.support.ui.PageTemplate;
import com.codingzombies.support.ui.Select;

import java.util.Optional;


public class DefaultTemplate extends PageTemplate {

    public DefaultTemplate(WebDriver driver) {
        super(driver);
    }

    @Find("#js-site-search-input")
    public InputSearch searchInput;

    @Find(".js_search_button")
    public Button searchButton;

    @Find(".nav__links--account a")
    public Link signIn;

    @Find(".js-site-logo img")
    public Image logo;

    @Find("nav.navigation.navigation--bottom")
    public Navigation headerNavigation;

    @Find(".navigation .desktop__nav .nav-cart")
    public MiniCart miniCart;
    
    @Find(".footer__top .footer__left")
    public Navigation footerNavigation;

    @Find("#lang-selector")
    public Select languageSelector;

    @Find("#currency-selector")
    public Select currencySelector;

    @Find(".breadcrumb-section .breadcrumb")
    public Optional<Breadcrumbs> breadcrumbs;

    @Find(".ui-autocomplete")
    public WebElement autocompleteContainer;

    public void search(String text) {
        searchInput.search(text);
        searchButton.submit();
    }

    public void searchAutoComplete(String text, String selector) {
        searchInput.search(text);
        WebElement autocomplete = getWait().until(ExpectedConditions.visibilityOf(autocompleteContainer));
        autocomplete.findElement($by(selector)).click();
    }

    public void changeCurrency(String currency) {
        currencySelector.selectByVisibleText(currency);
    }

    public void changeLanguage(String language) {
        languageSelector.selectByVisibleText(language);
    }

}
