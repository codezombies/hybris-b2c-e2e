package com.codingzombies.support.driver;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codingzombies.support.EnhancedWebDriver;

public final class DriverFactory {

    private DriverFactory() {
    }
    
    public static EnhancedWebDriver headless() {
        setDriverPath("phantomjs.binary.path", "phantomjs");
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Arrays.asList("--ignore-ssl-errors=true", "--ssl-protocol=TLSv1"));
        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        return new EnhancedWebDriver(driver);
    }
    
    public static EnhancedWebDriver firefox() {
        setDriverPath("webdriver.gecko.driver", "geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setLogLevel(FirefoxDriverLogLevel.ERROR);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        
        FirefoxDriver driver = new FirefoxDriver(options);
        return new EnhancedWebDriver(driver);
    }

    public static EnhancedWebDriver chrome() {
        setDriverPath("webdriver.chrome.driver", "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        
        ChromeDriver driver = new ChromeDriver(options);
        return new EnhancedWebDriver(driver);
    }
    
    private static void setDriverPath(String key, String driver) {
        System.setProperty(key, getDriverPath(driver));
    }
    
    private static String getDriverPath(String driver) {
        final String resourcePath = DriverFactory.class.getResource("/").getPath();
        final String osname = System.getProperty("os.name").toLowerCase();
        
        String filename = driver;
        final String directory;
        if (osname.contains("win")) {
            filename = driver + ".exe";
            directory = "windows";
        } else if (osname.contains("mac os")) {
            directory = "osx";
        } else if (osname.contains("linux")) {
            String osarch = System.getProperty("os.arch");
            if (osarch.equals("i386")) {
                directory = "linux86";
            } else {
                directory = "linux64";
            }
        } else {
            throw new IllegalArgumentException("Unsupported OS " + osname);
        }
        
        File executable = Paths.get(resourcePath)
            .resolve("drivers")
            .resolve(driver)
            .resolve(directory)
            .resolve(filename)
            .toAbsolutePath().toFile();
                
        if(!"windows".equals(directory) && !executable.setExecutable(true)) {
            throw new IllegalStateException("failed to set driver as executable on path: " + executable.getAbsolutePath());
        }
        
        return executable.getAbsolutePath();
    }
}
