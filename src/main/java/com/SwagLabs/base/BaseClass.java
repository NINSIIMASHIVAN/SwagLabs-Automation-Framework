package com.SwagLabs.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.SwagLabs.actiondriver.ActionDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.SwagLabs.utilities.ExtentManager;

public class BaseClass {
	protected static Properties prop;
	//protected static WebDriver driver;
	//private static ActionDriver actionDriver;
	
	 // ThreadLocal means each test thread gets its own WebDriver instance
		private static ThreadLocal<WebDriver>driver=new ThreadLocal<>();
		private static ThreadLocal <ActionDriver> actionDriver=new ThreadLocal<>();

	@BeforeSuite
	public void loadConfig() throws IOException 
	{ 
		//load the configuration file using classloader for better portability
		prop=new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
			prop.load(fis);
			System.out.println("Configuration file loaded successfully");
		} catch (IOException e) {
			System.err.println("Failed to load configuration file: " + e.getMessage());
			throw e;
		}
	}
		
		@BeforeMethod
		public void setup()
		{
			System.out.println("Setting up WebDriver for:"+this.getClass().getSimpleName());
			launchBrowser();
			
			 // Register current thread's driver for Extent Reports
		    ExtentManager.registerDriver(getDriver());
		    
			configureBrowser();
			
			staticWait(2);
			

			//initialize the actionDriver  per thread,if not already set 
		if(actionDriver.get()==null) {
		actionDriver.set(new ActionDriver(getDriver()));
		System.out.println("ActionDriver instance is created");
			
		}}	
	
		private void launchBrowser() {

			String browser = prop.getProperty("browser");

			if(browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				if (Boolean.parseBoolean(prop.getProperty("headless"))) {
					options.addArguments("--headless=new");
				}
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-notifications");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--start-maximized");
				options.addArguments("--ignore-certificate-errors");
				options.addArguments("--disable-extensions");

				// Disable password manager and breach detection popup
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				prefs.put("profile.password_manager_leak_detection", false);
				prefs.put("safebrowsing.enabled", false);
				options.addArguments("--disable-save-password-bubble");
				options.setExperimentalOption("prefs", prefs);

				driver.set(new ChromeDriver(options));
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions options = new FirefoxOptions();

				if(Boolean.parseBoolean(prop.getProperty("headless"))) {
					options.addArguments("-headless");
				}

				options.addArguments("--width=1920");
				options.addArguments("--height=1080");

				// Disable notifications
				options.addPreference("dom.webnotifications.enabled", false);

				// Ignore certificate errors
				options.setAcceptInsecureCerts(true);

				// Disable browser extensions
				options.addPreference("extensions.enabledScopes", 0);

				driver.set(new FirefoxDriver(options));
			}
			else if (browser.equalsIgnoreCase("edge")) {
			    System.setProperty("webdriver.edge.driver", "C:\\edge\\msedgedriver.exe");

			    EdgeOptions options = new EdgeOptions();

			    if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			        options.addArguments("--headless=new");
			    }

			    options.addArguments("--window-size=1920,1080");
			    options.addArguments("--disable-notifications");
			    options.addArguments("--disable-popup-blocking");
			    options.addArguments("--no-sandbox");
			    options.addArguments("--disable-dev-shm-usage");
			    options.addArguments("--ignore-certificate-errors");
			    options.addArguments("--disable-extensions");

			    driver.set(new EdgeDriver(options));
			}
		}
		    // Configure browser settings & open the URL; uses getDriver() for per-thread WebDriver
		
		private void configureBrowser()
		{
		//implement implicit wait
		int implicitWait=Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		
		//maximise the browser
		//getDriver().manage().window().maximize();
		
		
		//Navigate to URL
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL:"+e.getMessage());
		}
		}
		

		@AfterMethod

		//terminating the browser
		public void tearDown() 
		{
			if(driver.get()!=null) 
			{
				try {
					driver.get().quit();
				} catch (Exception e) {
					System.out.println("Failed to quit browser:"+e.getMessage());
					
					
				}
			}
			System.out.println("WebDriver instance is closed");
			driver.remove();
			actionDriver.remove();
			}
	
	
			//Getter for per-thread ActionDriver
			public static ActionDriver getActionDriver() 
			{
				if(actionDriver.get()==null) 
				{
					System.out.println("ActionDriver is not initialized");
					throw new IllegalStateException("ActionDriver is not initialized");
				}
				return  actionDriver.get();
				
			}
			//Getter for per-thread WebDriver
			public static WebDriver getDriver() 
			{
				if(driver.get()==null) 
				{
					System.out.println("WebDriver is  not initialized");
					throw new IllegalStateException("WebDriver is not initialized");
				}
				return driver.get();
				
			}
			//Static wait for pause
		 public void staticWait(int seconds)
		 {
			 LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
		 }

			 //getter method for properties file values,if needed globally
		public static Properties getProp() {
			
			return prop;
		}
}

			
		
			
			
			


	


