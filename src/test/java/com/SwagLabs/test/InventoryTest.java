package com.SwagLabs.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.SwagLabs.base.BaseClass;
import com.SwagLabs.pages.InventoryPage;
import com.SwagLabs.pages.LoginPage;


public class InventoryTest extends BaseClass {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    
    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        inventoryPage = new InventoryPage(getDriver());
        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
    }
   
 
    @Test(priority = 1)
    public void verifyInventoryPageLoadsSuccessfully() {
        Assert.assertTrue(
                inventoryPage.isPageFullyLoaded(),
                "Inventory page failed to load - Products title not visible.");
        
        Assert.assertTrue(
                inventoryPage.isShoppingCartDisplayed(),
                "Shopping cart icon should be visible on inventory page.");
        
        System.out.println("✓ Inventory page loaded successfully");
    }

    @Test(priority = 2)
    public void verifyInventoryPageTitle() {
        String expectedTitle = "Products";
        Assert.assertTrue(
                inventoryPage.isPageTitleCorrect(expectedTitle),
                "Inventory page title should be '" + expectedTitle + "'");
        
        System.out.println("✓ Page title verified: " + expectedTitle);
    }

    @Test(priority = 3)
    public void verifyProductsDisplayedOnePage() {
        int totalProducts = inventoryPage.getTotalProductsDisplayed();
        Assert.assertTrue(
                totalProducts > 0,
                "At least one product should be displayed on the inventory page.");
        
        System.out.println("✓ Total products displayed: " + totalProducts);
    }

    @Test(priority = 4)
    public void verifyAllProductNamesAreNotEmpty() {
        Assert.assertTrue(
                inventoryPage.areAllProductNamesDisplayed(),
                "All product names should be displayed and not empty.");
        
        System.out.println("✓ All product names are displayed correctly");
    }

    @Test(priority = 5)
    public void verifyAllProductPricesAreNotEmpty() {
        Assert.assertTrue(
                inventoryPage.areAllProductPricesDisplayed(),
                "All product prices should be displayed and not empty.");
        
        System.out.println("✓ All product prices are displayed correctly");
    }

    @Test(priority = 6)
    public void verifyShoppingCartInitiallyEmpty() {
        int cartCount = inventoryPage.getShoppingCartCount();
        Assert.assertEquals(
                cartCount,
                0,
                "Shopping cart should be empty initially.");
        
        System.out.println("✓ Shopping cart is initially empty");
    }

    @Test(priority = 7)
    public void verifyAddSingleProductToCart() {
        String productName = "Sauce Labs Backpack";
        inventoryPage.addProductToCart(productName);

        Assert.assertEquals(
                inventoryPage.getShoppingCartCount(),
                1,
                "Cart count should be 1 after adding one product.");
        
        System.out.println("✓ Single product added to cart successfully");
    }

    @Test(priority = 8)
    public void verifyAddTwoProductsToCart() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        
        inventoryPage.addProductToCart(product1);
        inventoryPage.addProductToCart(product2);

        Assert.assertEquals(
                inventoryPage.getShoppingCartCount(),
                2,
                "Cart count should be 2 after adding two products.");
        
        System.out.println("✓ Two products added to cart successfully");
    }

    @Test(priority = 9)
    public void verifyAddMultipleProductsToCart() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        String product3 = "Sauce Labs Bolt T-Shirt";
        
        inventoryPage.addProductToCart(product1);
        inventoryPage.addProductToCart(product2);
        inventoryPage.addProductToCart(product3);

        Assert.assertEquals(
                inventoryPage.getShoppingCartCount(),
                3,
                "Cart count should be 3 after adding three products.");
        
        System.out.println("✓ Three products added to cart successfully");
    }

    @Test(priority = 10)
    public void verifyRemoveButtonAppearsAfterAddingProduct() {
        String productName = "Sauce Labs Backpack";
        inventoryPage.addProductToCart(productName);

        Assert.assertTrue(
                inventoryPage.isRemoveButtonDisplayed(productName),
                "Remove button should be displayed after adding product: " + productName);
        
        System.out.println("✓ Remove button displayed for added product");
    }

    @Test(priority = 11)
    public void verifyRemoveProductFromCart() {
        String productName = "Sauce Labs Backpack";
        
        inventoryPage.addProductToCart(productName);
        Assert.assertEquals(
                inventoryPage.getShoppingCartCount(),
                1,
                "Cart should contain 1 product after addition.");

        inventoryPage.removeProductFromCart(productName);
        Assert.assertEquals(
                inventoryPage.getShoppingCartCount(),
                0,
                "Cart should be empty after removing the only product.");
        
        System.out.println("✓ Product removed from cart successfully");
    }

    @Test(priority = 12)
    public void verifyRemoveOneProductFromMultipleProducts() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        
        inventoryPage.addProductToCart(product1);
        inventoryPage.addProductToCart(product2);
        Assert.assertEquals(inventoryPage.getShoppingCartCount(),2,"Cart should contain 2 products.");

        inventoryPage.removeProductFromCart(product1);
        Assert.assertEquals(inventoryPage.getShoppingCartCount(), 1,"Cart should contain 1 product after removing one.");
        
        System.out.println("✓ One product removed from cart with remaining products intact");
    }

    @Test(priority = 13)
    public void verifyRemoveAllProductsFromCart() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        String product3 = "Sauce Labs Bolt T-Shirt";
        
        inventoryPage.addProductToCart(product1);
        inventoryPage.addProductToCart(product2);
        inventoryPage.addProductToCart(product3);
        Assert.assertEquals(inventoryPage.getShoppingCartCount(), 3);

        inventoryPage.removeProductFromCart(product1);
        Assert.assertEquals(inventoryPage.getShoppingCartCount(), 2,"Cart should contain 2 products after first removal.");

        inventoryPage.removeProductFromCart(product2);
        Assert.assertEquals(inventoryPage.getShoppingCartCount(),1,"Cart should contain 1 product after second removal.");

        inventoryPage.removeProductFromCart(product3);
        Assert.assertEquals(inventoryPage.getShoppingCartCount(), 0,"Cart should be empty after removing all products.");
        
        System.out.println("✓ All products removed from cart successfully");
    }
}