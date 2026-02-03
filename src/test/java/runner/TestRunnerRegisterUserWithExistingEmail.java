package runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/Login/RegisterUserWithExistingEmail.feature",
        glue = {"stepDefinition", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/RegisterUserWithExistingEmail.html",
                "json:target/cucumber-reports/RegisterUserWithExistingEmail.json"
        },
        monochrome = true
)
public class TestRunnerRegisterUserWithExistingEmail extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
