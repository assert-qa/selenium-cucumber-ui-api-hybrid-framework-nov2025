package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import pages.ContactUsPage;
import pages.LoginPage;
import pages.models.RegisterFormData;
import utils.LogUtils;
import helpers.DataFakerHelper;
import reports.ExtentTestManager;

import java.util.Map;

public class StepsRegisterUser {
    private TestContext testContext;
    private LoginPage loginPage;
    private RegisterFormData registerFormData;

    // Constructor for Cucumber Dependency Injection
    public StepsRegisterUser(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = new LoginPage(); // testContext.getLoginPage();
        this.registerFormData = new RegisterFormData();
    }

    // Zero-argument constructor as fallback
    public StepsRegisterUser() {
        this(new TestContext());
    }

    @When("I enter name {string} and email address {string}")
    public void iEnterNameAndEmailAddress(String nameParam, String emailParam) {
        String generatedName = DataFakerHelper.getFaker().name().firstName();
        String generatedEmail = DataFakerHelper.getFaker().internet().emailAddress();

        LogUtils.info("Generated Name: " + generatedName);
        LogUtils.info("Generated Email: " + generatedEmail);

        loginPage.typeUserInformation(generatedName, generatedEmail);

        // Set generated name to CommonSteps for verification
        CommonSteps.setGeneratedName(generatedName);

        ExtentTestManager.logMessage("Entered name: " + generatedName);
        ExtentTestManager.logMessage("Entered email: " + generatedEmail);
    }

    @When("I fill account details with title {string}, password {string}, date of birth {string}, month {string}, year {string}")
    public void iFillAccountDetailsWithTitlePasswordDateOfBirthMonthYear(String title, String password, String day, String month, String year) {
        registerFormData = RegisterFormData.builder()
                .title(title)
                .password(password)
                .day(day)
                .month(month)
                .year(year)
                .build();
        loginPage.fillAccountInformation(registerFormData);
        ExtentTestManager.logMessage("Filled account details");
    }

    @And("I select checkbox {string}")
    public void iSelectCheckbox(String checkboxLabel) {
        loginPage.selectCheckBox(checkboxLabel);
        ExtentTestManager.logMessage("Selected checkbox: " + checkboxLabel);
    }

    @And("I fill address details:")
    public void iFillAddressDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        registerFormData = RegisterFormData.builder()
                .firstName(data.get("First name"))
                .lastName(data.get("Last name"))
                .company(data.get("Company"))
                .address(data.get("Address"))
                .address2(data.get("Address2"))
                .country(data.get("Country"))
                .state(data.get("State"))
                .city(data.get("City"))
                .zipCode(data.get("Zipcode"))
                .mobileNumber(data.get("Mobile Number"))
                .build();

        loginPage.fillAddressInformation(registerFormData);
        ExtentTestManager.logMessage("Filled address details");
    }
}
