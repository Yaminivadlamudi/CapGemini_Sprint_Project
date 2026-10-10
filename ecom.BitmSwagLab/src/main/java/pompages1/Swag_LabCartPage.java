
package pompages1;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Swag_LabCartPage {

    public Swag_LabCartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "item_4_title_link")
    private WebElement backpack;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public boolean isBackpackDisplayed() {
        try {
            return backpack.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickCheckout() {
        checkoutButton.click();
    }
}
