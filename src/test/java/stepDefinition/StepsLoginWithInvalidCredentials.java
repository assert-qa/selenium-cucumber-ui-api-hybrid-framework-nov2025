package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import pages.LoginPage;
import pages.models.CredentialsData;
import reports.ExtentTestManager;

public class StepsLoginWithInvalidCredentials {
    private TestContext testContext;
    private LoginPage loginPage;
    private CredentialsData credentialsData;

    // Constructor for Cucumber Dependency Injection
    public StepsLoginWithInvalidCredentials(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = new LoginPage();
        this.credentialsData = new CredentialsData();
    }

    // Zero-argument constructor as fallback
    public StepsLoginWithInvalidCredentials() {
        this(new TestContext());
    }

    @When("I enter email {string} and password {string}")
    public void iEnterEmailAndPassword(String email, String password) {
        credentialsData = CredentialsData.builder()
                .userEmail(email)
                .userPassword(password)
                .build();

        loginPage.loginAccount(credentialsData);
        ExtentTestManager.logMessage("Entered email: " + email);
        ExtentTestManager.logMessage("Entered password: ********");
    }

    @Then("I verify error {string} is visible")
    public void iVerifyErrorIsVisible(String errorMessage) {
        String actualError = loginPage.verifyInlineErrorMessage();
        WebUI.verifyEquals(actualError, errorMessage);
        ExtentTestManager.logMessage("Verified error message: " + errorMessage);
    }
}
