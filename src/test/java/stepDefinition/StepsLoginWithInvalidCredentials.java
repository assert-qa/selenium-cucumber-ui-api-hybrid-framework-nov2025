package stepDefinition;

import common.BaseTest;
import factory.DriverManager;
import hooks.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import pages.LoginPage;
import reports.ExtentTestManager;
import utils.LogUtils;

public class StepsLoginWithInvalidCredentials {
    private LoginPage loginPage;

    public StepsLoginWithInvalidCredentials() {
        this(new TestContext());
    }

    public StepsLoginWithInvalidCredentials(TestContext testContext){
        loginPage = testContext.getLoginPage();
    }

    @Given("I launch browser")
    public void iLaunchBrowser() {
        if (DriverManager.getDriver() == null) {
            BaseTest.createDriver();
            LogUtils.info("Browser launched successfully");
        }
        loginPage.openLoginPage();
    }

    @When("I navigate to url {string}")
    public void iNavigateToUrl(String url) {
        WebUI.openURL(url);
        ExtentTestManager.logMessage("Navigated to: " + url);
    }

    @Then("I verify that home page is visible successfully")
    public void iVerifyThatHomePageIsVisibleSuccessfully() {
        if (loginPage.verifyHomePageIsVisible()) {
            LogUtils.info("Home page is visible successfully");
            ExtentTestManager.logMessage("Home page is visible successfully");
        } else {
            LogUtils.error("Home page is not visible");
        }
    }

    @When("I click on {string} button")
    public void iClickOnButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Signup / Login")) {
            loginPage.goToLoginPage();
            ExtentTestManager.logMessage("Clicked on '" + buttonName + "' button");
        }
    }

    @Then("I verify {string} is visible")
    public void iVerifyIsVisible(String expectedText) {
        String actualResult;
        if (expectedText.equals("Login to your account")) {
            actualResult = loginPage.verifyLoginLabel();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: '" + expectedText + "' is visible");
            return;
        }
    }

    @When("I enter incorrect email address {string} and password {string}")
    public void iEnterIncorrectEmailAddressAndPassword(String email, String password) {
        loginPage.loginAccount(email, password);

        ExtentTestManager.logMessage("Entered email: " + email);
        ExtentTestManager.logMessage("Entered password: ********");
    }

    @Then("I verify error {string} is visible")
    public void iVerifyErrorIsVisible(String errorMessage) {
        String actualError = loginPage.verifyInlineErrorMessage();
        WebUI.verifyEquals(actualError, "Your email or password is incorrect!");
    }
}
