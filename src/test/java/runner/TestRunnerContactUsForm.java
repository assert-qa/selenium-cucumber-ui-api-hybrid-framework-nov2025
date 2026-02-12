package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/ContactUs/ContactUsForm.feature",
        glue = {"stepDefinition", "hooks"},
        plugin = {"pretty",
                "html:target/cucumber-reports/ContactUsForm.html",
                "json:target/cucumber-reports/ContactUsForm.json"},
        monochrome = true
)
public class TestRunnerContactUsForm extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
