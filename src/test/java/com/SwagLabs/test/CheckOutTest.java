package com.SwagLabs.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.SwagLabs.base.BaseClass;
import com.SwagLabs.pages.CheckOutPage;
import com.SwagLabs.pages.InventoryPage;
import com.SwagLabs.pages.LoginPage;
import com.SwagLabs.utilities.DataProviders;
import com.SwagLabs.utilities.DataProviders;

public class CheckOutTest extends BaseClass {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CheckOutPage checkOutPage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        inventoryPage = new InventoryPage(getDriver());
        checkOutPage = new CheckOutPage(getDriver());

        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

        // Checkout flow needs at least one item in the cart to reach checkout
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        checkOutPage.clickCartIcon();
        checkOutPage.clickCheckOutButton();
    }

    @Test
    public void verifyCheckoutFailsWithoutFirstName() {
        checkOutPage.checkoutWithoutFirstName("Mercy", "2345");

        Assert.assertTrue(checkOutPage.isErrorMessageDisplayed(),
            "Expected an error when First Name is missing");

        String actualError = checkOutPage.getErrorMessageText();
        String expectedError = "Error: First Name is required";
        Assert.assertEquals(actualError, expectedError,
            "Error message did not match for missing First Name");
    }

    @Test
    public void verifyCheckoutFailsWithoutLastName() {
        checkOutPage.checkoutWithoutLastName("Grace", "2345");

        Assert.assertTrue(checkOutPage.isErrorMessageDisplayed(),
            "Expected an error when Last Name is missing");

        String actualError = checkOutPage.getErrorMessageText();
        String expectedError = "Error: Last Name is required";
        Assert.assertEquals(actualError, expectedError,
            "Error message did not match for missing Last Name");
    }

    @Test
    public void verifyCheckoutFailsWithoutPostalCode() {
        checkOutPage.checkoutWithoutPostalCode("Grace", "Mercy");

        Assert.assertTrue(checkOutPage.isErrorMessageDisplayed(),
            "Expected an error when Postal Code is missing");

        String actualError = checkOutPage.getErrorMessageText();
        String expectedError = "Error: Postal Code is required";
        Assert.assertEquals(actualError, expectedError,
            "Error message did not match for missing Postal Code");
    }

    @Test(dataProvider = "checkoutData", dataProviderClass =DataProviders.class)
    
    public void verifyCheckoutSucceedsWithValidDetails(String firstName, String lastName, String postalCode) {
        checkOutPage.checkoutWithAllFieldDetails(firstName, lastName,postalCode);
        checkOutPage.clickFinishButton();

        Assert.assertTrue(checkOutPage.isOrderConfirmed(),
            "Order confirmation was not shown after completing checkout");

        System.out.println("Checkout completed successfully for: " + firstName + " " + lastName);
    }
}