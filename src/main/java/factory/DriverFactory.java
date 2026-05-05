package factory;

import constants.ConstantGlobal;
import helpers.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    static{
        PropertiesHelper.loadAllFiles();
    }

    public WebDriver createDriver() {
        WebDriver driver = setupBrowser(PropertiesHelper.getValue("BROWSER"));
        DriverManager.setDriver(driver);
        return driver;
    }

    public WebDriver createDriver(String browserName) {
        WebDriver driver = setupBrowser(browserName);
        DriverManager.setDriver(driver);
        return driver;
    }

    private WebDriver setupBrowser(String browserName) {
        WebDriver driver = switch (browserName.trim().toLowerCase()) {
            case "chrome" -> initChromeDriver();
            case "firefox" -> initFirefoxDriver();
            case "edge" -> initEdgeDriver();
            default -> {
                System.out.println("Browser: " + browserName + " is invalid, Launching Chrome browser default...");
                yield initChromeDriver();
            }
        };
        return driver;
    }

    private WebDriver initChromeDriver() {
        WebDriver driver;
        System.out.println("Launching Chrome browser...");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("excludeSwitches", new String[]{"disable-popup-blocking"});
        options.addArguments("--disable-popup-blocking");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        if (ConstantGlobal.HEADLESS) { // default: false
            options.addArguments("--headless=new");
            options.addArguments("window-size=1800,900");
        } else {
            options.addArguments("--start-maximized");
        }
        driver = new ChromeDriver(options);
        return driver;
    }

    private WebDriver initEdgeDriver() {
        WebDriver driver;
        System.out.println("Launching Edge browser...");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("excludeSwitches", new String[]{"disable-popup-blocking"});
        options.addArguments("--disable-popup-blocking");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        if (ConstantGlobal.HEADLESS) { // default: false
            options.addArguments("--headless=new");
            options.addArguments("window-size=1800,900");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new EdgeDriver(options);

        return driver;
    }

    private WebDriver initFirefoxDriver() {
        WebDriver driver;
        System.out.println("Launching Firefox browser...");

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.generation.enabled", false);
        options.addPreference("browser.startup.homepage_override.mstone", "ignore");
        options.addPreference("startup.homepage_welcome_url", "");
        options.addPreference("startup.homepage_welcome_url.additional", "");
        // Disable notifications
        options.addPreference("permissions.default.desktop-notification", 2);

        if (ConstantGlobal.HEADLESS) { // default: false
            options.addArguments("--headless=new");
            options.addArguments("window-size=1800,900");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new FirefoxDriver(options);

        return driver;
    }
}
