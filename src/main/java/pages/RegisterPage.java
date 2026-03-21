package pages;

import factory.DriverFactory;
import keywords.WebUI;
import org.openqa.selenium.By;
import pages.models.RegisterFormData;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class RegisterPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public RegisterPage(){

    }

    String registerEmail = setUp.getProperty("REGISTER_EMAIL");
    String registerPassword = setUp.getProperty("REGISTER_PASSWORD");
    String registerConfirmPassword = setUp.getProperty("REGISTER_CONF_PASSWORD");
    String registerButton = setUp.getProperty("CREATE_ACCOUNT_BUTTON");
    String registerPage = setUp.getProperty("REGISTER_PAGE");

    public void goToRegisterPage(){
        WebUI.clickElement(By.xpath(registerPage));
    }

    public void setRegisterAccount(RegisterFormData data){
        if (data.getEmail() != null)
            WebUI.setText(By.xpath(registerEmail), data.getEmail());
        if (data.getPassword() != null)
            WebUI.setText(By.xpath(registerPassword), data.getPassword());
        if (data.getConfirmPassword() != null)
            WebUI.setText(By.xpath(registerConfirmPassword), data.getConfirmPassword());
    }

    public void createAccountButton(){
        WebUI.clickElement(By.xpath(registerButton));
    }

}
