package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/Login/LoginWithInvalidCredentials.feature",
        glue = {"stepDefinition", "hooks"},
        tags = "@smokeTest",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/LoginWithInvalidCredentials.html",
                "json:target/cucumber-reports/LoginWithInvalidCredentials.json"
        },
        monochrome = true
)
public class TestRunnerLoginWithInvalidCredentials extends AbstractTestNGCucumberTests{
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
