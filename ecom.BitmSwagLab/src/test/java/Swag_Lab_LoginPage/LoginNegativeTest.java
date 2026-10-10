
package Swag_Lab_LoginPage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import Swag_Lab_CheckoutTest.DataProviderClass;
import pompages1.Swag_LabLoginPage;

public class LoginNegativeTest extends SwagLabBaseclass1 {

    @Test(
        dataProvider = "negativeLoginData",
        dataProviderClass = DataProviderClass.class,
        groups = {"functional"}
    )
    public void functionalNegativeLoginTest(
            String username,
            String password,
            String firstName,
            String lastName,
            String postalCode,
            String scenario,
            String expectedResult) {

        verifyNegativeLogin(username, password, scenario);
    }

    @Test(
        dataProvider = "negativeLoginData",
        dataProviderClass = DataProviderClass.class,
        groups = {"integration"}
    )
    public void integrationNegativeLoginTest(
            String username,
            String password,
            String firstName,
            String lastName,
            String postalCode,
            String scenario,
            String expectedResult) {

        verifyNegativeLogin(username, password, scenario);
    }

    @Test(
        dataProvider = "negativeLoginData",
        dataProviderClass = DataProviderClass.class,
        groups = {"smoke"}
    )
    public void smokeNegativeLoginTest(
            String username,
            String password,
            String firstName,
            String lastName,
            String postalCode,
            String scenario,
            String expectedResult) {

        verifyNegativeLogin(username, password, scenario);
    }

    private void verifyNegativeLogin(
            String username,
            String password,
            String scenario) {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);

        try {
            login.login(username, password);

            Assert.assertTrue(
                login.isErrorDisplayed(),
                "Expected login error was not displayed"
            );

            String actualError = login.getErrorMessage();

            Assert.assertTrue(
                actualError != null && !actualError.trim().isEmpty(),
                "Login error message is empty"
            );

            Reporter.log(
                "PASS: " + scenario
                + " | Login rejected successfully"
                + " | Error: " + actualError,
                true
            );

        } catch (AssertionError e) {
            Reporter.log(
                "FAIL: " + scenario
                + " | Negative login validation failed",
                true
            );
            throw e;

        } finally {
            takeScreenshot(scenario);
        }
    }

    private void takeScreenshot(String scenario) {
        try {
            File folder = new File("test-output/screenshots");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String safeName = scenario.replaceAll(
                "[^a-zA-Z0-9_-]", "_"
            );

            String timestamp = new SimpleDateFormat(
                "yyyyMMdd_HHmmss_SSS"
            ).format(new Date());

            File source = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

            File destination = new File(
                folder,
                safeName + "_" + timestamp + ".png"
            );

            FileHandler.copy(source, destination);

            Reporter.log(
                "SCREENSHOT SAVED: " + destination.getAbsolutePath(),
                true
            );

        } catch (Exception e) {
            Reporter.log(
                "SCREENSHOT FAILED: " + e.getMessage(),
                true
            );
        }
    }
}
