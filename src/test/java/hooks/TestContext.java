package hooks;

import factory.DriverFactory;
import factory.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import pages.*;
import utils.LogUtils;

public class TestContext {
    private static TestContext instance;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private ContactUsPage contactUsPage;
    private HomePage homePage;
    private LoginPage loginPage;
    private PaymentPage paymentPage;
    private ProductPage productPage;
    private TestCasePage testCasePage;

    public TestContext(){
        if (instance == null) {
            instance = this;
            ThreadGuard.protect(new DriverFactory().createDriver());
            LogUtils.info("Driver initialized in TestContext - " + getDriver());
            this.testCasePage = new TestCasePage();
        }
    }

    public WebDriver getDriver(){
        return DriverManager.getDriver();
    }

    public CartPage getCartPage(){
        return (cartPage == null) ? cartPage = new CartPage() : cartPage;
    }

    public CheckoutPage getCheckoutPage(){
        return (checkoutPage == null) ? checkoutPage = new CheckoutPage() : checkoutPage;
    }

    public ContactUsPage getContactUsPage(){
        return (contactUsPage == null) ? contactUsPage = new ContactUsPage() : contactUsPage;
    }

    public HomePage getHomePage(){
        return (homePage == null) ? homePage = new HomePage() : homePage;
    }

    public LoginPage getLoginPage(){
        return (loginPage == null) ? loginPage = new LoginPage() : loginPage;
    }

    public PaymentPage getPaymentPage(){
        return (paymentPage == null) ? paymentPage = new PaymentPage() : paymentPage;
    }

    public ProductPage getProductPage(){
        return (productPage == null) ? productPage = new ProductPage() : productPage;
    }

    public TestCasePage getTestCasePage() {
        return (testCasePage == null) ? testCasePage = new TestCasePage() : testCasePage;
    }

    public static void reset() {
        instance = null;
    }
}
