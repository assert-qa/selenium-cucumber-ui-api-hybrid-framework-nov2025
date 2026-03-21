# Selenium — Page Object Model

## Base Page Class

```java
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void type(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
```

## Concrete Page Object

```java
public class LoginPage extends BasePage {
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage navigate() {
        driver.get("https://example.com/login");
        waitForVisible(usernameField);
        return this;
    }

    public DashboardPage loginAs(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        return new DashboardPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
```

## Page Factory Pattern

```java
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "username") private WebElement usernameField;
    @FindBy(id = "password") private WebElement passwordField;
    @FindBy(css = "button[type='submit']") private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String user, String pass) {
        usernameField.sendKeys(user);
        passwordField.sendKeys(pass);
        loginButton.click();
    }
}
```

---

# Design Patterns for Scalable, Maintainable, and Usable Automation Frameworks

## 1. Page Object Model (POM)
**Deskripsi:** Memisahkan representasi halaman aplikasi dari test logic. Setiap halaman diwakili oleh satu class.
**Kapan digunakan:** Selalu gunakan untuk aplikasi web dengan banyak halaman/interaksi. Membuat test lebih mudah dirawat dan scalable.

```java
// Contoh POM
public class LoginPage {
    private WebDriver driver;
    private By username = By.id("username");
    private By password = By.id("password");
    private By loginBtn = By.id("login");
    public LoginPage(WebDriver driver) { this.driver = driver; }
    public void login(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }
}
```

## 2. Page Factory
**Deskripsi:** Ekstensi dari POM, menggunakan anotasi `@FindBy` untuk inisialisasi elemen secara otomatis.
**Kapan digunakan:** Untuk framework yang ingin mengurangi boilerplate dan meningkatkan kecepatan inisialisasi elemen.

```java
public class LoginPage {
    @FindBy(id = "username") WebElement username;
    @FindBy(id = "password") WebElement password;
    @FindBy(id = "login") WebElement loginBtn;
    public LoginPage(WebDriver driver) { PageFactory.initElements(driver, this); }
    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginBtn.click();
    }
}
```

## 3. Singleton WebDriver
**Deskripsi:** Memastikan hanya ada satu instance WebDriver aktif selama eksekusi test.
**Kapan digunakan:** Untuk menghindari resource leak dan konflik session, terutama pada parallel execution.

```java
public class DriverManager {
    private static WebDriver driver;
    private DriverManager() {}
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
```

## 4. Factory Pattern (Driver/Browser/Environment)
**Deskripsi:** Mengelola pembuatan objek (WebDriver, Page, Data, dsb) secara terpusat dan fleksibel.
**Kapan digunakan:** Jika framework mendukung multi-browser, multi-environment, atau ingin mudah di-extend.

```java
public class WebDriverFactory {
    public static WebDriver create(String browser) {
        switch (browser) {
            case "firefox": return new FirefoxDriver();
            case "edge": return new EdgeDriver();
            default: return new ChromeDriver();
        }
    }
}
```

## 5. Strategy Pattern (Test Data/Execution)
**Deskripsi:** Memungkinkan pemilihan strategi eksekusi/test data secara dinamis (misal: data dari file, DB, API).
**Kapan digunakan:** Untuk test data management yang fleksibel dan reusable.

```java
// Interface
public interface DataProviderStrategy {
    List<String[]> getData();
}
// Implementasi
public class CsvDataProvider implements DataProviderStrategy {
    public List<String[]> getData() { /* baca dari CSV */ return ...; }
}
public class DbDataProvider implements DataProviderStrategy {
    public List<String[]> getData() { /* baca dari DB */ return ...; }
}
// Penggunaan
dataProvider = new CsvDataProvider();
```

## 6. Command Pattern (Test Steps/Actions)
**Deskripsi:** Mengenkapsulasi aksi test sebagai objek, sehingga bisa diatur, diulang, atau dikombinasikan.
**Kapan digunakan:** Untuk test yang kompleks, modular, dan ingin mendukung reusability step.

```java
public interface Command { void execute(); }
public class ClickCommand implements Command {
    private WebElement el;
    public ClickCommand(WebElement el) { this.el = el; }
    public void execute() { el.click(); }
}
// Penggunaan
Command clickLogin = new ClickCommand(driver.findElement(By.id("login")));
clickLogin.execute();
```

