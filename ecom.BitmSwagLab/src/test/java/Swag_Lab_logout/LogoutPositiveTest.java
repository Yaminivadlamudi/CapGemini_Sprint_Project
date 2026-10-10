
package Swag_Lab_logout;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import pompages1.Swag_LabLoginPage;
import pompages1.Swag_LabHamburgerMenuPage;

public class LogoutPositiveTest extends SwagLabBaseclass1 {

    private void loginToSauceDemo() {
        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login("standard_user", "secret_sauce");

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains("inventory.html"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login failed. Products page was not displayed."
        );
    }

    private void logoutFromApplication() {
        Swag_LabHamburgerMenuPage menu = new Swag_LabHamburgerMenuPage(driver);

        menu.clickHamburgerIcon();
        menu.clickLogout();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the login page's username field is visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("user-name")
        ));
    }

    @Test(groups = {"functional", "smoke"})
    public void verifyLogoutMenuOpens() {
        loginToSauceDemo();

        Swag_LabHamburgerMenuPage menu = new Swag_LabHamburgerMenuPage(driver);
        menu.clickHamburgerIcon();

        Assert.assertTrue(
                menu.isLogoutDisplayed(),
                "Logout option was not displayed."
        );

        Reporter.log("PASS: Logout menu opened successfully", true);
    }

    @Test(groups = {"functional", "integration", "smoke"})
    public void verifySuccessfulLogout() {
        loginToSauceDemo();
        logoutFromApplication();

        Assert.assertTrue(
                driver.findElement(By.id("user-name")).isDisplayed(),
                "Username field was not displayed after logout."
        );

        Assert.assertTrue(
                driver.findElement(By.id("login-button")).isDisplayed(),
                "Login button was not displayed after logout."
        );

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory.html"),
                "User was not logged out."
        );

        Reporter.log("PASS: User logged out successfully", true);
    }

    @Test(groups = {"functional", "integration"})
    public void verifyLoginPageAfterLogout() {
        loginToSauceDemo();
        logoutFromApplication();

        Assert.assertTrue(
                driver.findElement(By.id("user-name")).isDisplayed(),
                "Login page was not displayed after logout."
        );

        Assert.assertTrue(
                driver.findElement(By.id("login-button")).isDisplayed(),
                "Login button was not displayed after logout."
        );

        Reporter.log(
                "PASS: Login page displayed after logout",
                true
        );
    }
}