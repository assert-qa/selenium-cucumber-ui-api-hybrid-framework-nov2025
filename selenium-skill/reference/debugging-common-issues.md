# Selenium — Debugging Common Issues

## StaleElementReferenceException

**Cause**: DOM re-rendered after element was found.

```java
// ❌ Bad
WebElement el = driver.findElement(By.id("btn"));
// ... page reloads or AJAX updates ...
el.click(); // StaleElementReferenceException

// ✅ Good — Re-locate element
wait.until(ExpectedConditions.elementToBeClickable(By.id("btn"))).click();
```

## NoSuchElementException

**Cause**: Element not in DOM or not visible yet.

```java
// ❌ Bad
driver.findElement(By.id("dynamic-element")).click();

// ✅ Good — Wait for it
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dynamic-element"))).click();
```

## ElementClickInterceptedException

**Cause**: Another element covers the target.

```java
// ✅ Use JavaScript click
WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn")));
((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);

// ✅ Or scroll into view first
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
el.click();
```

## Timeouts

```java
// Page load timeout
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

// Script timeout (for async JS)
driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

// Custom wait with polling
new WebDriverWait(driver, Duration.ofSeconds(15))
    .pollingEvery(Duration.ofMillis(500))
    .ignoring(NoSuchElementException.class)
    .until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
```

## Iframe Handling

```java
// Switch to iframe
driver.switchTo().frame("iframeName");
// or by index
driver.switchTo().frame(0);
// or by WebElement
driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.content")));
// Switch back to main
driver.switchTo().defaultContent();
```

## Alert Handling

```java
wait.until(ExpectedConditions.alertIsPresent());
Alert alert = driver.switchTo().alert();
String alertText = alert.getText();
alert.accept(); // or alert.dismiss();
```

## File Upload

```java
WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
upload.sendKeys("/path/to/file.pdf");
```

## Screenshots on Failure

```java
@AfterEach
void tearDown(TestInfo testInfo) {
    if (driver != null) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(),
                Path.of("screenshots/" + testInfo.getDisplayName() + ".png"));
        } catch (Exception e) { /* ignore */ }
        driver.quit();
    }
}
```

## Common Cloud Issues

### 1. Authentication Errors (Cloud Grid)
- **Symptom**: `401 Unauthorized`, `Invalid credentials`, or session not created.
- **Solution**: 
  - Double-check your username/access key (environment variables or config).
  - Regenerate access key if needed from your cloud provider dashboard.
  - Ensure credentials are not expired or revoked.

### 2. Capabilities Mismatch
- **Symptom**: `SessionNotCreatedException`, `Invalid capabilities`, or browser/device not found.
- **Solution**:
  - Verify browserName, browserVersion, platform, and deviceName values.
  - Use the provider's device/browser catalog for valid options.
  - For mobile, ensure `isRealMobile` and device capabilities are correct.

### 3. Network/Firewall Issues
- **Symptom**: Test cannot connect to cloud grid, timeouts, or tunnel errors.
- **Solution**:
  - Check your internet connection and firewall/proxy settings.
  - For localhost testing, ensure tunnel/connector is running and configured.
  - Use the correct tunnel name if using multiple tunnels.

### 4. Test Flakiness (Cloud)
- **Symptom**: Tests pass locally but fail on cloud, especially for timing/UI issues.
- **Solution**:
  - Add explicit waits for dynamic elements.
  - Avoid hardcoded sleeps; use WebDriverWait.
  - Use screenshots and video logs from the cloud dashboard for debugging.

### 5. Session Not Closed Properly
- **Symptom**: Orphaned sessions, resource leaks, or max session limit reached.
- **Solution**:
  - Always call `driver.quit()` in a `finally` or teardown block.
  - Use test status reporting (see cloud integration docs) to mark test as passed/failed.

---

## Debugging Tips
- Use browser console logs and network logs (enable via capabilities if supported).
- Download video and screenshots from the cloud dashboard for failed tests.
- Check cloud provider status page for outages if all tests suddenly fail.
- For element issues, use cloud grid's live view or session replay to inspect DOM state.

---

## Useful Links
- [Selenium Docs](https://www.selenium.dev/documentation/)
- [LambdaTest Troubleshooting](https://www.lambdatest.com/support/docs/selenium-troubleshooting/)
- [BrowserStack Troubleshooting](https://www.browserstack.com/docs/automate/selenium/troubleshooting)
- [Sauce Labs Troubleshooting](https://docs.saucelabs.com/dev/cli/saucectl/troubleshooting/)