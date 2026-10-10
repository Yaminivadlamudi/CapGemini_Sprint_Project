
package pompages1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Swag_labProductPage {

    public Swag_labProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpackButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeBackpackButton;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    // Get product page title
    public String getPageTitle() {
        return pageTitle.getText();
    }

    // Sort products
    public void sortProducts(String option) {
        Select select = new Select(sortDropdown);
        select.selectByValue(option);
    }

    public void sortNameAToZ() {
        sortProducts("az");
    }

    public void sortNameZToA() {
        sortProducts("za");
    }

    public void sortPriceLowToHigh() {
        sortProducts("lohi");
    }

    public void sortPriceHighToLow() {
        sortProducts("hilo");
    }

    // Get all product names
    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();

        for (WebElement product : productNames) {
            names.add(product.getText());
        }

        return names;
    }

    // Get all product prices
    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();

        for (WebElement product : productPrices) {
            String price = product.getText().replace("$", "");
            prices.add(Double.parseDouble(price));
        }

        return prices;
    }

    // Add backpack to cart
    public void addBackpack() {
        addBackpackButton.click();
    }

    // Remove backpack from cart
    public void removeBackpack() {
        removeBackpackButton.click();
    }

    // Check whether Add to Cart button is displayed
    public boolean isAddBackpackDisplayed() {
        try {
            return addBackpackButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Check whether Remove button is displayed
    public boolean isRemoveButtonDisplayed() {
        try {
            return removeBackpackButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Get cart count; return 0 when the badge is absent
    public String getCartCount() {
        try {
            return cartBadge.getText();
        } catch (NoSuchElementException e) {
            return "0";
        }
    }

    // Open cart
    public void clickCartIcon() {
        cartIcon.click();
    }

    // Get total number of products displayed
    public int getProductCount() {
        return productNames.size();
    }
}
