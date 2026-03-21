package keywords;

import constants.ConstantGlobal;
import factory.DriverManager;
import helpers.CaptureHelper;
import helpers.PropertiesHelper;
import helpers.SystemHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import reports.AllureManager;

import utils.LogUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class WebUI {
    private final static long EXPLICIT_TIMEOUT = Long.parseLong(ConstantGlobal.EXPLICIT_TIMEOUT);
    private final static long STEP_TIME = Long.parseLong(ConstantGlobal.STEP_TIME);
    private final static long PAGE_LOAD_TIMEOUT = Long.parseLong(ConstantGlobal.PAGE_LOAD_TIMEOUT);

    static {
        PropertiesHelper.loadAllFiles();
    }

    public static void maximizeWindow() {
        DriverManager.getDriver().manage().window().maximize();
        LogUtils.info("Window maximized");
    }

    public static void verifyElementVisible(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        LogUtils.info("Verify " + by + " is displayed");
        Assert.assertTrue(DriverManager.getDriver().findElement(by).isDisplayed(), "Element not visible.");
    }

    public static void verifyElementVisible(By by, String message) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        LogUtils.info("Verify " + by + " is displayed");
        Assert.assertTrue(DriverManager.getDriver().findElement(by).isDisplayed(), message);
    }

    public static boolean isElementDisplayed(By by) {
        try {
            LogUtils.info("Verify " + by + " is displayed");
            return DriverManager.getDriver().findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void sleep(double second) {
        if (second <= 0) {
            return;
        }

        long nanos = (long) (second * 1_000_000_000L);
        LockSupport.parkNanos(nanos);
    }

    public static void logConsole(Object message) {
        System.out.println(message);
    }

    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

    @Step("Verify Equals: {0} and {1}")
    public static void verifyEquals(Object actual, Object expected) {
        waitForPageLoaded();
        sleep(STEP_TIME);
        LogUtils.info("Verify equals: " + actual + " and " + expected);
       // ExtentTestManager.logMessage(Status.PASS, "Verify equals: " + actual + " and " + expected);
        Assert.assertEquals(actual, expected, "Fail. Not match. '" + actual.toString() + "' != '" + expected.toString() + "'");
    }

    @Step("Verify Equals: {0} and {1}")
    public static void verifyEquals(Object actual, Object expected, String message) {
        waitForPageLoaded();
        sleep(STEP_TIME);
        LogUtils.info("Verify equals: " + actual + " and " + expected);
        // ExtentTestManager.logMessage(Status.PASS, "Verify equals: " + actual + " and " + expected);
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Verify True: {0}")
    public static void verifyTrue(boolean condition){
        waitForPageLoaded();
        sleep(STEP_TIME);
        LogUtils.info("Verify true: " + condition);
        // ExtentTestManager.logMessage(Status.PASS, "Verify true: " + condition);
        Assert.assertTrue(condition, "Fail, Expected condition to be TRUE, but was FALSE");
    }

    @Step("Verify True: {0}")
    public static void verifyTrue(boolean condition, String message){
        waitForPageLoaded();
        sleep(STEP_TIME);
        LogUtils.info("Verify true: " + condition);
        // ExtentTestManager.logMessage(Status.PASS, "Verify true: " + condition);
        Assert.assertTrue(condition, message);
    }

    @Step("Verify False: {0}")
    public static void verifyFalse(boolean condition){
        waitForPageLoaded();
        sleep(STEP_TIME);
        LogUtils.info("Verify false: " + condition);
        // ExtentTestManager.logMessage(Status.PASS, "Verify false: " + condition);
        Assert.assertFalse(condition, "Fail, Expected condition to be FALSE, but was TRUE");
    }

    @Step("Verify False: {0}")
    public static void verifyFalse(boolean condition, String message){
        waitForPageLoaded();
        sleep(STEP_TIME);
        LogUtils.info("Verify false: " + condition);
        // ExtentTestManager.logMessage(Status.PASS, "Verify false: " + condition);
        Assert.assertFalse(condition, message);
    }

    @Step("Check element existing {0}")
    public static Boolean checkElementExist(By by) {
        waitForPageLoaded();
        waitForElementVisible(by);
        List<WebElement> listElement = getWebElements(by);

        if (!listElement.isEmpty()) {
            LogUtils.info("Check Element Exist: " + true + " --- " + by);
            return true;
        } else {
            LogUtils.info("Check Element Exist: " + false + " --- " + by);
            return false;
        }
    }

    @Step("Open URL: {0}")
    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        sleep(STEP_TIME);
        LogUtils.info("Open URL: " + url);
     //   ExtentTestManager.logMessage(Status.PASS, "Open URL: " + url);
        AllureManager.saveTextLog("Open URL: " + url);
        waitForPageLoaded();
        if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
            CaptureHelper.takeScreenshot("openURL_" + SystemHelper.makeSlug(url));
        }
    }

    @Step("Click element {0}")
    public static void clickElement(By by) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        try {
            getWebElement(by).click();
        } catch (ElementClickInterceptedException e) {
            LogUtils.warn("Click intercepted for " + by + ", fallback to JS click");
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", getWebElement(by));
        }
        LogUtils.info("Click element " + by);
      //  ExtentTestManager.logMessage(Status.PASS, "Click element " + by);

        if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
            CaptureHelper.takeScreenshot("clickElement_" + SystemHelper.makeSlug(by.toString()));
        }
    }

    @Step("Click element {0} with timeout {1}")
    public static void clickElement(By by, int timeout) {
        waitForPageLoaded();
        waitForElementVisible(by, timeout);
        sleep(STEP_TIME);
        try {
            getWebElement(by).click();
        } catch (ElementClickInterceptedException e) {
            LogUtils.warn("Click intercepted for " + by + ", fallback to JS click");
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", getWebElement(by));
        }
        LogUtils.info("Click element " + by);
     //   ExtentTestManager.logMessage(Status.PASS, "Click element " + by);

        if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
            CaptureHelper.takeScreenshot("clickElement_" + SystemHelper.makeSlug(by.toString()));
        }
    }

    @Step("Set text {1} on {0}")
    public static void setText(By by, String value) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(value);
        LogUtils.info("Set text: " + value + " on element " + by);
     //   ExtentTestManager.logMessage(Status.PASS, "Set text: " + value + " on element " + by);

        if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
            CaptureHelper.takeScreenshot("setText_" + SystemHelper.makeSlug(by.toString()));
        }
    }

    @Step("Set text {1} on {0} and press key {2}")
    public static void setTextAndKey(By by, String value, Keys key) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(value, key);
        LogUtils.info("Set text: " + value + " on element " + by);
      //  ExtentTestManager.logMessage(Status.PASS, "Set text: " + value + " on element " + by);

        if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
            CaptureHelper.takeScreenshot("setText_" + SystemHelper.makeSlug(by.toString()));
        }
    }

    @Step("Get text of element {0}")
    public static String getElementText(By by) {
        waitForPageLoaded();
        waitForElementVisible(by);
        sleep(STEP_TIME);
        String text = getWebElement(by).getText();
        LogUtils.info("Get text: " + text);
      //  ExtentTestManager.logMessage(Status.PASS, "Get text: " + text);
        return text;
    }

    // Alert handle
    @Step("Accept alert")
    public static void acceptAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.alertIsPresent());
            DriverManager.getDriver().switchTo().alert().accept();
            LogUtils.info("Alert accepted");
        } catch (Throwable error) {
            LogUtils.error("No alert present to accept.");
            Assert.fail("No alert present to accept.");
        }
    }

    //Wait for Element
    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
            LogUtils.info("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
            LogUtils.info("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    @Step("Scroll to element {0}")
    public static void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
    }

    @Step("Scroll to element {0}")
    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);

        if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
            CaptureHelper.takeScreenshot("scrollToElement_" + SystemHelper.makeSlug(element.getText()));
        }
    }

    @Step("Scroll to element {0} with type {1}")
    public static void scrollToElement(WebElement element, String type) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(" + type + ");", element);
    }

    @Step("Scroll to position with X={0}, Y={1}")
    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    @Step("Move to element {0}")
    public static boolean moveToElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).release(getWebElement(by)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    @Step("Successfully select: {1} on {0}")
    public static void selectCheckBox(By by, String value) {
        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));

        WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(DriverManager.getDriver().findElement(by)));

        if (value == null || value.equals(checkBox.getAttribute("value"))) {
            if (!checkBox.isSelected()) {
                checkBox.click();
            }
        }
    }

    @Step("Successfully select: {0}")
    public static void selectRadioButton(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));

        WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(by));

        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    @Step("Successfully select: {1} on {0}")
    public static void selectDropDown(By by, String value) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_TIMEOUT), Duration.ofMillis(500));
        WebElement dropDownElement = wait.until(ExpectedConditions.elementToBeClickable(by));

        Select select = new Select(dropDownElement);

        if (!select.getFirstSelectedOption().getText().equalsIgnoreCase(value)) {
            select.selectByVisibleText(value);
        }
    }

    public static boolean moveToOffset(int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    @Step("Highlight element {0}")
    public static WebElement highLightElement(By by) {

        if (DriverManager.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].style.border='3px solid red'", getWebElement(by));
            sleep(1);
        }
        return getWebElement(by);
    }

    @Step("Hover on element {0}")
    public static boolean hoverElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Mouse hover on element {0}")
    public static boolean mouseHover(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Drag element {0} to element {1}")
    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            //action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.clickAndHold(getWebElement(fromElement)).pause(1).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    @Step("Press ENTER on keyboard")
    public static boolean pressENTER() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Press ESC on keyboard")
    public static boolean pressESC() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Press F11 on keyboard")
    public static boolean pressF11() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for Page
     */
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) DriverManager.getDriver()).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LogUtils.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load (Javascript). (" + PAGE_LOAD_TIMEOUT + "s)");
            }
        }
    }

    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            assert driver != null;
            return ((Long) ((JavascriptExecutor) DriverManager.getDriver()).executeScript("return jQuery.active") == 0);
        };

        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

        //Wait JQuery until it is Ready!
        if (!jqueryReady) {
            LogUtils.info("JQuery is NOT Ready!");
            try {
                //Wait for jQuery to load
                wait.until(jQueryLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for JQuery load. (" + PAGE_LOAD_TIMEOUT + "s)");
            }
        }
    }

    //Wait for Angular Load

    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        final String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> {
            assert driver != null;
            return Boolean.valueOf(((JavascriptExecutor) DriverManager.getDriver()).executeScript(angularReadyScript).toString());
        };

        //Get Angular is Ready
        boolean angularReady = Boolean.parseBoolean(js.executeScript(angularReadyScript).toString());

        //Wait ANGULAR until it is Ready!
        if (!angularReady) {
            LogUtils.info("Angular is NOT Ready!");
            //Wait for Angular to load
            try {
                //Wait for jQuery to load
                wait.until(angularLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for Angular load. (" + PAGE_LOAD_TIMEOUT + "s)");
            }
        }

    }


}
