package com.codingzombies.support;

import java.io.IOException;
import java.util.Properties;

public final class Config {

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(Config.class.getResourceAsStream("/test.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("test.properties not found on the classpath");
        }
    }

    private Config() {
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}
