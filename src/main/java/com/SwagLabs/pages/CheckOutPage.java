package com.SwagLabs.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SwagLabs.actiondriver.ActionDriver;

public class CheckOutPage {


	WebDriver driver;
	ActionDriver actionDriver;
	
	
	public CheckOutPage(WebDriver driver) {
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}
	
	
	//locators(click on cart icon->checkout->overview page)
	private By cartIcon=By.cssSelector("a[class='shopping_cart_link']");
	private By checkoutButton=By.cssSelector("button[data-test='checkout']");// wait for url to be "https://www.saucedemo.com/checkout-step-one.html"
	private By firstNameField=By.cssSelector("input[placeholder='First Name']");
	private By lastNameField=By.cssSelector("input[placeholder='Last Name']");
	private By postalCodeField=By.id("postal-code");
	private By continueButton=By.cssSelector("input[class='submit-button btn btn_primary cart_button btn_action']");
	private By firstNameRequiredErrorMessage=By.cssSelector("h3[data-test='error']");
			
	private By finishButton=By.id("finish");
	
	//methods
	//what happens with missing first name,last name,postal code ,all fields .
	//work with all fields correctly filled in 
	
	
	public void clickCartIcon() 
	{
		actionDriver.click(cartIcon);
	}
	public void clickCheckOutButton() 
	{
		actionDriver.click(checkoutButton);
	}
	public void enterFirstName(String firstName) {
	    actionDriver.enterText(firstNameField, firstName);
	}

	public void enterLastName(String lastName) {
	    actionDriver.enterText(lastNameField, lastName);
	}

	public void enterPostalCode(String postalCode) {
	    actionDriver.enterText(postalCodeField, postalCode);
	}
	public void clickContinueButton()
	{
		actionDriver.click(continueButton);
	}
	public void clickFinishButton() 
	{
		actionDriver.click(finishButton);
	}
	
	public void checkoutWithoutFirstName(String lastName, String postalCode) {

	    actionDriver.enterText(lastNameField, lastName);
	    actionDriver.enterText(postalCodeField, postalCode);
	    actionDriver.click(continueButton);

	}
	
	public void checkoutWithoutLastName(String firstName, String postalCode) {

	    actionDriver.enterText(firstNameField, firstName);
	    actionDriver.enterText(postalCodeField, postalCode);
	    actionDriver.click(continueButton);

	}
	public void checkoutWithoutPostalCode(String firstName, String lastName) {

	    actionDriver.enterText(firstNameField, firstName);
	    actionDriver.enterText(lastNameField, lastName);
	    actionDriver.click(continueButton);

	}
	
	public void checkoutWithAllFieldDetails(String firstName, String lastName, String postalCode)
	{
	    actionDriver.enterText(firstNameField, firstName);
	    actionDriver.enterText(lastNameField, lastName);
	    actionDriver.enterText(postalCodeField, postalCode);
	    actionDriver.click(continueButton);
	}
	public boolean isErrorMessageDisplayed() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameRequiredErrorMessage));
	        return true;
	    } catch (Exception e) {
	        System.out.println("No error message appeared: " + e.getMessage());
	        return false;
	    }
	}

	public String getErrorMessageText() {
	    return driver.findElement(firstNameRequiredErrorMessage).getText();
	}

	public boolean isOrderConfirmed() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        By confirmationHeader = By.className("complete-header"); // "Thank you for your order!"
	        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeader));
	        return true;
	    } catch (Exception e) {
	        System.out.println("Order confirmation not shown: " + e.getMessage());
	        return false;
	    }
	}
}
