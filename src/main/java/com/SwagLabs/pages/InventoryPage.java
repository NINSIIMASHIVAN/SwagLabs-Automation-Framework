package com.SwagLabs.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SwagLabs.actiondriver.ActionDriver;

public class InventoryPage {

    WebDriver driver;
    ActionDriver actionDriver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = new ActionDriver(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

  
    // Locators
  

    private By pageTitle = By.className("title");

    private By shoppingCartIcon = By.className("shopping_cart_link");

    private By shoppingCartBadge = By.className("shopping_cart_badge");

    private By sortDropdown = By.className("product_sort_container");

    private By allProductNames = By.className("inventory_item_name");

    private By allProductPrices = By.className("inventory_item_price");

    private By addToCartLocator(String productName) {
        String idSuffix = productName.toLowerCase().replace(" ", "-");
        return By.id("add-to-cart-" + idSuffix);
    }

    private By removeFromCartLocator(String productName) {
        String idSuffix = productName.toLowerCase().replace(" ", "-");
        return By.id("remove-" + idSuffix);
    }


    // Page Verification
  

    public boolean isPageFullyLoaded() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle))
                .getText()
                .equalsIgnoreCase("Products");
    }

    public boolean isPageTitleCorrect(String expectedTitle) {

        String actualTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle))
                .getText();

        System.out.println("Expected: " + expectedTitle);
        System.out.println("Actual: " + actualTitle);

        return actualTitle.equals(expectedTitle);
    }


    // Product Actions
 

    public void addProductToCart(String productName) {

        wait.until(ExpectedConditions.elementToBeClickable(
                addToCartLocator(productName))).click();

        System.out.println(productName + " added to cart.");
    }

    public void removeProductFromCart(String productName) {

        wait.until(ExpectedConditions.elementToBeClickable(
                removeFromCartLocator(productName))).click();

        System.out.println(productName + " removed from cart.");
    }

   
    // Cart Verification


    public int getShoppingCartCount() {

        try {

            WebElement badge = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));

            return Integer.parseInt(badge.getText());

        } catch (Exception e) {

            return 0;
        }
    }

    public boolean isRemoveButtonDisplayed(String productName) {

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    removeFromCartLocator(productName)));

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public boolean isShoppingCartDisplayed() {

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    shoppingCartIcon));

            return true;

        } catch (Exception e) {

            return false;
        }
    }


    // Inventory Verification
   

    public int getTotalProductsDisplayed() {

        List<WebElement> products =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allProductNames));

        return products.size();
    }

    public boolean areAllProductNamesDisplayed() {

        List<WebElement> names =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allProductNames));

        for (WebElement name : names) {

            if (name.getText().trim().isEmpty()) {

                return false;
            }
        }

        return true;
    }

    public boolean areAllProductPricesDisplayed() {

        List<WebElement> prices =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allProductPrices));

        for (WebElement price : prices) {

            if (price.getText().trim().isEmpty()) {

                return false;
            }
        }

        return true;
    }

}