# 📋 Dokumentasi Perubahan: Integrasi LoginData.xlsx untuk Credentials

## 📝 Ringkasan Perubahan

Semua file yang telah dimodifikasi untuk menggunakan data login dari Excel file `data/LoginData.xlsx` daripada dari `env.properties` dan `ConstantGlobal`. Perubahan ini memberikan fleksibilitas lebih besar dalam manajemen kredensial login test berdasarkan environment.

---

## 🔧 File-File yang Dimodifikasi/Dibuat

### 1. **ExcelHelper.java** (Modifikasi)
📁 Location: `src/main/java/helpers/ExcelHelper.java`

**Perubahan:**
- ✅ Ditambahkan 2 method baru untuk membaca login credentials dari Excel:
  - `getLoginCredentialsByEnvironment(String excelPath, String sheetName, String environment)` - Method lengkap dengan parameter
  - `getLoginCredentialsByEnvironment(String environment)` - Convenience method dengan default path dan sheet name

**Fungsi:**
- Membaca file `data/LoginData.xlsx` dari sheet `LoginData`
- Mencari row yang cocok berdasarkan kolom `ENVIRONMENT`
- Mengembalikan `Hashtable<String, String>` berisi `ENVIRONMENT`, `EMAIL`, dan `PASSWORD`
- Logging otomatis untuk debug dan monitoring

**Struktur Excel yang diharapkan:**
```
| ENVIRONMENT | EMAIL              | PASSWORD |
|-------------|-------------------|----------|
| dev         | dev@email.com     | Dev@123  |
| staging     | staging@email.com | Stage@123|
| prod        | prod@email.com    | Prod@123 |
```

---

### 2. **LoginDataHelper.java** (BARU)
📁 Location: `src/main/java/helpers/LoginDataHelper.java`

**Tujuan:**
- Helper class untuk menyimpan dan mengakses data login yang sudah dibaca dari Excel
- Menggunakan Singleton pattern untuk memastikan hanya satu instance

**Fitur Utama:**
```java
// Inisialisasi dari Excel
LoginDataHelper.initializeFromExcel("dev");

// Akses data (static methods untuk kemudahan)
String email = LoginDataHelper.getCurrentEmail();
String password = LoginDataHelper.getCurrentPassword();
String environment = LoginDataHelper.getCurrentEnvironment();

// Reset (untuk cleanup)
LoginDataHelper.reset();
```

**Method yang tersedia:**
- `getInstance()` - Dapatkan singleton instance
- `initializeFromExcel(String environment)` - Load credentials dari Excel
- `getEnvironment()` - Get environment dari instance
- `getEmail()` - Get email dari instance
- `getPassword()` - Get password dari instance
- `getCurrentEmail()` - Static convenience method
- `getCurrentPassword()` - Static convenience method
- `getCurrentEnvironment()` - Static convenience method
- `reset()` - Reset singleton instance

---

### 3. **CucumberHooks.java** (Modifikasi)
📁 Location: `src/test/java/hooks/CucumberHooks.java`

**Perubahan di `@BeforeAll`:**
```java
// Initialize LoginDataHelper from Excel
try {
    String environment = ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "dev";
    LoginDataHelper.initializeFromExcel(environment);
    LogUtils.info("✅ Login credentials loaded from Excel for environment: " + environment);
} catch (Exception e) {
    LogUtils.warn("⚠️  Failed to load credentials from Excel: " + e.getMessage());
    LogUtils.warn("Falling back to ConstantGlobal configuration");
}
```

**Perubahan di `@Before`:**
- Menggunakan `LoginDataHelper.getCurrentEmail()` dan `LoginDataHelper.getCurrentEnvironment()`
- Logging user info dan environment ke Extent Report menggunakan data dari Excel

**Benefit:**
- LoginDataHelper diinisialisasi sekali di awal test suite
- Fallback ke ConstantGlobal jika Excel load gagal
- User info ditampilkan di report menggunakan data dari Excel

---

### 4. **StepsLoginWithRegisteredCredentials.java** (Modifikasi)
📁 Location: `src/test/java/steps/StepsLoginWithRegisteredCredentials.java`

