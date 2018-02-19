package com.codingzombies.support;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;

import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(Find.FindBuilder.class)
public @interface Find {

    String value();

    class FindBuilder extends AbstractFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            Find findBy = (Find) annotation;
            return Selector.$by(findBy.value());
        }

    }

}
