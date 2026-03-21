package steps;

import constants.ConstantGlobal;
import hooks.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import keywords.WebUI;
import pages.LoginPage;
import pages.models.CredentialsData;

public class StepsLoginWithValidCredentials {
    private TestContext testContext;
    private LoginPage loginPage;

    private final String validEmail = ConstantGlobal.VALID_EMAIL;
    private final String validPassword = ConstantGlobal.VALID_PASSWORD;

    public StepsLoginWithValidCredentials(TestContext testContext) {
        this.testContext = testContext;
        this.loginPage = new LoginPage();
    }

    public StepsLoginWithValidCredentials(){
        this(new TestContext());
    }

    @When("I enter correct email address and password")
    public void iEnterCorrectEmailAddressAndPassword() {
        CredentialsData credentialsData = CredentialsData.builder()
                .userEmail(validEmail)
                .userPassword(validPassword)
                .build();

        loginPage.loginAccount(credentialsData);
    }

    @Then("I verify that {string} is visible")
    public void iVerifyThatIsVisible(String expectedText) {
        String actualText = loginPage.getSuccessLogin();

        if ("Logged in as user email".equalsIgnoreCase(expectedText.trim())) {
            WebUI.verifyEquals(actualText, validEmail,
                    "Success login label is not showing the expected user email.");
            return;
        }

        WebUI.verifyEquals(actualText, expectedText,
                "Success login label is different from expected text.");
    }
}
