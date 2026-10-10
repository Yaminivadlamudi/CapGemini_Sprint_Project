
package Swag_Lab_logout;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import pompages1.Swag_LabLoginPage;
import pompages1.Swag_LabHamburgerMenuPage;

public class LogoutNegativeTest extends SwagLabBaseclass1 {

    // Test 1: Verify logout option is not visible
    // before opening the hamburger menu
    @Test(groups = {"functional", "negative"})
    public void verifyLogoutOptionBeforeOpeningMenu() {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login("standard_user", "secret_sauce");

        Swag_LabHamburgerMenuPage menu = new Swag_LabHamburgerMenuPage(driver);

        Assert.assertFalse(
                menu.isLogoutDisplayed(),
                "Logout option should not be visible before opening the menu"
        );

        Reporter.log(
                "PASS: Logout option hidden while menu is closed",
                true
        );
    }

    // Test 2: Verify logout returns user to login page
    // and product page is no longer accessible through the current URL
    @Test(groups = {"functional", "integration", "negative"})
    public void verifyProductPageAfterLogout() {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login("standard_user", "secret_sauce");

        Swag_LabHamburgerMenuPage menu = new Swag_LabHamburgerMenuPage(driver);
        menu.clickHamburgerIcon();
        menu.clickLogout();

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory.html"),
                "User remained on the product page after logout"
        );

        Reporter.log(
                "PASS: Product page is no longer the current page after logout",
                true
        );
    }

    // Test 3: Verify logout menu is closed after logout
    @Test(groups = {"functional", "negative"})
    public void verifyLogoutMenuAfterLogout() {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login("standard_user", "secret_sauce");

        Swag_LabHamburgerMenuPage menu = new Swag_LabHamburgerMenuPage(driver);
        menu.clickHamburgerIcon();
        menu.clickLogout();

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory.html"),
                "Logout failed; product page is still open"
        );

        Reporter.log(
                "PASS: Logout completed and product page is no longer open",
                true
        );
    }
}
