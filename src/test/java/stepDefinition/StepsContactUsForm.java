package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import pages.ContactUsPage;
import pages.models.ContactFormData;
import reports.ExtentTestManager;
import utils.FileUtils;
import utils.LogUtils;

public class StepsContactUsForm {
    private TestContext testContext;
    private ContactUsPage contactUsPage;
    private ContactFormData contactFormData;

    public StepsContactUsForm(TestContext testContext) {
        this.testContext = testContext;
        this.contactUsPage = new ContactUsPage();
        this.contactFormData = new ContactFormData();
    }

    public StepsContactUsForm() {
        this(new TestContext());
    }

    @When("I enter contact details with name {string}, email {string}, subject {string}, and message {string}")
    public void iEnterContactDetails(String name, String email, String subject, String message) {
        contactFormData = ContactFormData.builder()
                .name(name)
                .email(email)
                .subject(subject)
                .message(message)
                .build();
        contactUsPage.fillContactForm(contactFormData);
        LogUtils.info("Entered contact details: " + name + ", " + email + ", " + subject + ", " + message);
        ExtentTestManager.logMessage("Entered contact details successfully");
    }

    @When("I upload file {string}")
    public void iUploadFile(String filePath) {
        String fullPath = FileUtils.getFilePath(filePath);

        // Validate file exists before attempting upload
        if (!FileUtils.fileExists(fullPath)) {
            LogUtils.error("File not found: " + fullPath);
            ExtentTestManager.logMessage("ERROR: File not found at: " + fullPath);
            throw new RuntimeException("File not found: " + fullPath +
                "\nExpected location: data/" + filePath +
                "\nPlease create the file in the data folder.");
        }

        contactUsPage.uploadFile(fullPath);
        LogUtils.info("Uploaded file: " + fullPath);
        ExtentTestManager.logMessage("File uploaded: " + fullPath);
    }

    @Then("I verify success message {string} is visible")
    public void iVerifySuccessMessageIsVisible(String successMessage) {
        String actualMessage = contactUsPage.verifySuccessfulMessage();
        WebUI.verifyEquals(actualMessage, successMessage);
        LogUtils.info("Success message verified: " + successMessage);
        ExtentTestManager.logMessage("Verified success message: " + successMessage);
    }

    @Then("I verify that landed to home page successfully")
    public void iVerifyThatLandedToHomePageSuccessfully() {
        boolean isHomePage = contactUsPage.verifyLandingPage();
        if (isHomePage) {
            LogUtils.info("Successfully landed on home page");
            ExtentTestManager.logMessage("Verified: Landed to home page successfully");
        } else {
            LogUtils.error("Failed to land on home page");
        }
    }
}
