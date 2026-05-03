# 🚀 Quick Start Guide - LoginData.xlsx Integration

## 📦 File-File yang Diubah

### 1. **ExcelHelper.java**
```java
// Cara menggunakan:
ExcelHelper helper = new ExcelHelper();
Hashtable<String, String> creds = helper.getLoginCredentialsByEnvironment("dev");

// Result:
// {ENVIRONMENT=dev, EMAIL=dev@email.com, PASSWORD=Dev@123}
```

### 2. **LoginDataHelper.java** (NEW)
```java
// Initialize di @BeforeAll (sudah otomatis di CucumberHooks)
LoginDataHelper.initializeFromExcel("dev");

// Get credentials di step definition
String email = LoginDataHelper.getCurrentEmail();
String password = LoginDataHelper.getCurrentPassword();
String env = LoginDataHelper.getCurrentEnvironment();
```

### 3. **CucumberHooks.java**
```java
@BeforeAll
public static void beforeAll() {
    PropertiesHelper.loadAllFiles();
    // ✅ NEW: Load credentials from Excel
    LoginDataHelper.initializeFromExcel(ConstantGlobal.ENV);
}

@Before
public void beforeScenario(Scenario scenario) {
    // ✅ Uses LoginDataHelper for logging
    String userEmail = LoginDataHelper.getCurrentEmail();
    ExtentTestManager.getTest().info("Test User: " + userEmail);
}
```

### 4. **StepsLoginWithRegisteredCredentials.java**
```java
@When("I enter registered email address and password")
public void iEnterCorrectEmailAddressAndPassword() {
    // ✅ Uses LoginDataHelper instead of ConstantGlobal
    String userEmail = LoginDataHelper.getCurrentEmail();
    String userPassword = LoginDataHelper.getCurrentPassword();
    
    CredentialsDataObject credentials = CredentialsDataObject.builder()
            .userEmail(userEmail)
            .userPassword(userPassword)
            .build();
    
    loginPage.loginAccount(credentials);
}
```

### 5. **AllureManager.java**
```java
// ✅ attachEnvironmentInfo() uses LoginDataHelper
// ✅ attachUserAccountInfo() uses LoginDataHelper  
// ✅ attachTestResultSummary() uses LoginDataHelper
```

### 6. **ExtentReportManager.java**
```java
// ✅ getExtentReports() sets system info from LoginDataHelper
// ✅ getUserAccountType() uses LoginDataHelper for environment
```

---

## 📊 Excel Data Format

### File: `data/LoginData.xlsx`
### Sheet: `LoginData`

| ENVIRONMENT | EMAIL                    | PASSWORD    |
|-------------|--------------------------|-------------|
| dev         | dev@eventhub.example.com | Dev@12345   |
| staging     | qa@eventhub.example.com  | QA@12345    |
| prod        | prod@eventhub.example.com| Prod@12345  |

**Penting:** 
- Header harus: `ENVIRONMENT`, `EMAIL`, `PASSWORD` (case-insensitive)
- Environment value harus lowercase: `dev`, `staging`, `prod`
- Email dan Password tidak boleh kosong

---

## 🎯 Cara Menggunakan

### Step 1: Siapkan Data di Excel
```bash
# Buka data/LoginData.xlsx
# Pastikan sheet "LoginData" ada dengan kolom:
# ENVIRONMENT | EMAIL | PASSWORD
```

### Step 2: Jalankan Test dengan Environment
```bash
# Development (default)
mvn clean test

# Staging
mvn clean test -Denv=staging

# Production  
mvn clean test -Denv=prod
```

### Step 3: Verify Report
```
✅ Extent Report menampilkan credentials dari Excel
✅ Allure Report menampilkan credentials dari Excel
✅ Login step menggunakan data dari Excel
```

---

## 🔍 Debugging

### Lihat log untuk verify credentials loaded:
```
✅ Login credentials found for environment: dev
   Email: dev@eventhub.example.com
   Password: [masked]
```

### Jika credentials tidak ditemukan:
```
❌ No credentials found for environment: dev
```

