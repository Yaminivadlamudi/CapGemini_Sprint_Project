
package pompages1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Swag_LabLoginPage {

    public Swag_LabLoginPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is null. Check Baseclass1 and the test class."
            );
        }

        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    private WebElement usernameTF;

    @FindBy(id = "password")
    private WebElement passwordTF;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public void enterUsername(String username) {
        usernameTF.clear();
        usernameTF.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordTF.clear();
        passwordTF.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isErrorDisplayed() {
        return errorMessage.isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }
}
