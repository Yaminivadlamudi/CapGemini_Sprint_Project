
package pompages1;

import java.time.Duration;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Swag_LabHamburgerMenuPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public Swag_LabHamburgerMenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerIcon;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    // Open hamburger menu
    public void clickHamburgerIcon() {
        wait.until(
                ExpectedConditions.elementToBeClickable(hamburgerIcon)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOf(logoutLink)
        );
    }

    // Click logout
    public void clickLogout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink)
        ).click();
    }

    public void clickAbout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(aboutLink)
        ).click();
    }

    public void clickResetAppState() {
        wait.until(
                ExpectedConditions.elementToBeClickable(resetAppStateLink)
        ).click();
    }

    public void closeMenu() {
        wait.until(
                ExpectedConditions.elementToBeClickable(closeMenuButton)
        ).click();
    }

    // Check logout link visibility
    public boolean isLogoutDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
