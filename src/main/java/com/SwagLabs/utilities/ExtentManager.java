package com.SwagLabs.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.SwagLabs.base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap =
	        new ConcurrentHashMap<>();
//Initialize the extent report
	public static ExtentReports getReporter() 
	{
		if (extent==null) 
		{
			String reportPath=System.getProperty("user.dir")+"\\src\\test\\resources\\ExtentReports.html";
			ExtentSparkReporter spark=new ExtentSparkReporter(reportPath);
		//nb.spark is the report generator
		spark.config().setReportName("SwagLabs Automation Report");
		spark.config().setDocumentTitle("SwagLabs Report");
		spark.config().setTheme(Theme.DARK);
		
		//nb.extent is the reporting engine,“ExtentReports, use SparkReporter to generate the actual HTML report.”
		extent=new ExtentReports();
		//adding system info
		extent.attachReporter(spark);
		extent.setSystemInfo("operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Browser", BaseClass.getProp().getProperty("browser"));
       
		}
		return extent;
	}

	//Start creating tests
	public static ExtentTest startTest(String testName) 
	{
		
		ExtentTest extentTest=getReporter().createTest(testName);
		test.set(extentTest);//“ it tell us to Store this ExtentTest object separately for the CURRENT THREAD.”
		return extentTest;
		
	}
	
	//end test
	/*public static void endTest()
	{
		extent.flush();	
	}*/
	
	//Get current Thread test
	public static ExtentTest getTest() 
	{
		return test.get();	
	}
//Method to get the name of the current test
	public static  String getTestName() 
	{
		ExtentTest currentTest=getTest();
		if (currentTest!=null) 
		{
			return currentTest.getModel().getName();
		}
		else {
		return "No test is currently active for this thread";
		}}
	
	//log a step
	public static void logStep(String logMessage) 
	{
	getTest().info(logMessage);	
	
	}
	
	//log a step validation with screenshot
	public static void logStepWithScreenshot(WebDriver driver,String logMessage,String screenshot) 
	{
		getTest().pass(logMessage);
		//Screenshot method
		attachScreenshot(driver,logMessage);
	}
	
	//log a failure
	public static void logFailure(WebDriver driver,String logMessage,String screenshot) 
	{
		getTest().fail(logMessage);
		//Screenshot method
		attachScreenshot(driver,logMessage);
	}
	
	//log a skip
	public static void logSkip(String logMessage) 
	{
		getTest().skip(logMessage);
	}
	
	//Take a screenshot with date and time in the file 
	public static String takesScreenShotWithTime(WebDriver driver,String screenShotName) 
	{
	TakesScreenshot ts=	(TakesScreenshot)driver;
	File src=ts.getScreenshotAs(OutputType.FILE);
	//Format date and Time for fileName
	String timeStamp=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	
	//saving the screenshot to a file
	String destPath=System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots\\"
	+screenShotName+ "-" +timeStamp+".png";
	
	File finalPath= new File(destPath);
	try {
		FileUtils.copyFile(src, finalPath);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	//convert screenshot to Base64 for embadding in the report
	String base64Format=convertToBase64(src);
	return base64Format;
	}
	//Convert screenshot to Base64Format="";
	public static String convertToBase64(File screenShotFile) 
	{
		String base64Format="";
		//Read the file content into byte array
		
		try {
			byte[]fileContent = FileUtils.readFileToByteArray(screenShotFile);
			base64Format=Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//convert the byte array to Base64 string
		
		return base64Format ;
	}
	
	//Attach screenshot to report using Base64
	public static void attachScreenshot(WebDriver driver, String message) 
	{try 
		{
		String screenShotBase64=takesScreenShotWithTime(driver,getTestName());
		getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.
				createScreenCaptureFromBase64String(screenShotBase64).build());
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		getTest().fail("Failed to attach screenshot"+ message);
		e.printStackTrace();
	}
}
//Register WebDriver for current Thread
	public static void registerDriver(WebDriver driver) 
	{
		driverMap.put(Thread.currentThread().getId(), driver);
}}
