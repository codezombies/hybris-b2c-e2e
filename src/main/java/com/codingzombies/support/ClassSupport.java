package com.codingzombies.support;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.Component;
import com.codingzombies.support.ui.EnhancedElement;

class ClassSupport {

    @SuppressWarnings("unchecked")
    static <T> T newInstance(Class<T> clazz, WebDriver webDriver, WebElement element) {
        if (Component.class.isAssignableFrom(clazz)) {            
            return PageFactory.newComponent(webDriver, element, clazz);
        } else if (EnhancedElement.class.isAssignableFrom(clazz)) {
            try {
                return clazz.getConstructor(WebDriver.class, WebElement.class).newInstance(webDriver, element);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return (T)element;
    }
    
}
