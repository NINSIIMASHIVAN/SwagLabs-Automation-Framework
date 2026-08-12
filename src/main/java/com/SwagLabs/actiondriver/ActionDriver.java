package com.SwagLabs.actiondriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SwagLabs.base.BaseClass;

public class ActionDriver {

	private  WebDriver driver;
	private WebDriverWait wait;//we put this because we are going to use explicit wait.
	
	
	//initialize the above 2 declared variables while forming a constructor for this class
	public ActionDriver(WebDriver driver) 
	{this.driver=driver;
    int explicitWait=Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
	
	this.wait=new WebDriverWait(driver,Duration.ofSeconds(explicitWait));
	}
	
	//method to click an element
	public void click(By by)
	{
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			System.out.println("Element clicked successfully");
		} catch (Exception e) {
			System.out.println("Unable to click element: " + e.getMessage());
		}
	}
	
	public void waitForElement(By by)
	{
	    wait.until(
	        ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	//method to select a dropdown by visible text
	public void selectByVisibleText(By by, String value) 
	{
		try {
			WebElement element = driver.findElement(by);
			new Select(element).selectByVisibleText(value);
			System.out.println("Selected dropdown value: " + value);
		} catch (Exception e) {
			System.out.println("Unable to select dropdown value: " + value + ", Error: " + e.getMessage());
		}
	}


	//method to enter text into field
	
	
	public void enterText(By by, String value) 
	{
		try {
			waitForElementToBeVisible(by);
			WebElement element=driver.findElement(by);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			
			System.out.println("Unable to enter the value in input field;" +e.getMessage());
		}
	}
	
	//method to get text from an input field
	public String getText(By by) 
	{
		try {
			waitForElementToBeVisible(by); 
			
			return driver.findElement(by).getText();
		} catch (Exception e) {
			
			System.out.println("Unable to get Text;" +e.getMessage());
		}
		return " ";
	}
	
	//method to wait for page to load 
	public void waitForPageToLoad() {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        for (int i = 0; i < 20; i++) {
	            String state = js.executeScript("return document.readyState").toString();

	            if (state.equals("complete")) {
	                break;
	            }

	            Thread.sleep(1000);
	        }

	    } catch (Exception e) 
	    {
	    System.out.println("page unable to load:" +e.getMessage());	
	    }
	    }
	
	//method to check if an element is displayed
public boolean isDisplayed(By by) 
	{
		try 
	{ 
		
			waitForElementToBeVisible(by); 
			return driver.findElement(by).isDisplayed(); 
			}
		catch (Exception e)
		{ 
			
			System.out.println("element not displayed:" +e.getMessage()); 
		} 
		return false;
		}
	
	
	

   
	
		//method to scroll to element
		public void scrollToElement(WebElement element) 
		{
			try {
				JavascriptExecutor js=(JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", element);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			System.out.println("unable to locate Element:" +e.getMessage());
			}
		}
		
	//method to compare two texts
	public boolean compareText(By by, String expectedText)
	{
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText().trim();
			if (expectedText.equalsIgnoreCase(actualText)) {
				System.out.println("Texts are matching");
				return true;
			} else {
				System.out.println("Texts are not matching");
				System.out.println("Expected: '" + expectedText + "' | Actual: '" + actualText + "'");
			}
			return false;
		} catch (Exception e) {
			System.out.println("Unable to compare texts: " + e.getMessage());
		}
		return false;
	}	
	//wait for the element to be clickable
	private void waitForElementToBeClickable(By by) { 
	try{
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}catch(Exception e) {
System.out.println("element not clickable;" +e.getMessage());

	}
	}
	
	
	
	//wait for element to be visible
	private void waitForElementToBeVisible(By by) 
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("Element not visible: " + e.getMessage());
		}
	}
}

