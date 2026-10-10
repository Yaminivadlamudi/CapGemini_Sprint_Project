package SwagLabBaseClass;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import Swag_LabFileutility.FileUtility;

public class SwagLabBaseclass1 {

    protected WebDriver driver;

    // 1. Open database connectivity / start suite
    @BeforeSuite(alwaysRun = true)
    public void openDatabaseConnectivity() {

        Reporter.log("Automation suite started.", true);
    }

    // 2. Launch browser
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void launchBrowser(@Optional("chrome") String browser) {

        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome";
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put(
            "profile.default_content_setting_values.notifications", 2
        );

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {

            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("signon.rememberSignons", false);

            driver = new FirefoxDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-notifications");
            options.setExperimentalOption("prefs", prefs);

            driver = new EdgeDriver(options);

        } else if (browser.equalsIgnoreCase("safari")) {

            if (!System.getProperty("os.name")
                    .toLowerCase().contains("mac")) {
                throw new SkipException("Safari requires macOS.");
            }

            driver = new SafariDriver();

        } else {

            throw new IllegalArgumentException(
                "Invalid browser: " + browser
            );
        }

        driver.manage().window().maximize();

        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(3));

        FileUtility file = new FileUtility();
        String url = file.readDataFromPropertyFile("url");

        Assert.assertNotNull(url, "Application URL is missing.");
        Assert.assertFalse(url.trim().isEmpty(), "Application URL is blank.");

        driver.get(url);

        Assert.assertTrue(
            driver.getCurrentUrl().contains("saucedemo.com"),
            "SauceDemo application did not open."
        );

        Reporter.log(
            "PASS: " + browser + " browser launched successfully.",
            true
        );
    }

    // 3. Close browser and report test result
    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {

            for (String group : result.getMethod().getGroups()) {

                if (group.equalsIgnoreCase("functional")) {
                    Reporter.log("PASS: Functional test case passed.", true);

                } else if (group.equalsIgnoreCase("integration")) {
                    Reporter.log("PASS: Integration test case passed.", true);

                } else if (group.equalsIgnoreCase("system")) {
                    Reporter.log("PASS: System test case passed.", true);

                } else if (group.equalsIgnoreCase("negative")) {
                    Reporter.log("PASS: Negative test case passed.", true);

                } else if (group.equalsIgnoreCase("smoke")) {
                    Reporter.log("PASS: Smoke test case passed.", true);
                }
            }

            Reporter.log(
                "PASS: Test completed - " + result.getName(),
                true
            );

        } else if (result.getStatus() == ITestResult.FAILURE) {

            Reporter.log(
                "FAIL: Test case failed - " + result.getName(),
                true
            );

            if (result.getThrowable() != null) {
                Reporter.log(
                    "Failure reason: " + result.getThrowable(),
                    true
                );
            }

        } else if (result.getStatus() == ITestResult.SKIP) {

            Reporter.log(
                "SKIPPED: Test case - " + result.getName(),
                true
            );
        }

        if (driver != null) {
            driver.quit();
            driver = null;
        }

        Reporter.log("Browser closed successfully.", true);
    }

    // 4. Close database connectivity / finish suite
    @AfterSuite(alwaysRun = true)
    public void closeDatabaseConnectivity() {

        Reporter.log("Automation suite completed.", true);
    }
}