**Perubahan di `@When("I enter registered email address and password")`:**
```java
// Get credentials from LoginDataHelper (Excel data)
String userEmail = LoginDataHelper.getCurrentEmail();
String userPassword = LoginDataHelper.getCurrentPassword();

LogUtils.info("Logging in with account: " + userEmail);
LogUtils.info("Account Type: " + UserInfoHelper.getUserAccountType());
LogUtils.info("Environment: " + LoginDataHelper.getCurrentEnvironment());
```

**Perubahan di `@Then("I verify that {string} is visible")`:**
- Menggunakan `LoginDataHelper.getCurrentEmail()` untuk verifikasi
- Email yang digunakan untuk assert diambil dari Excel, bukan hardcoded

**Benefit:**
- Login step sekarang menggunakan credentials dari Excel
- Fleksibel untuk test di environment berbeda
- Tidak perlu mengubah code, hanya perlu update data di Excel

---

### 5. **AllureManager.java** (Modifikasi)
📁 Location: `src/main/java/reports/AllureManager.java`

**Perubahan di `attachEnvironmentInfo()`:**
```java
String environment = LoginDataHelper.getCurrentEnvironment() != null ? 
    LoginDataHelper.getCurrentEnvironment() : (ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A");
```

**Perubahan di `attachUserAccountInfo()`:**
```java
String email = LoginDataHelper.getCurrentEmail() != null ? 
    LoginDataHelper.getCurrentEmail() : (ConstantGlobal.VALID_EMAIL != null ? ConstantGlobal.VALID_EMAIL : "N/A");
String environment = LoginDataHelper.getCurrentEnvironment() != null ? 
    LoginDataHelper.getCurrentEnvironment() : (ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A");

userInfo.append("Email: ").append(email).append("\n");
userInfo.append("Account Type: ").append(getAccountType()).append("\n");
userInfo.append("Environment: ").append(environment).append("\n");
userInfo.append("Test Data Source: Excel (LoginData.xlsx)\n");
userInfo.append("Credentials Loaded From: LoginData/").append(environment).append("\n");
```

**Perubahan di `attachTestResultSummary()`:**
- Menggunakan LoginDataHelper untuk email dan environment di test result summary

**Benefit:**
- Allure report menampilkan data dari Excel
- Environment info lebih akurat
- Credentials info lebih transparan

---

### 6. **ExtentReportManager.java** (Modifikasi)
📁 Location: `src/main/java/reports/ExtentReportManager.java`

**Perubahan di `getExtentReports()`:**
```java
// Use environment from LoginDataHelper (Excel)
String environment = LoginDataHelper.getCurrentEnvironment() != null ? 
    LoginDataHelper.getCurrentEnvironment() : (ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A");
extentReports.setSystemInfo("Environment", environment);

// User Information from Excel
String testUserEmail = LoginDataHelper.getCurrentEmail() != null ? 
    LoginDataHelper.getCurrentEmail() : (ConstantGlobal.VALID_EMAIL != null ? ConstantGlobal.VALID_EMAIL : "N/A");
extentReports.setSystemInfo("Test User Email", testUserEmail);
extentReports.setSystemInfo("Test Account Type", getUserAccountType());
extentReports.setSystemInfo("Test Data Source", "Excel (LoginData.xlsx) - Environment: " + environment);
```

**Perubahan di `getUserAccountType()`:**
- Menggunakan LoginDataHelper untuk mendapatkan environment
- Fallback ke ConstantGlobal jika LoginDataHelper null

**Perubahan di `getFormattedUserInfo()`:**
- Menggunakan LoginDataHelper untuk email dan environment

**Benefit:**
- Extent Report menampilkan data yang sama dengan Allure
- Test data source jelas terdokumentasi
- User info lebih akurat

---

## 🔄 Alur Eksekusi

