package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "src/test/java/features/Home/TestCasesPage.feature",
        glue = {"stepDefinition", "hooks"},
        plugin = {"pretty",
                "html:target/cucumber-reports/TestCasesPage.html",
                "json:target/cucumber-reports/TestCasesPage.json"
        },
        monochrome = true
)
public class TestRunnerTestCasesPage extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
