package common;

import constants.ConstantGlobal;
import factory.DriverFactory;
import factory.DriverManager;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    public static void createDriver(){
        if (DriverManager.getDriver() == null) {
            new DriverFactory().createDriver(ConstantGlobal.BROWSER);
        }
    }

    public static void createDriver(String browser){
        if (DriverManager.getDriver() == null) {
            new DriverFactory().createDriver(browser);
        }
    }

    public static WebDriver setupBrowser(String browserName){
        return new DriverFactory().createDriver(browserName);
    }

    public static WebDriver initChromeDriver(){
        return new DriverFactory().createDriver("chrome");
    }

    public static WebDriver initFirefoxDriver(){
        return new DriverFactory().createDriver("firefox");
    }

    public static WebDriver initEdgeDriver(){
        return new DriverFactory().createDriver("edge");
    }

}
