package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;

import java.util.Properties;
import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class TestCasePage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public TestCasePage() {
    }

    // 1. By Locators
    String testCasePage = setUp.getProperty("GO_TO_TEST_CASE_BUTTON");
    String testCaseLabel = setUp.getProperty("TEST_CASE_LABEL");


    // 2. Page actions: features (behaviors) of the page in the form of methods
    public void goToTestCasePage() {
        clickElement(By.xpath(testCasePage));
    }

    public String verifyNavigatedToTestCasePage(){
        verifyElementVisible(By.xpath(testCaseLabel));
        return getElementText(By.xpath(testCaseLabel));
    }
}
