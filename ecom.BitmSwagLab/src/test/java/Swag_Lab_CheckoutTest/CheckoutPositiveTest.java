
package Swag_Lab_CheckoutTest;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import pompages1.Swag_LabLoginPage;
import pompages1.Swag_labProductPage;
import pompages1.Swag_LabCartPage;
import pompages1.SwagCheckoutPage;

public class CheckoutPositiveTest extends SwagLabBaseclass1 {

    @Test(
        dataProvider = "positiveCheckoutData",
        dataProviderClass = DataProviderClass.class,
        groups = {"functional", "integration", "smoke"}
    )
    public void verifySuccessfulCheckout(
            String username,
            String password,
            String firstName,
            String lastName,
            String postalCode,
            String scenario,
            String expectedResult) {

        Reporter.log("Scenario: " + scenario, true);

      
        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login(username, password);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login failed for scenario: " + scenario
        );
Reporter.log("PASS: Login successful", true);

       
        Swag_labProductPage product = new Swag_labProductPage(driver);
        product.addBackpack();

        Assert.assertEquals(
                product.getCartCount(), "1", "Backpack was not added to cart" );

        product.clickCartIcon();
       Swag_LabCartPage cart = new Swag_LabCartPage(driver);
Assert.assertEquals(cart.getPageTitle(),"Your Cart","Cart page was not displayed");

        Assert.assertTrue(
                cart.isBackpackDisplayed(),
                "Backpack was not found in cart"
        );

        cart.clickCheckout();

        // STEP 4: VERIFY CHECKOUT INFORMATION PAGE
        SwagCheckoutPage checkout = new SwagCheckoutPage(driver);

        Assert.assertEquals(
                checkout.getPageTitle(),
                "Checkout: Your Information",
                "Checkout information page was not displayed"
        );

        // STEP 5: ENTER CUSTOMER DETAILS FROM EXCEL
        checkout.enterCustomerDetails(
                firstName,
                lastName,
                postalCode
        );

        // STEP 6: CLICK CONTINUE
        checkout.clickContinue();

        // STEP 7: HANDLE JAVASCRIPT ALERT IF PRESENT
        checkout.handleBrowserAlert();

        // STEP 8: CHECK CHECKOUT VALIDATION ERROR
        if (checkout.isErrorDisplayed()) {
            Assert.fail(
                    "Checkout validation failed: "
                    + checkout.getErrorMessage()
                    + " | Scenario: " + scenario
            );
        }

        // STEP 9: VERIFY CHECKOUT OVERVIEW
        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Checkout overview did not open. Current URL: "
                        + driver.getCurrentUrl()
        );

        Assert.assertEquals(
                checkout.getPageTitle(),
                "Checkout: Overview",
                "Checkout overview was not displayed"
        );

        Reporter.log("PASS: Checkout overview displayed", true);

        // STEP 10: VERIFY ORDER SUMMARY
        Assert.assertTrue(
                checkout.getSubtotal().contains("Item total:"),
                "Item subtotal was not displayed"
        );

        Assert.assertTrue(
                checkout.getTax().contains("Tax:"),
                "Tax was not displayed"
        );

        Assert.assertTrue(
                checkout.getTotal().contains("Total:"),
                "Order total was not displayed"
        );

        Reporter.log("PASS: Order summary verified", true);



        checkout.clickFinish();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-complete.html"),
                "Order completion page did not open. Current URL: "
                        + driver.getCurrentUrl()
        );

        Assert.assertEquals(
                checkout.getConfirmationMessage(),
                "Thank you for your order!",
                "Order confirmation was not displayed"
        );

        Reporter.log("PASS: Order completed successfully", true);

        Assert.assertTrue(
                checkout.getConfirmationText().length() > 0,
                "Order confirmation text is missing"
        );

        // STEP 12: RETURN TO PRODUCTS
        checkout.clickBackToProducts();

        Assert.assertEquals(
                product.getPageTitle(),
                "Products",
                "Product page was not displayed after checkout"
        );

        Reporter.log("PASS: Returned to products page", true);
    }
}
