package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/features/Login/LoginWithValidCredentials.feature",
        glue = {"steps", "hooks"},
        monochrome = true,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/login-with-valid-credentials.html",
                "json:target/cucumber-reports/login-with-valid-credentials.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "hooks.CucumberReportListener"
        }
)
public class TestRunnerLoginWithValidCredentials extends AbstractTestNGCucumberTests {
}
