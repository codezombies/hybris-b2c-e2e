package com.codingzombies.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import com.codingzombies.support.Find.TransformType;
import com.codingzombies.support.ui.Component;
import com.codingzombies.support.ui.EnhancedElement;
import com.google.common.base.Suppliers;

public class PageElementFieldDecorator extends DefaultFieldDecorator {

    private static final Logger logger = Logger.getLogger(PageElementFieldDecorator.class.getName());

    private final SearchContext driver;

    PageElementFieldDecorator(SearchContext driver, SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
        this.driver = driver;
    }

    PageElementFieldDecorator(SearchContext driver) {
        super(new DefaultElementLocatorFactory(driver));
        this.driver = driver;
    }

    public Object decorate(ClassLoader loader, Field field) {
        Class<?> type = field.getType();
        Find find;
        if ((find = isFindable(field)) == null) {
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

            List<?> proxyForListLocator = proxyForListLocator(driver, find.transform(), (Class<?>) listType, loader, locator);
            return proxyForListLocator;
        }

        WebElement proxy = proxyForLocator(loader, locator);
        if (isSupportedPrimitiveType(type)) {
            return find.transform().transform(proxy.getText());
        } else if (Optional.class.isAssignableFrom(type)) {
            return createOptionalValue(field, find, proxy, loader, locator);
        } else if (Supplier.class.isAssignableFrom(type)) {
            return createSupplierValue(field, find, proxy, loader, locator);
        }

        return ClassSupport.newInstance(type, (WebDriver) driver, proxy);
    }

    private Supplier<?> createSupplierValue(Field field, Find find, WebElement proxy, ClassLoader loader, ElementLocator locator) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            throw new IllegalStateException("Supplier does not contain a parameter type");
        }
        
        Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
        return createSupplierValue(field, find, proxy, loader, locator, type);
    }
    
    private Supplier<?> createSupplierValue(Field field, Find find, WebElement proxy, ClassLoader loader, ElementLocator locator, Type type) {
        if(type instanceof Class) {
            Class<?> supplierType = (Class<?>) type;
            if (isSupportedPrimitiveType(supplierType)) {
                return Suppliers.memoize(() -> find.transform().transform(proxy.getText()));
            } 
            else if (isSupportedComplexType(supplierType)) {
                return Suppliers.memoize(() -> ClassSupport.newInstance(supplierType, (WebDriver) driver, proxy));
            }
        }
        // e.g. Supplier<List<E>>, Supplier<Optional<T>>
        else if (type instanceof ParameterizedType) {
            Class<?> rawType = (Class<?>) ((ParameterizedType) type).getRawType();
            if (Optional.class.isAssignableFrom(rawType)) {
                Type parameterType = ((ParameterizedType) type).getActualTypeArguments()[0];
                return Suppliers.memoize(() -> createOptionalValue(field, find, proxy, loader, locator, parameterType));
            }
            else if (List.class.isAssignableFrom(rawType)) {
                Type parameterType = ((ParameterizedType) type).getActualTypeArguments()[0];
                // Suplier<List<E>> will only work if E is not a ParameterizedType, e.g. Optional
                // Supplier<List<String>> will work but Supplier<List<Optional<String>> won't work
                if(parameterType instanceof Class) {
                    return Suppliers.memoize(() -> proxyForListLocator(driver, find.transform(), (Class<?>) parameterType, loader, locator));
                }
            }
        }
        throw new IllegalArgumentException("Parameter type passed in supplier is not supported");
    }

    
    
    private Optional<?> createOptionalValue(Field field, Find find, WebElement proxy, ClassLoader loader, ElementLocator locator) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            throw new IllegalStateException("Optional does not contain a parameter type");
        }
        
        Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
        return createOptionalValue(field, find, proxy, loader, locator, type);
    }
    
    private Optional<?> createOptionalValue(Field field, Find find, WebElement proxy, ClassLoader loader, ElementLocator locator, Type type) {
        if(type instanceof Class) {
            Class<?> optionalType = (Class<?>) type;
            if (isSupportedPrimitiveType(optionalType)) {
                Object value = null;
                try {
                    value = find.transform().transform(proxy.getText());
                } catch (Exception e) {
                    logger.log(Level.FINE, "Optional value cannot be found: " + e.getMessage());
                }
                return Optional.ofNullable(value);
            } else if (isSupportedComplexType(optionalType)) {
                Object instance = null;
                try {
                    Method method = proxy.getClass().getMethod("getWrappedElement");
                    instance = ClassSupport.newInstance(optionalType, (WebDriver) driver,
                            (WebElement) method.invoke(proxy));
                } catch (Exception e) {
                    logger.log(Level.FINE, "Optional value cannot be found: " + e.getMessage());
                }
                return Optional.ofNullable(instance);
            }
        }
        else {
            Class<?> rawType = (Class<?>) ((ParameterizedType) type).getRawType();
            if (Supplier.class.isAssignableFrom(rawType)) {
                Type parameterType = ((ParameterizedType) type).getActualTypeArguments()[0];
                return Optional.ofNullable(createSupplierValue(field, find, proxy, loader, locator, parameterType));
//                return Suppliers.memoize(() -> createOptionalValue(field, find, proxy, parameterType));
            }
            else if (List.class.isAssignableFrom(rawType)) {
                Type parameterType = ((ParameterizedType) type).getActualTypeArguments()[0];
                // Optional<List<E>> will only work if E is not a ParameterizedType, e.g. Supplier
                // Optional<List<String>> will work but Supplier<Optional<Supplier<String>> won't work
                if(parameterType instanceof Class) {
                    return Optional.ofNullable(proxyForListLocator(driver, find.transform(), (Class<?>) parameterType, loader, locator));
                }
                
            }
        }
        throw new IllegalArgumentException("Parameter type passed in optional is not supported");
    }
    

    @SuppressWarnings("unchecked")
    protected <T> List<T> proxyForListLocator(SearchContext searchContext, TransformType transformType, Class<T> clazz,
            ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementListHandler(locator);
        List<WebElement> proxy = (List<WebElement>) Proxy.newProxyInstance(loader, new Class[] { List.class }, handler);
        return proxy.stream().map(element -> {
            if(isSupportedPrimitiveType(clazz)) {
                return (T)transformType.transform(element.getText());
            }
            else {                
                return ClassSupport.newInstance(clazz, (WebDriver) searchContext, element);
            }
        }).collect(Collectors.toList());
    }

    private boolean isSupportedPrimitiveType(Class<?> type) {
        return String.class.isAssignableFrom(type) 
                || Integer.class.isAssignableFrom(type)
                || Long.class.isAssignableFrom(type) 
                || Double.class.isAssignableFrom(type);
    }
    
    private boolean isSupportedComplexType(Class<?> type) {
        return Component.class.isAssignableFrom(type)
                || EnhancedElement.class.isAssignableFrom(type)
                || WebElement.class.isAssignableFrom(type);
    }
    
    private Find isFindable(Field field) {
        Annotation[] annotations = field.getAnnotations();
        if (annotations.length == 0)
            return null;

        for (Annotation annotation : annotations) {
            if (Find.class.isAssignableFrom(annotation.annotationType())) {
                return (Find) annotation;
            }
        }
        return null;
    }
}