### Fallback ke ConstantGlobal:
Jika Excel file tidak ditemukan atau environment tidak ada, system akan fallback ke `ConstantGlobal.VALID_EMAIL` dan `ConstantGlobal.VALID_PASSWORD`

---

## 💡 Contoh di Test Step

```java
@And("I verify user {string} is logged in")
public void verifyUserLoggedIn(String expectedUser) {
    // ✅ Get actual user from Excel data
    String actualUser = LoginDataHelper.getCurrentEmail();
    
    // Verify
    WebUI.verifyEquals(actualUser, expectedUser, "User tidak sesuai");
}

@When("I perform login for {string} environment")
public void loginForEnvironment(String environment) {
    // Note: environment sudah di-set via maven profile
    // Credentials otomatis loaded di BeforeAll
    loginPage.login(
        LoginDataHelper.getCurrentEmail(),
        LoginDataHelper.getCurrentPassword()
    );
}
```

---

## 📋 Checklist Setup

- [ ] File `data/LoginData.xlsx` sudah ada
- [ ] Sheet `LoginData` terbuat dengan header: ENVIRONMENT, EMAIL, PASSWORD
- [ ] Data untuk minimal 1 environment sudah diisi (dev)
- [ ] Maven profile di pom.xml sudah ada (dev, staging, prod)
- [ ] Kode sudah di-compile: `mvn clean compile`
- [ ] Test berjalan: `mvn clean test`
- [ ] Extent Report menampilkan email dari Excel ✅
- [ ] Allure Report menampilkan email dari Excel ✅

---

## 🔐 Security Best Practices

✅ **DO:**
- Simpan Excel file di `.gitignore` jika berisi sensitive data
- Gunakan encrypted passwords di production
- Limit access ke file Excel

❌ **DON'T:**
- Commit credentials ke git repository
- Hardcode passwords di test code
- Share Excel file dengan semua orang

---

## 🆘 Troubleshooting

| Issue | Solution |
|-------|----------|
| `❌ Excel file not found` | Check path `data/LoginData.xlsx` exists |
| `❌ Sheet not found` | Verify sheet name is exactly `LoginData` |
| `❌ Required columns not found` | Check header: ENVIRONMENT, EMAIL, PASSWORD |
| `❌ No credentials found for dev` | Add row with ENVIRONMENT=dev |
| `Login fails but credentials look correct` | Verify password exact match di Excel |
| `Report shows ConstantGlobal email instead` | Check LoginDataHelper initialized in @BeforeAll |

---

## 📞 Support Information

### Files Related:
- `src/main/java/helpers/ExcelHelper.java` - Excel reading logic
- `src/main/java/helpers/LoginDataHelper.java` - Credentials management
- `src/test/java/hooks/CucumberHooks.java` - Initialization
- `src/test/java/steps/StepsLoginWithRegisteredCredentials.java` - Step definition
- `src/main/java/reports/AllureManager.java` - Allure reporting
- `src/main/java/reports/ExtentReportManager.java` - Extent reporting
- `data/LoginData.xlsx` - Credentials data

### Key Methods:
```java
// Load credentials (called in @BeforeAll automatically)
LoginDataHelper.initializeFromExcel(String environment);

// Get credentials
LoginDataHelper.getCurrentEmail();
LoginDataHelper.getCurrentPassword();
LoginDataHelper.getCurrentEnvironment();

// Read from Excel directly
ExcelHelper helper = new ExcelHelper();
Hashtable<String, String> creds = helper.getLoginCredentialsByEnvironment(environment);
```

---

## ✅ Validation Checklist

Run this to verify everything is working:

```bash
# 1. Compile project
mvn clean compile

# 2. Check if Excel file has correct structure
# Manually verify: data/LoginData.xlsx

# 3. Run a single scenario
mvn clean test -Denv=dev

# 4. Check logs for:
# "✅ Login credentials found for environment: dev"

# 5. Verify report contains:
# - Email from Excel (not ConstantGlobal)
# - Test Data Source: Excel (LoginData.xlsx)
# - Environment: dev
```

---

**Last Updated:** April 4, 2026
**Status:** ✅ Ready for Use