## 7. Observer Pattern (Event Listener/Reporting)
**Deskripsi:** Menghubungkan event (misal: test start, fail, pass) ke listener (logging, reporting, screenshot).
**Kapan digunakan:** Untuk integrasi reporting, logging, atau custom hooks tanpa mengubah test utama.

```java
public interface TestListener {
    void onTestStart();
    void onTestSuccess();
    void onTestFailure();
}
public class LoggerListener implements TestListener {
    public void onTestStart() { System.out.println("Test started"); }
    public void onTestSuccess() { System.out.println("Test passed"); }
    public void onTestFailure() { System.out.println("Test failed"); }
}
// Integrasi di framework test
```

## 8. Builder Pattern (Test Data/Request)
**Deskripsi:** Memudahkan pembuatan objek kompleks (misal: test data, API request) dengan chaining method.
**Kapan digunakan:** Untuk test data atau request yang banyak opsional/variasi field.

```java
public class UserBuilder {
    private String name, email;
    public UserBuilder setName(String name) { this.name = name; return this; }
    public UserBuilder setEmail(String email) { this.email = email; return this; }
    public User build() { return new User(name, email); }
}
// Penggunaan
User user = new UserBuilder().setName("Budi").setEmail("budi@mail.com").build();
```

## 9. Service Object Pattern
**Deskripsi:** Memisahkan logika interaksi dengan API/service dari test dan page object.
**Kapan digunakan:** Untuk framework yang menguji aplikasi dengan kombinasi UI dan API.

```java
public class UserService {
    public User getUserById(int id) {
        // Panggil API, parsing response
        return ...;
    }
}
// Penggunaan di test
testUser = userService.getUserById(1);
```

## 10. Data-Driven & Parameterized Test
**Deskripsi:** Memisahkan data test dari script, mendukung eksekusi test dengan banyak variasi data.
**Kapan digunakan:** Untuk test validasi field, boundary, atau kombinasi input yang banyak.

```java
// TestNG
@DataProvider(name = "loginData")
public Object[][] loginData() {
    return new Object[][] { {"user1", "pass1"}, {"user2", "pass2"} };
}
@Test(dataProvider = "loginData")
public void testLogin(String user, String pass) {
    loginPage.login(user, pass);
}
```

## 11. Dependency Injection (DI)
**Deskripsi:** Mengelola dependency (WebDriver, config, helper) secara otomatis, meningkatkan modularitas.
**Kapan digunakan:** Untuk framework besar, scalable, dan ingin mengurangi coupling antar komponen.

```java
// Menggunakan Google Guice
public class TestModule extends AbstractModule {
    @Override protected void configure() {
        bind(WebDriver.class).to(ChromeDriver.class);
    }
}
@Inject WebDriver driver;
```

## 12. Test Decorator Pattern
**Deskripsi:** Menambah behavior pada test (misal: retry, logging, timing) tanpa mengubah test utama.
**Kapan digunakan:** Untuk kebutuhan retry otomatis, custom logging, atau pengukuran waktu eksekusi.

```java
public class RetryTest implements IRetryAnalyzer {
    private int count = 0, maxTry = 3;
    public boolean retry(ITestResult result) {
        if (count < maxTry) { count++; return true; }
        return false;
    }
}
// Penggunaan di TestNG: @Test(retryAnalyzer = RetryTest.class)
```

---

# Rekomendasi Penggunaan
- Gunakan kombinasi POM, Factory, dan Data-Driven untuk fondasi framework yang scalable dan maintainable.
- Tambahkan Observer/Decorator untuk kebutuhan reporting dan reliability.
- Gunakan DI dan Builder jika framework sudah kompleks dan butuh modularitas tinggi.
- Selalu pisahkan test logic, data, dan konfigurasi untuk usability dan maintainability.

---

# Referensi
- [Selenium Design Patterns and Best Practices](https://www.selenium.dev/documentation/test_practices/encouraged/design_patterns/)
- [Martin Fowler: Patterns of Enterprise Application Architecture](https://martinfowler.com/eaaCatalog/)
- [Test Automation University: Design Patterns](https://testautomationu.applitools.com/design-patterns-java/)
