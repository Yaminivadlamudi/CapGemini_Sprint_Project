
package pompages1;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Swag_LabLogoutPage {

    private WebDriverWait wait;

    public Swag_LabLogoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "user-name")
    private WebElement usernameField;

    public boolean isLoginButtonDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOf(loginButton)
        ).isDisplayed();
    }

    public boolean isUsernameFieldDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOf(usernameField)
        ).isDisplayed();
    }
}
