package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import pages.LoginPage;
import reports.ExtentTestManager;

public class StepsLogoutUser {
    private TestContext testContext;
    private LoginPage loginPage;

    public StepsLogoutUser(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }

    public StepsLogoutUser() {
        this(new TestContext());
    }

    @Then("I verify that user is navigated to login page")
    public void iVerifyThatUserIsNavigatedToLoginPage() {
        String actualResult = loginPage.verifyLoginLabel();
        WebUI.verifyEquals(actualResult, "Login to your account");
        ExtentTestManager.logMessage("Verified: User is navigated to login page");
    }
}
