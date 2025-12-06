package common;

import constants.ConstantGlobal;
import factory.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTest {

    public static void createDriver(){
        WebDriver driver = setupBrowser("chrome");
        DriverManager.setDriver(driver);
    }

    public static void createDriver(String browser){
        WebDriver driver = setupBrowser(browser);
        DriverManager.setDriver(driver);
    }

    public static WebDriver setupBrowser(String browserName){
        return switch (browserName.trim().toLowerCase()) {
            case "chrome" -> initChromeDriver();
            case "firefox" -> initFirefoxDriver();
            case "edge" -> initEdgeDriver();
            default -> throw new IllegalArgumentException("Browser " + browserName + " not supported");
        };
    }

    public static WebDriver initChromeDriver(){
        WebDriver driver;
        System.out.println("Launching Chrome browser...");

        ChromeOptions options = new ChromeOptions();
        if (ConstantGlobal.HEADLESS){
            options.addArguments("--headless=new");
            options.addArguments("window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver();
        return driver;
    }

    public static WebDriver initFirefoxDriver(){
        WebDriver driver;
        System.out.println("Launching Firefox browser...");

        FirefoxOptions options = new FirefoxOptions();
        if (ConstantGlobal.HEADLESS){
            options.addArguments("--headless=new");
            options.addArguments("window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new FirefoxDriver();
        return driver;
    }

    public static WebDriver initEdgeDriver(){
        WebDriver driver;
        System.out.println("Launching Edge browser...");

        ChromeOptions options = new ChromeOptions();
        if (ConstantGlobal.HEADLESS){
            options.addArguments("--headless=new");
            options.addArguments("window-size=1920, 1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new EdgeDriver();
        return driver;
    }

}
