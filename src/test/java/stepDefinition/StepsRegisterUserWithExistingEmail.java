package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.When;
import pages.LoginPage;
import reports.ExtentTestManager;
import utils.LogUtils;

public class StepsRegisterUserWithExistingEmail {
    private TestContext testContext;
    private LoginPage loginPage;

    // Constructor for Cucumber Dependency Injection
    public StepsRegisterUserWithExistingEmail(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }

    // Zero-argument constructor as fallback
    public StepsRegisterUserWithExistingEmail() {
        this(new TestContext());
    }

    @When("I enter name {string} and already registered email address {string}")
    public void iEnterNameAndAlreadyRegisteredEmailAddress(String name, String email) {
        LogUtils.info("Entering name: " + name);
        LogUtils.info("Entering already registered email: " + email);

        loginPage.typeUserInformation(name, email);

        ExtentTestManager.logMessage("Entered name: " + name);
        ExtentTestManager.logMessage("Entered already registered email: " + email);
    }
}
