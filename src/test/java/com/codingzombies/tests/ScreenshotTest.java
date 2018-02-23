package com.codingzombies.tests;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.codingzombies.support.Config;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codingzombies.support.Browser;
import com.codingzombies.support.driver.DriverFactory;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;

import javax.imageio.ImageIO;

public class ScreenshotTest {


    private String rootUrl = Config.get("rootUrl");
    private String failureImageDirectory = Config.get("screenshots.failed.directory");

    private String rootImagePath;
    private Browser browser;
    
    @BeforeTest
    public void setUp() {
        browser = new Browser(DriverFactory.headless());
        
        rootImagePath = ScreenshotTest.class.getResource("/screenshots").getPath();
    }
    
    @AfterTest
    public void tearDown() {
        browser.close();
    }
    
    @Test(dataProvider = "screenshots")
    public void testUIChanges(String device, String imageToCompare, String url) throws IOException {
        browser.resize(device);
        browser.get(rootUrl + url);

        // we need to forcefully sleep to make sure that animations for images are complete
        sleep(500);

        final File referenceImage = Paths.get(rootImagePath, device, imageToCompare).toFile();
        final File actualImage = browser.takeScreenshot();

        final ImageDiffer differ = new ImageDiffer().withDiffMarkupPolicy(new PointsMarkupPolicy().withDiffColor(Color.GREEN));
        ImageDiff diff = differ.makeDiff(ImageIO.read(referenceImage), ImageIO.read(actualImage));

        boolean isDifferent = diff.hasDiff() && diff.getDiffSize() > 100 ;
        if(isDifferent) {

        }
        Assert.assertFalse(isDifferent, "there is an image difference for image " + imageToCompare + " for device " + device);
    }

    @DataProvider(name = "screenshots")
    public Object[][] imagesData() {
     return new Object[][] {
       { "desktop"    , "homepage.png"    , "/" },
       { "tablet"     , "homepage.png"    , "/" },
       { "mobile"     , "homepage.png"    , "/" },
       { "desktop"    , "loginpage.png"   , "/en/login" },
       { "tablet"     , "loginpage.png"   , "/en/login" },
       { "mobile"     , "loginpage.png"   , "/en/login" },
       { "desktop"    , "listingpage.png" , "/en/open-catalogue/cameras/digital-cameras/c/575" },
       { "tablet"     , "listingpage.png" , "/en/open-catalogue/cameras/digital-cameras/c/575" },
       { "mobile"     , "listingpage.png" , "/en/open-catalogue/cameras/digital-cameras/c/575" },
       { "desktop"    , "detailpage.png"  , "/en/open-catalogue/cameras/digital-cameras/digital-compacts/cyber-shot-w55/p/816802" },
       { "tablet"     , "detailpage.png"  , "/en/open-catalogue/cameras/digital-cameras/digital-compacts/cyber-shot-w55/p/816802" },
       { "mobile"     , "detailpage.png"  , "/en/open-catalogue/cameras/digital-cameras/digital-compacts/cyber-shot-w55/p/816802" },
     };
    }

    private void saveImageDifference(String device, String imageName, File referenceImage, File actualImage, ImageDiff diff) {
        if(failureImageDirectory != null && !failureImageDirectory.isEmpty()) {
            try {
                final BufferedImage markedImage = diff.getMarkedImage();

                ImageIO.write(markedImage, "png", Paths.get(failureImageDirectory, createFilename(device, imageName, "")).toFile());

                BufferedImage transparentMarkedImage = diff.getTransparentMarkedImage();
                ImageIO.write(transparentMarkedImage, "png", Paths.get(failureImageDirectory, createFilename(device, imageName, "transparent")).toFile());

                ImageIO.write(ImageIO.read(referenceImage), "png", Paths.get(failureImageDirectory, createFilename(device, imageName, "reference")).toFile());
                ImageIO.write(ImageIO.read(actualImage), "png", Paths.get(failureImageDirectory, createFilename(device, imageName, "actual")).toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String createFilename(String device, String imageName, String infix) {
        if(infix == null || infix.isEmpty()) {
            return device + "_" + imageName;
        }
        return device + "_" + infix + "_" + imageName;
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
