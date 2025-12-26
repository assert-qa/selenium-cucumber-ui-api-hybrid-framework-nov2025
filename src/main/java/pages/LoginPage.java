package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class LoginPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    LoginPage() {

    }

    // 1. By Locators
    String goToLoginButton = setUp.getProperty("GO_TO_LOGIN_BUTTON");
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
    String logOutButton = setUp.getProperty("LOG_OUT_BUTTON");
    String deleteAccountButton = setUp.getProperty("DELETE_ACCOUNT_BUTTON");
    String verifyAccountDeletedLabel = setUp.getProperty("VERIFY_ACCOUNT_DELETED_LABEL");

    // LOGIN | LOGOUT | REGISTER TEST STEP
    public void goToLoginPage(){
        clickElement(By.xpath(goToLoginButton));
    }

    public String verifyRegisterIsVisible(){
        if (isElementDisplayed(By.xpath(registerLabel))){
            return getElementText(By.xpath(registerLabel));
        } else {
            return "Register label is not visible";
        }
    }

    public void typeUserInformation(String userName, String userEmail){
        setText(By.xpath(name), userEmail);
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

    private void selectGender(String gender){
        if (gender.equalsIgnoreCase("Mr.")){
            selectRadioButton(By.xpath(mrGender));
        } else if (gender.equalsIgnoreCase("Mrs.")){
            selectRadioButton(By.xpath(mrsGender));
        }
    }

    public void fillAccountInformation(String title, String userPassword, String date, String month, String year){
        selectGender(title);
        setText(By.xpath(password), userPassword);
        selectDropDown(By.xpath(days), date);
        selectDropDown(By.xpath(months), month);
        selectDropDown(By.xpath(years), year);
    }

    public void selectCheckBox(){
        clickElement(By.cssSelector(checkBoxSignUp));
        clickElement(By.cssSelector(checkBoxReceiveSpecialOffers));
    }

    public void fillAddressInformation(String firstName, String lastName, String company,
                                       String address, String address2, String country,
                                       String state, String city, String zipCode, String mobileNumber){

        setText(By.xpath(userFirstName), firstName);
        setText(By.cssSelector(userLastName), lastName);
        setText(By.cssSelector(userCompany), company);
        setText(By.cssSelector(userAddress1), address);
        setText(By.cssSelector(userAddress2), address2);
        selectDropDown(By.xpath(userCountry), country);
        setText(By.xpath(userState), state);
        setText(By.xpath(userCity), city);
        setText(By.xpath(userZipCode), zipCode);
        setText(By.cssSelector(userMobileNumber), mobileNumber);
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
}
