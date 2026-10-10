package pompages1;
import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwagCheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SwagCheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // CHECKOUT INFORMATION PAGE

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameTF;

    @FindBy(id = "last-name")
    private WebElement lastNameTF;

    @FindBy(id = "postal-code")
    private WebElement postalCodeTF;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // CHECKOUT OVERVIEW PAGE

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    // ORDER COMPLETE PAGE

    @FindBy(className = "complete-header")
    private WebElement confirmationMessage;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    // ENTER DETAILS FROM EXCEL

    public void enterCustomerDetails(String firstName,String lastName, String postalCode) {
   wait.until( ExpectedConditions.visibilityOf(firstNameTF)).clear();
    lastNameTF.clear();
   postalCodeTF.clear();
if (firstName != null && !firstName.isBlank()) {
firstNameTF.sendKeys(firstName);
        }

        if (lastName != null && !lastName.isBlank()) {
            lastNameTF.sendKeys(lastName);
        }

        if (postalCode != null && !postalCode.isBlank()) {
            postalCodeTF.sendKeys(postalCode);
        }

        System.out.println(
                "First name entered: " + firstNameTF.getAttribute("value")
        );
        System.out.println(
                "Last name entered: " + lastNameTF.getAttribute("value")
        );
        System.out.println(
                "Postal code entered: " + postalCodeTF.getAttribute("value")
        );
    }

 

    public void clickContinue() {
        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }



    public boolean handleBrowserAlert() {
        try {
            Alert alert = driver.switchTo().alert();

            System.out.println(
                    "JAVASCRIPT POPUP: " + alert.getText()
            );

            alert.accept();
            return true;

        } catch (NoAlertPresentException e) {
            return false;
        }
    }

public boolean isErrorDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)
            ).getText();

        } catch (org.openqa.selenium.TimeoutException
                | NoSuchElementException e) {
            return "";
        }
    }



    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageTitle)
        ).getText();
    }

   

    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    public String getTax() {
        return taxLabel.getText();
    }

    public String getTotal() {
        return totalLabel.getText();
    }



    public void clickFinish() {
        wait.until(
                ExpectedConditions.elementToBeClickable(finishButton)
        ).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-complete.html")
        );
    }

    public void clickCancel() {
        wait.until(
                ExpectedConditions.elementToBeClickable(cancelButton)
        ).click();
    }



    public String getConfirmationMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(confirmationMessage)
        ).getText();
    }

    public String getConfirmationText() {
        return wait.until(
                ExpectedConditions.visibilityOf(confirmationText)
        ).getText();
    }

    public void clickBackToProducts() {
        wait.until(
                ExpectedConditions.elementToBeClickable(backToProductsButton)
        ).click();
    }
}
