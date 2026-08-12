package com.SwagLabs.listeners;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.IAnnotationTransformer;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.IAnnotation;
import org.testng.annotations.ITestAnnotation;

import com.SwagLabs.base.BaseClass;
import com.SwagLabs.utilities.ExtentManager;
import com.SwagLabs.utilities.ScreenshotUtil;


public class TestListener implements ITestListener{ //IAnnotationTransformer{
/*//to get the unimplemented methods of IAnnotationTransformer , right click on it, go to source and the override unimplementedmethods
    @Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
			Method testMethod) {
		
annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}*/

	@Override
    public void onStart(ITestContext context) {

        ExtentManager.getReporter();
        //to initialize the extent reports
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	 ExtentManager.getTest()
         .pass("Test Passed");

 /*ExtentManager.attachScreenshot(
         BaseClass.getDriver(),
         "Success Screenshot");*/
    	 }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentManager.getTest()
                .fail(result.getThrowable());

       /* try {

            ExtentManager.attachScreenshot(
                    BaseClass.getDriver(),
                    "Failure Screenshot");

        } catch (Exception e) {

            ExtentManager.getTest()
                    .warning("Unable to capture screenshot");
        }*/
    }
    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentManager.getTest().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
       //Flush the extent report 
        ExtentManager.getReporter().flush();
    }
}


