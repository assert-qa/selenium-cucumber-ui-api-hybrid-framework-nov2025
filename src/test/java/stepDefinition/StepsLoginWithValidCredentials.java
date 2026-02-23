package stepDefinition;

import constants.ConstantGlobal;
import hooks.TestContext;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.models.CredentialsData;
import reports.ExtentTestManager;

public class StepsLoginWithValidCredentials {
    private TestContext testContext;
    private LoginPage loginPage;
    private CredentialsData credentialsData;

    // Constructor for Cucumber Dependency Injection
    public StepsLoginWithValidCredentials(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = new LoginPage();
        this.credentialsData = new CredentialsData();
    }

    // Zero-argument constructor as fallback
    public StepsLoginWithValidCredentials() {
        this(new TestContext());
    }

    @When("I enter correct email address and password")
    public void iEnterCorrectEmailAddressAndPassword() {
        String email = ConstantGlobal.EMAIL;
        String password = ConstantGlobal.PASSWORD;

        credentialsData = CredentialsData.builder()
                .userEmail(email)
                .userPassword(password)
                .build();

        loginPage.loginAccount(credentialsData);
        ExtentTestManager.logMessage("Entered email: " + email);
        ExtentTestManager.logMessage("Entered password: ********");
    }
}