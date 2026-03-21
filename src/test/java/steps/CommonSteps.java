package steps;

import factory.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import keywords.WebUI;
import org.openqa.selenium.By;
import pages.LoginPage;

import java.util.Locale;
import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class CommonSteps {

    private final LoginPage loginPage = new LoginPage();
    private final Properties setUp = loadAllFiles();

    @Given("I launch the browser")
    public void iLaunchTheBrowser() {
        WebUI.verifyTrue(DriverManager.getDriver() != null,
                "WebDriver is not initialized. Please check CucumberHooks @Before setup.");
    }

    @When("I navigate to url {string}")
    public void iNavigateToUrl(String url) {
        WebUI.openURL(url);
    }

    @Then("I verify that login page is visible successfully")
    public void iVerifyThatLoginPageIsVisibleSuccessfully() {
        WebUI.verifyElementVisible(By.xpath(setUp.getProperty("EMAIL")), "Email field is not visible.");
        WebUI.verifyElementVisible(By.xpath(setUp.getProperty("PASSWORD")), "Password field is not visible.");
        WebUI.verifyElementVisible(By.xpath(setUp.getProperty("SIGN_IN_BUTTON")), "Sign in button is not visible.");
    }

    @When("I click {string} button")
    public void iClickButton(String buttonName) {
        String normalizedButton = buttonName.trim().toLowerCase(Locale.ROOT);

        switch (normalizedButton) {
            case "sign in", "login" -> loginPage.clickSignInButton();
            case "log out", "logout" -> loginPage.clickLogOutButton();
            default -> throw new IllegalArgumentException("Unsupported button in common step: " + buttonName);
        }
    }
}
