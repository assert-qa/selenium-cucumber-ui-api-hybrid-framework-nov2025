#!/bin/bash
# Scaffold a Selenium + Java project with Maven
set -e

PROJECT_NAME="${1:-selenium-project}"
CLOUD="${2:-}"

if [ -d "$PROJECT_NAME" ]; then
    echo "Error: Directory '$PROJECT_NAME' already exists"
    exit 2
fi

command -v mvn >/dev/null 2>&1 || { echo "Error: Maven (mvn) not found"; exit 1; }

echo "Creating Selenium project: $PROJECT_NAME"
mkdir -p "$PROJECT_NAME/src/main/java/pages"
mkdir -p "$PROJECT_NAME/src/test/java/tests"
mkdir -p "$PROJECT_NAME/src/test/resources"

cat > "$PROJECT_NAME/pom.xml" << 'POMEOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>selenium-tests</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <selenium.version>4.27.0</selenium.version>
        <junit.jupiter.version>5.11.0</junit.jupiter.version>
        <webdrivermanager.version>5.7.0</webdrivermanager.version>
        <allure.version>2.25.0</allure.version>
        <faker.version>1.0.2</faker.version>
        <poi.version>5.2.5</poi.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>${webdrivermanager.version}</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-junit5</artifactId>
            <version>${allure.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.github.javafaker</groupId>
            <artifactId>javafaker</artifactId>
            <version>${faker.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>${poi.version}</version>
        </dependency>
    </dependencies>
</project>
POMEOF

# Add .gitignore
cat > "$PROJECT_NAME/.gitignore" << 'EOF'
target/
.idea/
*.iml
*.log
*.tmp
.DS_Store
EOF

# Add config.properties
cat > "$PROJECT_NAME/src/test/resources/config.properties" << 'EOF'
base.url=https://example.com
browser=chrome
timeout.explicit=10
headless=false
EOF

# Add BaseTest.java
cat > "$PROJECT_NAME/src/test/java/tests/BaseTest.java" << 'EOF'
package tests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
public class BaseTest {
    protected WebDriver driver;
    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
EOF

# Add BasePage.java
cat > "$PROJECT_NAME/src/main/java/pages/BasePage.java" << 'EOF'
package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
EOF

# Add sample LoginPage.java
cat > "$PROJECT_NAME/src/main/java/pages/LoginPage.java" << 'EOF'
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class LoginPage extends BasePage {
    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login");
    public LoginPage(WebDriver driver) { super(driver); }
    public void login(String user, String pass) {
        find(username).sendKeys(user);
        find(password).sendKeys(pass);
        find(loginBtn).click();
    }
}
EOF

# Add sample LoginTest.java
cat > "$PROJECT_NAME/src/test/java/tests/LoginTest.java" << 'EOF'
package tests;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
public class LoginTest extends BaseTest {
    @Test
    void testLogin() {
        driver.get("https://example.com/login");
        new LoginPage(driver).login("user", "pass");
        // Add assertion here
    }
}
EOF

# Add cloud dependencies if requested
if [ "$CLOUD" = "lambdatest" ]; then
    sed -i '/<\/dependencies>/i \\t<dependency>\n\t\t<groupId>com.lambdatest</groupId>\n\t\t<artifactId>lambdatest-java</artifactId>\n\t\t<version>1.0.0</version>\n\t<\/dependency>' "$PROJECT_NAME/pom.xml"
fi
if [ "$CLOUD" = "browserstack" ]; then
    sed -i '/<\/dependencies>/i \\t<dependency>\n\t\t<groupId>com.browserstack</groupId>\n\t\t<artifactId>browserstack-local-java</artifactId>\n\t\t<version>1.0.6</version>\n\t<\/dependency>' "$PROJECT_NAME/pom.xml"
fi

# Output instructions
cat <<EOM
✅ Project '$PROJECT_NAME' created
   cd $PROJECT_NAME
   mvn test
   # For Allure report:
   mvn allure:report
EOM
exit 0
