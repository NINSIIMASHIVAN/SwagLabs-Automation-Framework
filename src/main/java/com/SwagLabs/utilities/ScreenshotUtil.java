package com.SwagLabs.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.SwagLabs.base.BaseClass;

public class ScreenshotUtil {
	public static String takeScreenshot(String testName)
            throws IOException {

        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());

        String screenshotPath =
                System.getProperty("user.dir")
                + "\\Screenshots\\"
                + testName + "_" + timestamp + ".png";

        File source =
                ((TakesScreenshot) BaseClass.getDriver())
                        .getScreenshotAs(OutputType.FILE);

        File destination = new File(screenshotPath);

        FileUtils.copyFile(source, destination);

        return screenshotPath;
    }

	public static void highlightElement(WebDriver driver, WebElement body, String string) {
		// TODO Auto-generated method stub
		
	}
}

