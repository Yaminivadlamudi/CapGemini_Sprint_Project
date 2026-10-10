
package Swag_Lab_ProductTest;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import pompages1.Swag_LabLoginPage;
import pompages1.Swag_labProductPage;
import pompages1.Swag_LabCartPage;
import pompages1.Swag_LabHamburgerMenuPage;

public class ProductPositiveTest extends SwagLabBaseclass1 {

    Swag_LabLoginPage login;
    Swag_labProductPage product;
    Swag_LabCartPage cart;
    Swag_LabHamburgerMenuPage menu;

    // Login using valid credentials
    public void loginToProducts() {

        login = new Swag_LabLoginPage(driver);
        login.login("standard_user", "secret_sauce");

        product = new Swag_labProductPage(driver);
    }

    // Test 1: Verify product page title
    @Test(groups = {"functional", "smoke"})
    public void verifyProductPageTitle() {

        loginToProducts();

        Assert.assertEquals(
                product.getPageTitle(),
                "Products",
                "Product page title is incorrect"
        );

        Reporter.log("PASS: Product page title verified", true);
    }

    // Test 2: Verify total number of products
    @Test(groups = {"functional", "smoke"})
    public void verifyProductCount() {

        loginToProducts();

        Assert.assertEquals(
                product.getProductCount(),
                6,
                "Expected 6 products on the product page"
        );

        Reporter.log("PASS: Total 6 products displayed", true);
    }

    // Test 3: Verify sorting products from A to Z
    @Test(groups = {"functional", "integration"})
    public void verifySortNameAToZ() {

        loginToProducts();

        product.sortNameAToZ();

        List<String> names = product.getProductNames();
        List<String> sortedNames = new java.util.ArrayList<>(names);

        Collections.sort(sortedNames);

        Assert.assertEquals(
                names,
                sortedNames,
                "Products are not sorted from A to Z"
        );

        Reporter.log("PASS: Products sorted A to Z", true);
    }

    // Test 4: Verify sorting products from Z to A
    @Test(groups = {"functional", "integration"})
    public void verifySortNameZToA() {

        loginToProducts();

        product.sortNameZToA();

        List<String> names = product.getProductNames();
        List<String> sortedNames = new java.util.ArrayList<>(names);

        sortedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(
                names,
                sortedNames,
                "Products are not sorted from Z to A"
        );

        Reporter.log("PASS: Products sorted Z to A", true);
    }

    // Test 5: Verify sorting prices from low to high
    @Test(groups = {"functional", "integration"})
    public void verifySortPriceLowToHigh() {

        loginToProducts();

        product.sortPriceLowToHigh();

        List<Double> prices = product.getProductPrices();
        List<Double> sortedPrices = new java.util.ArrayList<>(prices);

        Collections.sort(sortedPrices);

        Assert.assertEquals(
                prices,
                sortedPrices,
                "Prices are not sorted from low to high"
        );

        Reporter.log("PASS: Prices sorted low to high", true);
    }

    // Test 6: Verify sorting prices from high to low
    @Test(groups = {"functional", "integration"})
    public void verifySortPriceHighToLow() {

        loginToProducts();

        product.sortPriceHighToLow();

        List<Double> prices = product.getProductPrices();
        List<Double> sortedPrices = new java.util.ArrayList<>(prices);

        sortedPrices.sort(Collections.reverseOrder());

        Assert.assertEquals(
                prices,
                sortedPrices,
                "Prices are not sorted from high to low"
        );

        Reporter.log("PASS: Prices sorted high to low", true);
    }

    // Test 7: Verify adding backpack to cart
    @Test(groups = {"functional", "smoke"})
    public void verifyAddBackpackToCart() {

        loginToProducts();

        product.addBackpack();

        Assert.assertEquals(
                product.getCartCount(),
                "1",
                "Cart count should be 1 after adding backpack"
        );

        Assert.assertTrue(
                product.isRemoveButtonDisplayed(),
                "Remove button should be displayed after adding backpack"
        );

        Reporter.log("PASS: Backpack added to cart", true);
    }

    // Test 8: Verify removing backpack from cart
    @Test(groups = {"functional", "integration"})
    public void verifyRemoveBackpackFromCart() {

        loginToProducts();

        product.addBackpack();

        Assert.assertEquals(
                product.getCartCount(),
                "1",
                "Cart count should be 1 after adding backpack"
        );

        product.removeBackpack();

        Assert.assertEquals(
                product.getCartCount(),
                "0",
                "Cart count should be 0 after removing backpack"
        );

        Assert.assertTrue(
                product.isAddBackpackDisplayed(),
                "Add to Cart button should appear after removing backpack"
        );

        Reporter.log("PASS: Backpack removed from cart successfully", true);
    }

    // Test 9: Verify backpack in cart
    @Test(groups = {"functional", "integration"})
    public void verifyBackpackInCart() {

        loginToProducts();

        product.addBackpack();
        product.clickCartIcon();

        cart = new Swag_LabCartPage(driver);

        Assert.assertEquals(
                cart.getPageTitle(),
                "Your Cart",
                "Cart page title is incorrect"
        );

        Assert.assertTrue(
                cart.isBackpackDisplayed(),
                "Backpack should be displayed in the cart"
        );

        Reporter.log("PASS: Backpack verified in cart", true);
    }

    // Test 10: Verify checkout navigation
    @Test(groups = {"functional", "integration"})
    public void verifyCheckoutNavigation() {

        loginToProducts();

        product.addBackpack();
        product.clickCartIcon();

        cart = new Swag_LabCartPage(driver);

        Assert.assertEquals(
                cart.getPageTitle(),
                "Your Cart",
                "Cart page title is incorrect"
        );

        cart.clickCheckout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one.html"),
                "Checkout information page did not open"
        );

        Reporter.log("PASS: Checkout information page opened", true);
    }

    // Test 11: Verify logout from product page
    @Test(groups = {"functional", "smoke"})
    public void verifyLogoutFromProductPage() {

        loginToProducts();

        menu = new Swag_LabHamburgerMenuPage(driver);
        menu.clickHamburgerIcon();
        menu.clickLogout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "Logout did not return to the SauceDemo site"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().endsWith("/"),
                "Expected to return to the login page"
        );

        Reporter.log("PASS: Logout completed successfully", true);
    }
}
