package com.SwagLabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.SwagLabs.actiondriver.ActionDriver;

public class LoginPage {


	WebDriver driver;
	ActionDriver actionDriver;
	
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}	
	
	//locators
	private By Username=By.cssSelector("input[placeholder='Username']");
	private By Password=By.cssSelector("input[data-test='password']");
	private By LoginButton=By.cssSelector("input[class='submit-button btn_action']");
	private By errorMessage=By.cssSelector("h3[data-test='error']");
	

//methods 
	//method to enter Username
	public void enterUsername (String username) 
	{
		actionDriver.enterText(Username, username);	
	}

public void enterPassword (String password) 
	{
		actionDriver.enterText(Password, password);	
	}
	
	public void clickLoginButton() 
	{
		actionDriver.click(LoginButton);
	}
	public void login(String username,String password) 
	{
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
	}
	
	//method to check if error message is displayed
	public boolean isErrorMessageDisplayed() 
	{
		return actionDriver.isDisplayed(errorMessage);
	}

	//method to get text from error message
	public String getErrorMessageText() 
	{
	    String text = actionDriver.getText(errorMessage);
	    System.out.println("Error message from page: '" + text + "'");
	    return text;
	}


	//method to verify if error is correct or not
	public boolean verifyErrorMessage(String expectedError) 
	{
		return actionDriver.compareText(errorMessage, expectedError);
	}

	
}
