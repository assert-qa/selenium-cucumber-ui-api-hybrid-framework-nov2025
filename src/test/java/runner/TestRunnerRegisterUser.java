package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/java/features/Login/RegisterUser.feature",
        tags = "@smokeTest",
        glue = {"stepDefinition",
                "hooks"},
        plugin = {
                "hooks.CucumberReportListener",
                "pretty",
                "html:target/cucumber-reports/RegisterUser.html",
                "json:target/cucumber-reports/RegisterUser.json"
        },
        monochrome = true
)
@Test
public class TestRunnerRegisterUser extends AbstractTestNGCucumberTests {
    @DataProvider
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
