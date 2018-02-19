package com.codingzombies.support;

import org.openqa.selenium.By;

public final class Selector {

    public static By $by(String selector) {
        if(selector.startsWith("#")) {
            return By.id(selector.substring(1));
        }
        else if (selector.startsWith("=")) {
            return By.partialLinkText(selector.substring(1));
        }
        else if (selector.startsWith("//") || selector.startsWith("./")) {
            return By.xpath(selector);
        }

        return By.cssSelector(selector);
    }
    
}
