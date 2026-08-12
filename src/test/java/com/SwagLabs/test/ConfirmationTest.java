package com.SwagLabs.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.SwagLabs.base.BaseClass;
import com.SwagLabs.pages.CheckOutPage;
import com.SwagLabs.pages.ConfirmationPage;
import com.SwagLabs.pages.InventoryPage;
import com.SwagLabs.pages.LoginPage;

public class ConfirmationTest extends BaseClass {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CheckOutPage checkOutPage;
    private ConfirmationPage confirmationPage;

    @BeforeMethod
    public void setupPagesAndCompleteCheckout() {
        loginPage = new LoginPage(getDriver());
        inventoryPage = new InventoryPage(getDriver());
        checkOutPage = new CheckOutPage(getDriver());
        confirmationPage = new ConfirmationPage(getDriver());

        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        checkOutPage.clickCartIcon();
        checkOutPage.clickCheckOutButton();
        checkOutPage.checkoutWithAllFieldDetails("Mercy", "Grace", "12345");
        checkOutPage.clickFinishButton();
    }

    @Test
    public void verifyConfirmationPageTitle() {
        Assert.assertTrue(confirmationPage.isPageTitleCorrect("Checkout: Complete!"),
            "Confirmation page title did not match expected value");
    }

    @Test
    public void verifyOrderCompleteMessageDisplayed() {
        Assert.assertTrue(confirmationPage.isCompleteOrderMessageDisplayed(),
            "'Thank you for your order!' message was not displayed");
    }

    @Test
    public void verifyReturningHomeNavigatesToInventoryPage() {
        confirmationPage.clickBackHome();

        Assert.assertTrue(inventoryPage.isPageFullyLoaded(),
            "Clicking Back Home did not return to the Inventory page");
    }
}