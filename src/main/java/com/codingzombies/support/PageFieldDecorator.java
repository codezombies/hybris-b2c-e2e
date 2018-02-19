package com.codingzombies.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

class PageFieldDecorator extends DefaultFieldDecorator {

    private SearchContext searchContext;

    PageFieldDecorator(SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
        this.searchContext = searchContext;
    }

    public Object decorate(ClassLoader loader, Field field) {
        Class<?> type = field.getType();
        if(!isFindable(field)) {
            return null;
        }
        
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        if (List.class.isAssignableFrom(type)) {
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                return false;
            }
            Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

            List<?> proxyForListLocator = proxyForListLocator(searchContext, (Class<?>) listType, loader, locator);
            return proxyForListLocator;
        }

        WebElement proxy = proxyForLocator(loader, locator);
        return ClassSupport.newInstance(type, (WebDriver) searchContext, proxy);
    }

    protected <T> List<T> proxyForListLocator(SearchContext searchContext, Class<T> clazz, ClassLoader loader,
            ElementLocator locator) {
        InvocationHandler handler = new LocatingElementListHandler(locator);
        @SuppressWarnings("unchecked")
        List<WebElement> proxy = (List<WebElement>) Proxy.newProxyInstance(loader, new Class[] { List.class }, handler);
        return proxy.stream().map(element -> {
//            System.out.println(element.getText());
            return ClassSupport.newInstance(clazz, (WebDriver) searchContext, element);
        }).collect(Collectors.toList());
    }
    
    private boolean isFindable(Field field) {
        Annotation[] annotations = field.getAnnotations();
        if(annotations.length == 0) return false;
        
        for (Annotation annotation : annotations) {
            if(Find.class.isAssignableFrom(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

}
