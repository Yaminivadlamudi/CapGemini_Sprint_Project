
package Swag_Lab_CheckoutTest;

import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SwagLabBaseClass.SwagLabBaseclass1;
import pompages1.Swag_LabLoginPage;
import pompages1.Swag_labProductPage;
import pompages1.Swag_LabCartPage;
import pompages1.SwagCheckoutPage;

public class CheckoutNegativeTest extends SwagLabBaseclass1 {

    // Read only checkout-negative rows from the Excel sheet.
    @DataProvider(name = "negativeCheckoutData")
    public Object[][] negativeCheckoutData() throws Exception {

        String fileName = "SwagLabs_SingleSheet_TestData.xlsx";

        try (InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName)) {

            Assert.assertNotNull(
                input,
                "Excel file was not found in src/test/resources: "
                    + fileName
            );

            try (Workbook workbook = WorkbookFactory.create(input)) {

                Sheet sheet = workbook.getSheet("TestData");

                Assert.assertNotNull(
                    sheet,
                    "Excel sheet 'TestData' was not found."
                );

                DataFormatter formatter = new DataFormatter();
                Row header = sheet.getRow(0);

                Assert.assertNotNull(
                    header,
                    "Excel header row is missing."
                );

                Map<String, Integer> columns = new HashMap<>();

                for (int c = 0; c < header.getLastCellNum(); c++) {
                    String heading = formatter
                            .formatCellValue(header.getCell(c))
                            .trim();

                    columns.put(heading, c);
                }

                String[] requiredColumns = {
                    "Username", "Password", "FirstName",
                    "LastName", "PostalCode", "Scenario",
                    "ExpectedResult"
                };

                for (String column : requiredColumns) {
                    Assert.assertTrue(
                        columns.containsKey(column),
                        "Missing Excel column: " + column
                    );
                }

                List<Object[]> testData = new ArrayList<>();

                for (int r = 1; r <= sheet.getLastRowNum(); r++) {

                    Row row = sheet.getRow(r);

                    if (row == null) {
                        continue;
                    }

                    String expected = formatter.formatCellValue(
                        row.getCell(columns.get("ExpectedResult"))
                    ).trim();

                    if (expected.equalsIgnoreCase("CHECKOUT_ERROR")) {

                        Object[] data = new Object[7];

                        for (int c = 0; c < requiredColumns.length; c++) {
                            data[c] = formatter.formatCellValue(
                                row.getCell(columns.get(requiredColumns[c]))
                            ).trim();
                        }

                        testData.add(data);
                    }
                }

                Assert.assertFalse(
                    testData.isEmpty(),
                    "No rows with ExpectedResult CHECKOUT_ERROR "
                        + "were found in Excel."
                );

                return testData.toArray(new Object[0][]);
            }
        }
    }

    @Test(
        dataProvider = "negativeCheckoutData",
        groups = {"negative", "functional", "integration"}
    )
    public void verifyCheckoutValidation(
            String username,
            String password,
            String firstName,
            String lastName,
            String postalCode,
            String scenario,
            String expectedResult) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        Reporter.log("Scenario: " + scenario, true);

        // Validate that this row represents missing-first-name checkout.
        Assert.assertEquals(
            expectedResult,
            "CHECKOUT_ERROR",
            "Unexpected expected result from Excel."
        );

        Assert.assertTrue(
            firstName == null || firstName.trim().isEmpty(),
            "The FirstName cell must be blank for this scenario."
        );

        Assert.assertFalse(
            username == null || username.trim().isEmpty(),
            "Username is blank in Excel."
        );

        Assert.assertFalse(
            password == null || password.trim().isEmpty(),
            "Password is blank in Excel."
        );

        // Step 1: Login using Excel credentials.
        Swag_LabLoginPage login = new Swag_LabLoginPage(driver);
        login.login(username, password);

        try {
            wait.until(
                ExpectedConditions.urlContains("inventory.html")
            );
        } catch (TimeoutException e) {

            List<WebElement> errors = driver.findElements(
                By.cssSelector("[data-test='error']")
            );

            String actualError = errors.isEmpty()
                    ? "No login error displayed. Current URL: "
                        + driver.getCurrentUrl()
                    : errors.get(0).getText();

            Assert.fail(
                "Login failed for scenario: " + scenario
                    + ". Actual error: " + actualError
            );
        }

        Reporter.log("PASS: Login successful", true);

        // Step 2: Add a product.
        Swag_labProductPage products = new Swag_labProductPage(driver);
        products.addBackpack();

        Assert.assertEquals(
            products.getCartCount(),
            "1",
            "Product was not added to the cart."
        );

        Reporter.log("PASS: Product added to cart", true);

        // Step 3: Open cart.
        products.clickCartIcon();

        wait.until(ExpectedConditions.urlContains("cart.html"));

        Swag_LabCartPage cart = new Swag_LabCartPage(driver);

        Assert.assertTrue(
            cart.isBackpackDisplayed(),
            "Product was not displayed in the cart."
        );

        // Step 4: Open checkout information.
        cart.clickCheckout();

        wait.until(
            ExpectedConditions.urlContains("checkout-step-one.html")
        );

        Reporter.log(
            "PASS: Checkout information page opened",
            true
        );

        // Step 5: Submit the blank first name from Excel.
        SwagCheckoutPage checkout = new SwagCheckoutPage(driver);

        checkout.enterCustomerDetails(
            firstName,
            lastName,
            postalCode
        );

        checkout.clickContinue();

        String actualError = checkout.getErrorMessage();

        Assert.assertTrue(
            actualError.toLowerCase().contains("first name"),
            "Expected first-name validation, but got: " + actualError
        );

        Reporter.log(
            "PASS: Missing first-name validation verified. "
                + "Message: " + actualError,
            true
        );
    }
}
