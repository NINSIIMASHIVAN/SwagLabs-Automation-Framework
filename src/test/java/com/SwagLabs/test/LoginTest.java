package com.SwagLabs.test;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.SwagLabs.pages.LoginPage;
import com.SwagLabs.base.BaseClass;


public class LoginTest extends BaseClass {
    
	private LoginPage loginPage;
    
   
    @BeforeMethod
    public void setupPages() {
    	loginPage = new LoginPage(getDriver());
    }	
    	
    @Test(priority = 1)
    public void verifyValidLoginTest() {
		String userName = prop.getProperty("username");
		String password = prop.getProperty("password");
	  
		loginPage.login(userName, password);
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("inventory.html"));
		
		String currentUrl = getDriver().getCurrentUrl();
		Assert.assertTrue(
				currentUrl.contains("inventory.html"),
				"URL should contain 'inventory.html' after successful login.");
		
		String pageTitle = getDriver().getTitle();
		Assert.assertNotNull(pageTitle, "Page title should not be null.");
		
		System.out.println("✓ Valid login successful");
		System.out.println("  Current URL: " + currentUrl);
		System.out.println("  Page title: " + pageTitle);
		System.out.println("  Logged in user: " + userName);
    }
      
    @Test(priority = 2)  
    public void verifyLoginFailureWithNoCredentials() {
		loginPage.login("", "");

		boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
		Assert.assertTrue(
				errorDisplayed,
				"Expected an error message when logging in with no credentials.");

		String actualError = loginPage.getErrorMessageText();
		String expectedError = "Epic sadface: Username is required";
		Assert.assertEquals(
				actualError,
				expectedError,
				"Error message text should match expected for empty credentials.");

		System.out.println("✓ Login correctly rejected with no credentials");
		System.out.println("  Error shown: " + actualError);
    }
  
    @Test(priority = 3)
    public void verifyLoginFailureWithNoUsername() {
		String password = prop.getProperty("password");
		loginPage.login("", password);

		boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
		Assert.assertTrue(
				errorDisplayed,
				"Expected an error message when username is missing.");

		String actualError = loginPage.getErrorMessageText();
		String expectedError = "Epic sadface: Username is required";
		Assert.assertEquals(
				actualError,
				expectedError,
				"Error message should indicate username is required.");

		System.out.println("✓ Login correctly rejected with no username");
		System.out.println("  Error shown: " + actualError);
    }

    @Test(priority = 4)
    public void verifyLoginFailureWithNoPassword() {
		String userName = prop.getProperty("username");
		loginPage.login(userName, "");

		boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
		Assert.assertTrue(
				errorDisplayed,
				"Expected an error message when password is missing.");

		String actualError = loginPage.getErrorMessageText();
		String expectedError = "Epic sadface: Password is required";
		Assert.assertEquals(
				actualError,
				expectedError,
				"Error message should indicate password is required.");

		System.out.println("✓ Login correctly rejected with no password");
		System.out.println("  Error shown: " + actualError);
    }
    
    @Test(priority = 5)
    public void verifyLoginFailureWithInvalidCredentials() {
		loginPage.login("invalid_user", "invalid_pass");

		boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
		Assert.assertTrue(
				errorDisplayed,
				"Expected an error message when using invalid credentials.");

		String actualError = loginPage.getErrorMessageText();
		Assert.assertTrue(
				actualError.contains("Epic sadface"),
				"Error message should contain 'Epic sadface' for invalid credentials.");

		System.out.println("✓ Login correctly rejected with invalid credentials");
		System.out.println("  Error shown: " + actualError);
    }
}
