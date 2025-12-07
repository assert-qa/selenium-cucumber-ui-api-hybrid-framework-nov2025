package pages;

import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class CartPage extends DriverFactory {
    WebDriver driver;

    private static Properties setUp = loadAllFiles();

    // 1. Constructor of the page class
    public CartPage(WebDriver driver){
        this.driver = driver;
    }

    // 2. By Locators
    String cartPage= setUp.getProperty("CART_PAGE");
    String footer = setUp.getProperty("CART_FOOTER");
    String subscriptionLabel = setUp.getProperty("CART_SUBSCRIPTION_LABEL");
    String inputSubscribeEmail = setUp.getProperty("INPUT_SUBSCRIBE_EMAIL");
    String subscribeButton = setUp.getProperty("CART_SUBSCRIBE_BUTTON");
    String successAlertMessage = setUp.getProperty("SUCCESS_ALERT_MESSAGE");
    String cartTableData = setUp.getProperty("CART_TABLE_DATA");
    String verifyShoppingCartPage = setUp.getProperty("VERIFY_SHOPPING_CART_PAGE");
    String proceedToCheckoutButton = setUp.getProperty("PROCEED_TO_CHECKOUT");
    String verifyCheckOutPopUp = setUp.getProperty("VERIFY_CHECKOUT_POP_UP");
    String registerOrLogin = setUp.getProperty("REGISTER_OR_LOGIN");
    String verifyAddressDetail = setUp.getProperty("VERIFY_ADDRESS_DETAIL");
    String verifyReviewYourOrder = setUp.getProperty("VERIFY_REVIEW_YOUR_ORDER");
    String typeCommentBeforeCheckout = setUp.getProperty("TYPE_COMMENT_BEFORE_CHECKOUT");
    String placeOrderButton = setUp.getProperty("PLACE_ORDER_BUTTON");
    String deleteProductButton = setUp.getProperty("DELETE_PRODUCT_BUTTON");
    String cartProductRow = setUp.getProperty("CART_PRODUCT_NOW");

    // 3. Page actions: features (behaviors) of the page in the form of methods
    public void goToCartPage(){
        clickElement(By.xpath(cartPage));
    }

    public void scrollDown(){
        scrollToElement(By.cssSelector(footer));
    }

    public String verifySubscriptionLabelIsVisible(){
        return getElementText(By.xpath(subscriptionLabel));
    }

    public void enterEmail(String userEmail){
        setText(By.id(inputSubscribeEmail), userEmail);
    }



}
