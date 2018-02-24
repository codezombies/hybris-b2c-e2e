package com.codingzombies.support;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codingzombies.support.ui.Component;
import com.codingzombies.support.ui.EnhancedElement;

class ClassSupport {

    private static final Logger logger = Logger.getLogger(ClassSupport.class.getName());
    
    @SuppressWarnings("unchecked")
    static <T> T newInstance(Class<T> clazz, WebDriver webDriver, WebElement element) {
        logger.log(Level.FINEST, "creating instance for class: " + clazz);
        if (Component.class.isAssignableFrom(clazz)) {            
            return PageFactory.newComponent(webDriver, element, clazz);
        } else if (EnhancedElement.class.isAssignableFrom(clazz)) {
            try {
                return clazz.getConstructor(WebDriver.class, WebElement.class).newInstance(webDriver, element);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                logger.log(Level.SEVERE, "Cannot instantiate class: " + clazz.getName());
            }
        }
        return (T)element;
    }
    
}
