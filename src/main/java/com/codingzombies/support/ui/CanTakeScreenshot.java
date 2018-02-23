package com.codingzombies.support.ui;

import java.io.File;

import org.openqa.selenium.WebDriverException;

public interface CanTakeScreenshot {
    File getScreenshot() throws WebDriverException;
}
