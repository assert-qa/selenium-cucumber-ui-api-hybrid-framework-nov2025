package hooks;

import factory.DriverManager;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.RegisterPage;

public class TestContext {
    private LoginPage loginPage;
    private RegisterPage registerPage;

    public TestContext() {
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage() : loginPage;
    }

    public RegisterPage getRegisterPage() {
        return (registerPage == null) ? registerPage = new RegisterPage() : registerPage;
    }

    public static void reset() {
        // No-op: context is scenario object, cleanup is handled by hooks and DriverManager.
    }
}
