package pages;

import factory.DriverFactory;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.openqa.selenium.By;
import pages.models.ContactFormData;
import pages.models.CredentialsData;
import pages.models.RegisterFormData;
import utils.LogUtils;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class LoginPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public LoginPage() {

    }

    // 1. By Locators
    String goToLoginButton = setUp.getProperty("GO_TO_LOGIN_BUTTON");
    String homePage = setUp.getProperty("HOME_PAGE");
    String newUserSignUp = setUp.getProperty("NEW_USER_SIGN_UP");
    String registerLabel = setUp.getProperty("REGISTER_LABEL");
    String signUpButton = setUp.getProperty("SIGN_UP_BUTTON");
    String name = setUp.getProperty("USER_NAME");
    String emailAddress = setUp.getProperty("USER_EMAIL_ADDRESS");
    String accountInformationLabel = setUp.getProperty("ACCOUNT_INFORMATION_LABEL");
    String createAccountButton = setUp.getProperty("CREATE_ACCOUNT_BUTTON");
    String mrGender = setUp.getProperty("Mr_GENDER");
    String mrsGender = setUp.getProperty("Mrs_GENDER");
    String password = setUp.getProperty("USER_PASSWORD");
    String days = setUp.getProperty("DAYS");
    String months = setUp.getProperty("MONTHS");
    String years = setUp.getProperty("YEARS");
    String checkBoxSignUp = setUp.getProperty("CHECKBOX_SIGN_UP");
    String checkBoxReceiveSpecialOffers = setUp.getProperty("CHECKBOX_RECEIVE_SPECIAL_OFFERS");
    String userFirstName = setUp.getProperty("USER_FIRST_NAME");
    String userLastName = setUp.getProperty("USER_LAST_NAME");
    String userCompany = setUp.getProperty("USER_COMPANY");
    String userAddress1 = setUp.getProperty("USER_ADDRESS1");
    String userAddress2 = setUp.getProperty("USER_ADDRESS2");
    String userCountry = setUp.getProperty("USER_COUNTRY");
    String userState = setUp.getProperty("USER_STATE");
    String userCity = setUp.getProperty("USER_CITY");
    String userZipCode = setUp.getProperty("USER_ZIPCODE");
    String userMobileNumber = setUp.getProperty("USER_MOBILE_NUMBER");
    String accountCreatedLabel = setUp.getProperty("ACCOUNT_CREATED_LABEL");
    String continueButton = setUp.getProperty("CONTINUE_BUTTON");
    String deleteButton = setUp.getProperty("DELETE_BUTTON");
    String loginLabel = setUp.getProperty("LOGIN_LABEL");
    String createdUserEmail = setUp.getProperty("CREATED_USER_EMAIL");
    String createdUserPassword = setUp.getProperty("CREATED_USER_PASSWORD");
    String loginButton = setUp.getProperty("LOGIN_BUTTON");
    String inLineErrorMessage = setUp.getProperty("IN_LINE_ERROR_MESSAGE");
    String signUpInLineErrorMessage = setUp.getProperty("SIGN_UP_IN_LINE_ERROR_MESSAGE");
    String logOutButton = setUp.getProperty("LOG_OUT_BUTTON");
    String deleteAccountButton = setUp.getProperty("DELETE_ACCOUNT_BUTTON");
    String accountDeletedLabel = setUp.getProperty("ACCOUNT_DELETED_LABEL");

    public void openLoginPage(){
        WebUI.openURL(PropertiesHelper.getValue("URL"));
        WebUI.maximizeWindow();
    }

    // LOGIN | LOGOUT | REGISTER TEST STEP
    public void goToLoginPage(){
        clickElement(By.xpath(goToLoginButton));
    }

    public boolean verifyHomePageIsVisible(){
        return isElementDisplayed(By.xpath(homePage));
    }

    public String verifyRegisterIsVisible(){
        if (isElementDisplayed(By.cssSelector(registerLabel))){
            return getElementText(By.cssSelector(registerLabel));
        } else {
            return "Register label is not visible";
        }
    }

    public void typeUserInformation(String userName, String userEmail){
        setText(By.xpath(name), userName);
        setText(By.xpath(emailAddress), userEmail);
    }

    public void clickSignUpButton(){
        clickElement(By.xpath(signUpButton));
    }

    public String verifyAccountInformationIsVisible(){
        if (isElementDisplayed(By.xpath(accountInformationLabel))){
            return getElementText(By.xpath(accountInformationLabel));
        } else {
            return "Account Information is not visible";
        }
    }

    private void selectGender(RegisterFormData data){
        if (data.getTitle().equalsIgnoreCase("Mr.") || data.getTitle().equalsIgnoreCase("Mrs."))
            selectRadioButton(By.id(data.getTitle().equalsIgnoreCase("Mr.") ? mrGender : mrsGender));
    }

    public void fillAccountInformation(RegisterFormData data){
        selectGender(data);
        if (data.getPassword() != null)
            setText(By.xpath(password), data.getPassword());
        if (data.getDay() != null)
            selectDropDown(By.xpath(days), data.getDay());
        if (data.getMonth() != null)
            selectDropDown(By.xpath(months), data.getMonth());
        if (data.getYear() != null)
            selectDropDown(By.xpath(years), data.getYear());
    }

    public void selectCheckBox(String label) {
        if (label.equalsIgnoreCase("Sign up for our newsletter!")) {
            clickElement(By.cssSelector(checkBoxSignUp));
        } else if (label.equalsIgnoreCase("Receive special offers from our partners!")) {
            clickElement(By.cssSelector(checkBoxReceiveSpecialOffers));
        } else {
            LogUtils.error("Checkbox label not recognized: " + label);
        }
    }

    public void fillAddressInformation(RegisterFormData data){
        if (data.getFirstName() != null)
            setText(By.xpath(userFirstName), data.getFirstName());
        if (data.getLastName() != null)
            setText(By.cssSelector(userLastName), data.getLastName());
        if (data.getCompany() != null)
            setText(By.cssSelector(userCompany), data.getCompany());
        if (data.getAddress() != null)
            setText(By.cssSelector(userAddress1), data.getAddress());
        if (data.getAddress2() != null)
            setText(By.cssSelector(userAddress2), data.getAddress2());
        if (data.getCountry() != null)
            selectDropDown(By.xpath(userCountry), data.getCountry());
        if (data.getState() != null)
            setText(By.xpath(userState), data.getState());
        if (data.getCity() != null)
            setText(By.xpath(userCity), data.getCity());
        if (data.getZipCode() != null)
            setText(By.xpath(userZipCode), data.getZipCode());
        if (data.getMobileNumber() != null)
            setText(By.cssSelector(userMobileNumber), data.getMobileNumber());
    }

    public void createAccountButton(){
        clickElement(By.xpath(createAccountButton));
    }

    public String verifyAccountWasCreatedIsVisible(){
        if (isElementDisplayed(By.xpath(accountCreatedLabel))){
            return getElementText(By.xpath(accountCreatedLabel));
        }else {
            return "Account Created! is not visible";
        }
    }

    public void clickContinueButton(){
        clickElement(By.xpath(continueButton));
    }

    public String verifyLoggedInAsUserNameIsVisible(String userName){
        // If caller passes "username" (placeholder) or null/empty, check for any logged-in username by prefix
        String xpath;
        if (userName == null || userName.trim().isEmpty() || userName.equalsIgnoreCase("username")) {
            // find element that contains the prefix "Logged in as "
            xpath = "//*[contains(text(),'Logged in as ')]";
        } else {
            // existing behaviour: look for specific username text (keeps current logic)
            xpath = String.format("//b[contains(text(), '%s')]", userName);
        }

        if (isElementDisplayed(By.xpath(xpath))) {
            return getElementText(By.xpath(xpath));
        } else {
            return "User is not logged in";
        }
    }

    public void clickDeleteAccountButton(){
        clickElement(By.xpath(deleteButton));
    }

    public void loginAccount(CredentialsData data){
        if (data.getUserEmail() != null)
            setText(By.xpath(createdUserEmail), data.getUserEmail());
        if (data.getUserPassword() != null)
            setText(By.xpath(createdUserPassword), data.getUserPassword());
    }

    public String verifyNewUserSignupIsVisible() {
        if (isElementDisplayed(By.xpath(newUserSignUp))) {
            return getElementText(By.xpath(newUserSignUp));
        } else {
            return "New User Signup! text is not visible";
        }
    }

    public String verifyAccountDeletedIsVisible() {
        try {
            // Wait for element to be visible
            waitForElementVisible(By.xpath(accountDeletedLabel), 10);

            if (isElementDisplayed(By.xpath(accountDeletedLabel))) {
                String actualText = getElementText(By.xpath(accountDeletedLabel));
                LogUtils.info("Account Deleted Label found: " + actualText);
                return actualText;
            } else {
                LogUtils.error("Account Deleted Label is not displayed");
                return "ACCOUNT DELETED! is not visible";
            }
        } catch (Exception e) {
            LogUtils.error("Error finding Account Deleted Label: " + e.getMessage());
            return "ACCOUNT DELETED! is not visible";
        }
    }

    public void clickLoginButton(){
        clickElement(By.xpath(loginButton));
    }

    public String verifyInlineErrorMessage(){
        if(isElementDisplayed(By.xpath(inLineErrorMessage))){
            return getElementText(By.xpath(inLineErrorMessage));
        }else{
            return "Your email or password is incorrect! is not visible";
        }
    }

    public String verifySignUpInLineErrorMessage(){
        if (isElementDisplayed(By.xpath(signUpInLineErrorMessage))){
            return getElementText(By.xpath(signUpInLineErrorMessage));
        } else {
            return "Email Address already exist! is not visible";
        }
    }

    public String verifyLoginLabel(){
        if(isElementDisplayed(By.xpath(loginLabel))){
            return getElementText(By.xpath(loginLabel));
        }else{
            return "Login to your account is not visible";
        }
    }

    public void clickLogoutButton(){
        clickElement(By.xpath(logOutButton));
    }


    public void deleteAccountButton(){
        clickElement(By.xpath(deleteAccountButton));
    }


}
