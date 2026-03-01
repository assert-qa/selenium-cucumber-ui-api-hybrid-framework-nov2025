package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import pages.TestCasePage;
import reports.ExtentTestManager;
import utils.LogUtils;

public class StepsVerifyTestCasesPage {
    private TestContext testContext;
    private TestCasePage testCasePage;

    public StepsVerifyTestCasesPage(TestContext testContext) {
        this.testContext = testContext;
        this.testCasePage = new TestCasePage();
    }

    @Then("I verify user is navigated to test cases page successfully")
    public void iVerifyUserIsNavigatedToTestCasesPageSuccessfully() {
        String actualResult = testCasePage.verifyNavigatedToTestCasePage();
        WebUI.verifyEquals(actualResult, "TEST CASES");
        LogUtils.info("Verified user is navigated to test cases page successfully");
        ExtentTestManager.logMessage("Verified: User is navigated to test cases page successfully");
    }
}
