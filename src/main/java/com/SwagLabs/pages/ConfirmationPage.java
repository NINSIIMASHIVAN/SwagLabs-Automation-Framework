package com.SwagLabs.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SwagLabs.actiondriver.ActionDriver;

public class ConfirmationPage {

    private WebDriver driver;
    private ActionDriver actionDriver;
    private WebDriverWait wait;

    public ConfirmationPage(WebDriver driver) {

        this.driver = driver;
        this.actionDriver = new ActionDriver(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

   
    // Locators
   

    private By pageTitle = By.className("title");

    private By completeOrderMessage = By.className("complete-header");

    private By completeOrderDescription = By.className("complete-text");

    private By backHomeButton = By.id("back-to-products");

   
    // Verification Methods
  

    public boolean isConfirmationPageLoaded() {

        wait.until(ExpectedConditions.urlContains("checkout-complete"));

        return driver.getCurrentUrl().contains("checkout-complete");

    }

    public boolean isPageTitleCorrect(String expectedTitle) {

        String actualTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle))
                .getText();

        System.out.println("Expected Title : " + expectedTitle);
        System.out.println("Actual Title   : " + actualTitle);

        return actualTitle.equals(expectedTitle);

    }

    public boolean isCompleteOrderMessageDisplayed() {

        String message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(completeOrderMessage))
                .getText();

        return message.equalsIgnoreCase("Thank you for your order!");

    }

    public boolean isOrderDescriptionDisplayed() {

        WebElement description = wait.until(
                ExpectedConditions.visibilityOfElementLocated(completeOrderDescription));

        return description.isDisplayed();

    }

    public boolean isBackHomeButtonDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(backHomeButton))
                .isDisplayed();

    }

   
    // Business Action
   
    public void clickBackHome() {

        wait.until(ExpectedConditions.elementToBeClickable(backHomeButton))
                .click();

    }

    public boolean isReturnedToInventoryPage() {

        wait.until(ExpectedConditions.urlContains("inventory"));

        return driver.getCurrentUrl().contains("inventory");

    }

}