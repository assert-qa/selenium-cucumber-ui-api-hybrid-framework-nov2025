package stepDefinition;

import common.BaseTest;
import constants.ConstantGlobal;
import factory.DriverManager;
import hooks.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import keywords.WebUI;
import pages.ContactUsPage;
import pages.LoginPage;
import reports.ExtentTestManager;
import utils.LogUtils;

public class CommonSteps {
    private TestContext testContext;
    private LoginPage loginPage;
    private ContactUsPage contactUsPage;
    private static String GENERATED_NAME = null;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }

    public CommonSteps() {
        this(new TestContext());
    }

    public static void setGeneratedName(String name) {
        GENERATED_NAME = name;
    }

    @Given("I launch the browser")
    public void iLaunchTheBrowser() {
        if (DriverManager.getDriver() == null) {
            BaseTest.createDriver();
            LogUtils.info("Browser launched successfully");
            ExtentTestManager.logMessage("Browser launched successfully");
        }
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
        } else if (buttonName.equalsIgnoreCase("Contact Us")) {
            contactUsPage.goToContactUsPage();
            ExtentTestManager.logMessage("Clicked on 'Contact Us' button");
        }
    }

    @When("I click {string} button")
    public void iClickButton(String buttonName) {
        switch (buttonName.toLowerCase()) {
            case "login":
                loginPage.clickLoginButton();
                ExtentTestManager.logMessage("Clicked 'login' button");
                break;
            case "signup":
                loginPage.clickSignUpButton();
                ExtentTestManager.logMessage("Clicked 'Signup' button");
                break;
            case "create account":
                loginPage.createAccountButton();
                ExtentTestManager.logMessage("Clicked 'Create Account' button");
                break;
            case "continue":
                loginPage.clickContinueButton();
                ExtentTestManager.logMessage("Clicked 'Continue' button");
                break;
            case "delete account":
                WebUI.sleep(Double.parseDouble(ConstantGlobal.HARD_WAIT_TIMEOUT));
                loginPage.clickDeleteAccountButton();
                ExtentTestManager.logMessage("Clicked 'Delete Account' button");
                break;
            case "logout":
                loginPage.clickLogoutButton();
                ExtentTestManager.logMessage("Clicked 'Logout' button");
                break;
            case "submit":
                contactUsPage.clickSubmitContactButton();
                ExtentTestManager.logMessage("Clicked 'Submit' button");
                break;
            case "home":
                contactUsPage.clickHomeButton();
                ExtentTestManager.logMessage("Clicked 'Home' button");
                break;
            default:
                throw new IllegalArgumentException("Button not supported: " + buttonName);
        }
    }

    @Then("I verify {string} is visible")
    public void iVerifyIsVisible(String expectedText) {
        String actualResult;
        if (expectedText.equals("Login to your account")) {
            actualResult = loginPage.verifyLoginLabel();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: '" + expectedText + "' is visible");
        } else if (expectedText.equals("New User Signup!")) {
            actualResult = loginPage.verifyNewUserSignupIsVisible();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: '" + expectedText + "' is visible");
        } else if (expectedText.equals("GET IN TOUCH")) {
            actualResult = contactUsPage.verifyGetInTouchIsVisible();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: '" + expectedText + "' is visible");
        }
    }

    @Then("I verify that {string} is visible")
    public void iVerifyThatIsVisible(String expectedText) {
        String actualResult = "";

        if (expectedText.equalsIgnoreCase("ENTER ACCOUNT INFORMATION")) {
            actualResult = loginPage.verifyAccountInformationIsVisible();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: " + actualResult);
        } else if (expectedText.equalsIgnoreCase("ACCOUNT CREATED!")) {
            actualResult = loginPage.verifyAccountWasCreatedIsVisible();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: " + actualResult);
        } else if (expectedText.toLowerCase().startsWith("logged in as ")) {
            if (expectedText.equalsIgnoreCase("Logged in as username") || expectedText.toLowerCase().contains(" username")) {
                actualResult = loginPage.verifyLoggedInAsUserNameIsVisible("username");
                if (actualResult.startsWith("Logged in as ")) {
                    LogUtils.info("Verified dynamic logged-in user: " + actualResult);
                    WebUI.verifyEquals(actualResult.startsWith("Logged in as ") ? "Logged in as " : actualResult, "Logged in as ");
                } else {
                    WebUI.verifyEquals(actualResult, expectedText);
                }
            } else {
                String userName = GENERATED_NAME != null ? GENERATED_NAME : ConstantGlobal.USERNAME;
                actualResult = loginPage.verifyLoggedInAsUserNameIsVisible(userName);
                WebUI.verifyEquals(actualResult, expectedText);
            }
            ExtentTestManager.logMessage("Verified: " + actualResult);
        } else if (expectedText.equalsIgnoreCase("ACCOUNT DELETED!")) {
            actualResult = loginPage.verifyAccountDeletedIsVisible();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: " + actualResult);
        } else {
            throw new IllegalArgumentException("Text verification not supported: " + expectedText);
        }
    }

    @Then("I verify error message {string} is visible")
    public void iVerifyErrorMessageIsVisible(String errorMessage) {
        String actualError;
        if (errorMessage.equals("Email Address already exist!")) {
            actualError = loginPage.verifySignUpInLineErrorMessage();
            WebUI.verifyEquals(actualError, errorMessage);
            ExtentTestManager.logMessage("Verified error message: " + errorMessage);
        } else if (errorMessage.equals("Your email or password is incorrect!")) {
            actualError = loginPage.verifyInlineErrorMessage();
            WebUI.verifyEquals(actualError, errorMessage);
            ExtentTestManager.logMessage("Verified error message: " + errorMessage);
        } else {
            throw new IllegalArgumentException("Error message verification not supported: " + errorMessage);
        }
    }
}
