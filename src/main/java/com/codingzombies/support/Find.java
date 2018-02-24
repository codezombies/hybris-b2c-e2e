package com.codingzombies.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(Find.FindBuilder.class)
public @interface Find {

    String value();
    
    TransformType transform() default TransformType.Identity;
    
    class FindBuilder extends AbstractFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            Find findBy = (Find) annotation;
            return Selector.$by(findBy.value());
        }
    }

    public static enum NotFoundHandler {
        ERROR,
        IGNORE; // will ignore error but the error will be logged
    }
    
    public static enum TransformType {
        Identity,
        IntegerOnly {
            @Override
            public Object transform(String text) {
                return Integer.parseInt(text.replaceAll("\\D+", ""));
            }
        },
        LongOnly {
            @Override
            public Object transform(String text) {
                return Long.parseLong(text.replaceAll("\\D+", ""));
            }
        },
        DoubleOnly {
            @Override
            public Object transform(String text) {
                return Double.parseDouble(text.replaceAll("[^[0-9]\\.]+", ""));
            }
        }
        ;
        
        private TransformType() {}
        
        public Object transform(String text) {
            return text.trim();
        }
    }
    
}
