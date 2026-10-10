package Swag_Lab_LoginPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import Swag_Lab_CheckoutTest.DataProviderClass;
import pompages1.Swag_LabLoginPage;

public class LoginPositiveTest extends SwagLabBaseclass1 {

    @Test(
        dataProvider = "positiveLoginData",
        dataProviderClass = DataProviderClass.class,
        groups = {"functional"}
    )
    public void functionalLoginTest(
            String username, String password,
            String firstName, String lastName,
            String postalCode, String scenario,
            String expectedResult) {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login(username, password);

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory.html"),
            "Functional login failed"
        );

        Reporter.log("PASS: FUNCTIONAL - " + scenario, true);
    }

    @Test(
        dataProvider = "positiveLoginData",
        dataProviderClass = DataProviderClass.class,
        groups = {"integration"}
    )
    public void integrationLoginTest(
            String username, String password,
            String firstName, String lastName,
            String postalCode, String scenario,
            String expectedResult) {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login(username, password);

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory.html"),
            "Integration login failed"
        );

        Reporter.log("PASS: INTEGRATION - " + scenario, true);
    }

    @Test(
        dataProvider = "positiveLoginData",
        dataProviderClass = DataProviderClass.class,
        groups = {"smoke"}
    )
    public void smokeLoginTest(
            String username, String password,
            String firstName, String lastName,
            String postalCode, String scenario,
            String expectedResult) {

        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login(username, password);

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory.html"),
            "Smoke login failed"
        );

        Reporter.log("PASS: SMOKE - " + scenario, true);
    }
}