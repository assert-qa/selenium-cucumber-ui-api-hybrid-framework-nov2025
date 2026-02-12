package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;
import utils.LogUtils;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;
import pages.models.ContactFormData;

public class ContactUsPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public ContactUsPage() {

    }

    // 1. By Locators
    String contactUsButton = setUp.getProperty("CONTACT_US_BUTTON");
    String getInTouchLabelIsVisible = setUp.getProperty("GET_IN_TOUCH_LABEL");
    String customerName = setUp.getProperty("CUSTOMER_NAME");
    String customerEmail = setUp.getProperty("CUSTOMER_EMAIL");
    String customerSubject = setUp.getProperty("CUSTOMER_SUBJECT");
    String customerMessage = setUp.getProperty("CUSTOMER_MESSAGE");
    String browseFileButton = setUp.getProperty("BROWSE_FILE_BUTTON");
    String submitButton = setUp.getProperty("SUBMIT_BUTTON");
    String successfulMessage = setUp.getProperty("SUCCESSFUL_MESSAGE");
    String homeButton = setUp.getProperty("HOME_BUTTON");
    String landingPageImg = setUp.getProperty("LANDING_PAGE_CONTACT_US_IMG");

    // 2. Page actions: features (behaviors) of the page in the form of methods

    public void goToContactUsPage(){
        clickElement(By.xpath(contactUsButton));
    }

    public String verifyGetInTouchIsVisible(){
        if (isElementDisplayed(By.xpath(getInTouchLabelIsVisible))){
            return getElementText(By.xpath(getInTouchLabelIsVisible));
        } else{
            LogUtils.error("GET IN TOUCH is not visible");
            return "";
        }
    }

    public void fillContactForm(ContactFormData data){
        if (data.getName() != null)
            setText(By.xpath(customerName), data.getName());
        if (data.getEmail() != null)
            setText(By.xpath(customerEmail), data.getEmail());
        if (data.getSubject() != null)
            setText(By.xpath(customerSubject), data.getSubject());
        if (data.getMessage() != null)
            setText(By.cssSelector(customerMessage), data.getMessage());
    }

    public void uploadFile(String filePath){
        setText(By.xpath(browseFileButton), filePath);
    }

    public void clickSubmitContactButton(){
        clickElement(By.xpath(submitButton));
    }

    public String verifySuccessfulMessage(){
        if (isElementDisplayed(By.xpath(successfulMessage))){
            return getElementText(By.xpath(successfulMessage));
        } else{
            LogUtils.info("Press OK to proceed!");
            return "";
        }
    }

    public void clickHomeButton(){
        clickElement(By.xpath(homeButton));
    }

    public boolean verifyLandingPage(){
        return isElementDisplayed(By.xpath(landingPageImg));
    }
}