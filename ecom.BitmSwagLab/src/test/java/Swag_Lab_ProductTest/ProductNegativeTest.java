
package Swag_Lab_ProductTest;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import pompages1.Swag_LabLoginPage;
import pompages1.Swag_labProductPage;
import pompages1.Swag_LabCartPage;

public class ProductNegativeTest extends SwagLabBaseclass1 {

    private Swag_labProductPage loginToProducts() {
        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login("standard_user", "secret_sauce");
        return new Swag_labProductPage(driver);
    }

    @Test(groups = {"functional", "negative"})
    public void verifyInvalidSortOption() {
        Swag_labProductPage product = loginToProducts();

        boolean invalidOptionRejected = false;

        try {
            product.sortProducts("invalid");
        } catch (NoSuchElementException e) {
            invalidOptionRejected = true;
        }

        Assert.assertTrue(
            invalidOptionRejected,
            "Invalid sorting option should be rejected"
        );

        Assert.assertEquals(product.getPageTitle(), "Products");

        Reporter.log("PASS: Invalid sort option rejected", true);
    }

    @Test(groups = {"functional", "negative"})
    public void verifyEmptyCart() {
        Swag_labProductPage product = loginToProducts();

        Assert.assertEquals(
            product.getCartCount(), "0",
            "Cart should initially be empty"
        );

        product.clickCartIcon();

        Swag_LabCartPage cart = new Swag_LabCartPage(driver);

        Assert.assertEquals(cart.getPageTitle(), "Your Cart");
        Assert.assertFalse(
            cart.isBackpackDisplayed(),
            "Backpack should not appear in an empty cart"
        );

        Reporter.log("PASS: Empty cart verified", true);
    }

    @Test(groups = {"functional", "negative"})
    public void verifyRemoveButtonBeforeAddingProduct() {
        Swag_labProductPage product = loginToProducts();

        Assert.assertFalse(
            product.isRemoveButtonDisplayed(),
            "Remove button should not appear before adding the backpack"
        );

        Assert.assertTrue(
            product.isAddBackpackDisplayed(),
            "Add button should be displayed initially"
        );

        Reporter.log(
            "PASS: Product cannot be removed before being added",
            true
        );
    }

    @Test(groups = {"integration", "negative"})
    public void verifyUnaddedProductNotInCart() {
        Swag_labProductPage product = loginToProducts();

        product.clickCartIcon();

        Swag_LabCartPage cart = new Swag_LabCartPage(driver);

        Assert.assertEquals(cart.getPageTitle(), "Your Cart");

        Assert.assertFalse(
            cart.isBackpackDisplayed(),
            "An unadded backpack must not appear in the cart"
        );

        Reporter.log(
            "PASS: Unadded product is absent from cart",
            true
        );
    }
}