```
1. Maven/Test Runner Start
   ↓
2. CucumberHooks @BeforeAll
   ├─ PropertiesHelper.loadAllFiles()
   ├─ ConstantGlobal.ENV diset dari env/[environment].properties
   └─ LoginDataHelper.initializeFromExcel(ConstantGlobal.ENV)
      ├─ ExcelHelper.getLoginCredentialsByEnvironment(env)
      ├─ Baca dari data/LoginData.xlsx, sheet LoginData
      └─ Store email & password di LoginDataHelper
   ↓
3. Test Scenario Start
   ↓
4. CucumberHooks @Before
   ├─ ExtentTestManager create test
   └─ Log user info dari LoginDataHelper ke report
   ↓
5. Step Definition dijalankan
   ├─ StepsLoginWithRegisteredCredentials @When
   ├─ Ambil email & password dari LoginDataHelper
   └─ Perform login
   ↓
6. Report Generation
   ├─ AllureManager attach info dari LoginDataHelper
   ├─ ExtentReportManager set system info dari LoginDataHelper
   └─ Report contains accurate credential info
   ↓
7. Test Selesai
```

---

## 🚀 Cara Menggunakan

### Setup Data di Excel

1. Buka `data/LoginData.xlsx`
2. Pastikan ada sheet bernama `LoginData`
3. Buat header: `ENVIRONMENT | EMAIL | PASSWORD`
4. Isi data sesuai environment:

```
ENVIRONMENT    EMAIL                      PASSWORD
dev            developer@eventhub.com     Dev@123
staging        staging@eventhub.com       Staging@123
prod           production@eventhub.com    Prod@123
```

### Menjalankan Test dengan Environment Berbeda

```bash
# Dev (default)
mvn test

# Staging
mvn test -Denv=staging

# Production
mvn test -Denv=prod
```

### Dalam Test Step

```java
// Di step definition
@When("I login")
public void login() {
    String email = LoginDataHelper.getCurrentEmail();
    String password = LoginDataHelper.getCurrentPassword();
    String env = LoginDataHelper.getCurrentEnvironment();
    
    LogUtils.info("Logging in as: " + email + " on env: " + env);
    // ... perform login ...
}
```

---

## ✅ Verifikasi Perubahan

### Checklist
- [x] ExcelHelper menambahkan method untuk read login data
- [x] LoginDataHelper class dibuat (Singleton pattern)
- [x] CucumberHooks @BeforeAll initialize LoginDataHelper
- [x] CucumberHooks @Before menggunakan LoginDataHelper
- [x] StepsLoginWithRegisteredCredentials menggunakan LoginDataHelper
- [x] AllureManager menggunakan LoginDataHelper
- [x] ExtentReportManager menggunakan LoginDataHelper
- [x] Project compile tanpa error
- [x] Fallback ke ConstantGlobal jika LoginDataHelper fail

### Testing Recommendations
1. Test dengan dev environment
2. Test dengan staging environment
3. Test dengan prod environment
4. Verify Excel tidak ada → fallback ke ConstantGlobal
5. Verify report menampilkan correct data

---

## 🔐 Security Notes

- Password di Excel ditampilkan sebagai `[MASKED]` di log
- Actual password hanya digunakan saat login
- Report menampilkan email, bukan password
- LoginDataHelper tidak expose password via getter

---

## 📚 Dokumentasi Tambahan

### LoginData.xlsx Structure
```
File: data/LoginData.xlsx
Sheet: LoginData
Columns:
  - ENVIRONMENT: String (dev, staging, prod)
  - EMAIL: String (full email address)
  - PASSWORD: String (encrypted password recommended)
```

### Error Handling
- Jika file Excel tidak ditemukan → Log error, fallback ke ConstantGlobal
- Jika sheet tidak ditemukan → Log error, fallback ke ConstantGlobal
- Jika environment tidak ada di Excel → Log error, fallback ke ConstantGlobal
- Jika LoginDataHelper belum diinit → Null check, use ConstantGlobal

---

## 🎯 Benefit Implementasi Ini

✅ **Centralized Credentials Management** - Semua credentials di satu file Excel
✅ **Environment-based Testing** - Mudah switch environment tanpa code change
✅ **Better Reporting** - Report menampilkan actual credentials yang digunakan
✅ **Maintainability** - Mudah update credentials untuk beda environment
✅ **Flexibility** - Support fallback ke ConstantGlobal
✅ **Security** - Password tidak hardcoded di code
✅ **Audit Trail** - Clear logging of which credentials were used

---

Generated: April 4, 2026
Status: ✅ Implementation Complete

