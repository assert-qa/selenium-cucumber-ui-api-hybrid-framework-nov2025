# 📊 LoginData.xlsx - Data Structure & Examples

## Overview

File `data/LoginData.xlsx` adalah sumber tunggal untuk semua kredensial login test. File ini menggantikan hardcoded credentials di `env.properties` dan `ConstantGlobal.java`.

---

## 📋 Sheet Structure

### Sheet Name: `LoginData`

| Column | Type | Description | Example |
|--------|------|-------------|---------|
| ENVIRONMENT | Text | Environment identifier | dev, staging, prod |
| EMAIL | Text | Login email address | user@domain.com |
| PASSWORD | Text | Login password | SecurePass@123 |

---

## 📝 Data Examples

### Basic Setup (3 environments)

| ENVIRONMENT | EMAIL | PASSWORD |
|---|---|---|
| dev | dev@eventhub.com | Dev@123 |
| staging | qa@eventhub.com | QA@123 |
| prod | prod@eventhub.com | Prod@123 |

### Extended Setup (multiple users per environment)

| ENVIRONMENT | EMAIL | PASSWORD |
|---|---|---|
| dev | admin-dev@eventhub.com | AdminDev@123 |
| dev | user-dev@eventhub.com | UserDev@123 |
| staging | admin-qa@eventhub.com | AdminQA@123 |
| staging | user-qa@eventhub.com | UserQA@123 |
| prod | admin-prod@eventhub.com | AdminProd@123 |
| prod | user-prod@eventhub.com | UserProd@123 |

### With Different Account Types

| ENVIRONMENT | EMAIL | PASSWORD |
|---|---|---|
| dev | regular-user@eventhub.com | RegularDev@123 |
| dev | premium-user@eventhub.com | PremiumDev@123 |
| dev | vip-user@eventhub.com | VIPDev@123 |
| staging | regular-user@eventhub.com | RegularQA@123 |
| staging | premium-user@eventhub.com | PremiumQA@123 |

---

## 🔧 Setup Instructions

### 1. Create/Open Excel File

```bash
# File location: data/LoginData.xlsx
# Ensure file exists in project root under 'data' folder
```

### 2. Add Sheet (if not exists)

- Right-click on sheet tab
- Select "Insert Sheet"
- Name it: `LoginData`

### 3. Add Headers (Row 1)

```
A1: ENVIRONMENT
B1: EMAIL
C1: PASSWORD
```

### 4. Add Data (Starting Row 2)

| Row | A | B | C |
|-----|---|---|---|
| 2 | dev | dev@email.com | Dev@123 |
| 3 | staging | qa@email.com | QA@123 |
| 4 | prod | prod@email.com | Prod@123 |

### 5. Save File

- Save as `LoginData.xlsx` (Excel format)
- Place in `data/` folder at project root

---

## 🎯 Usage Examples

### Example 1: Dev Environment Only

**Goal:** Setup minimal test data for development

```
ENVIRONMENT | EMAIL | PASSWORD
dev | tester@test.com | Test@123
```

**Command to run:**
```bash
mvn clean test                    # Uses dev by default
mvn clean test -Denv=dev         # Explicit dev
```

---

### Example 2: Multi-Environment Testing

**Goal:** Same test on dev, staging, and prod

```
ENVIRONMENT | EMAIL | PASSWORD
dev | dev-user@site.com | Dev@123
staging | qa-user@site.com | QA@123
prod | prod-user@site.com | Prod@123
```

**Commands to run:**
```bash
mvn clean test -Denv=dev
mvn clean test -Denv=staging
mvn clean test -Denv=prod
```

---

### Example 3: Multiple Test Scenarios per Environment

**Goal:** Test different user types (admin, regular user)

```
ENVIRONMENT | EMAIL | PASSWORD
dev | admin@test.com | AdminPass@123
dev | user@test.com | UserPass@123
staging | admin@qa.com | AdminQA@123
staging | user@qa.com | UserQA@123
```

**Note:** Dengan setup ini, hanya 1 user yang dipilih per environment run.
- Jika ada banyak entries untuk 1 environment, yang pertama akan digunakan
- Untuk scenario berbeda, buat data terpisah atau gunakan Cucumber Scenario Outline

---

## 🔄 How It Works

### Code Flow

```
1. Test Execution Start
   ↓
2. @BeforeAll in CucumberHooks
   ├─ PropertiesHelper.loadAllFiles()
   ├─ Read env property (dev, staging, prod)
   └─ LoginDataHelper.initializeFromExcel(env)
   ↓
3. LoginDataHelper.initializeFromExcel()
   ├─ ExcelHelper.getLoginCredentialsByEnvironment(env)
   ├─ Open data/LoginData.xlsx
   ├─ Find matching ENVIRONMENT row
   └─ Store EMAIL & PASSWORD in memory
   ↓
4. Test Step Execution
   ├─ StepsLoginWithRegisteredCredentials
   ├─ Get email: LoginDataHelper.getCurrentEmail()
   ├─ Get password: LoginDataHelper.getCurrentPassword()
   └─ Perform login with these credentials
   ↓
5. Report Generation
   ├─ Extent Report shows email from Excel
   ├─ Allure Report shows email from Excel
   └─ Both show "Test Data Source: Excel (LoginData.xlsx)"
```

---

## ✅ Validation

### Check if Excel is Properly Set Up

```java
// Add this temporary test to verify
@Test
public void verifyExcelSetup() {
    ExcelHelper helper = new ExcelHelper();
    
    // Try to read dev credentials
    Hashtable<String, String> creds = helper.getLoginCredentialsByEnvironment("dev");
    
    // Verify
    assertNotNull(creds, "Credentials should not be null");
    assertNotNull(creds.get("EMAIL"), "Email should not be null");
    assertNotNull(creds.get("PASSWORD"), "Password should not be null");
    
    System.out.println("✅ Excel setup is correct!");
    System.out.println("   Environment: " + creds.get("ENVIRONMENT"));
    System.out.println("   Email: " + creds.get("EMAIL"));
}
```

---

## 🚨 Common Issues & Fixes

### Issue 1: "❌ Excel file not found: data/LoginData.xlsx"

**Solution:**
- Check if file exists: `data/LoginData.xlsx`
- Check working directory (usually project root)
- Ensure path is correct in project structure

### Issue 2: "❌ Sheet not found: LoginData"

**Solution:**
- Open `data/LoginData.xlsx`
- Verify sheet tab name is exactly `LoginData` (case-sensitive)
- Create sheet if it doesn't exist

### Issue 3: "❌ Required columns (ENVIRONMENT, EMAIL, PASSWORD) not found"

**Solution:**
- Open Excel file
- Check Row 1 headers:
  - Column A: `ENVIRONMENT`
  - Column B: `EMAIL`
  - Column C: `PASSWORD`
- Headers must match exactly (case-insensitive but must exist)

### Issue 4: "❌ No credentials found for environment: dev"

**Solution:**
- Check if Row 2 or later has data for environment `dev`
- Verify ENVIRONMENT value is exactly `dev` (lowercase)
- Ensure EMAIL and PASSWORD are not empty

### Issue 5: Login fails but credentials look correct

**Solution:**
- Double-check password in Excel matches actual password
- Check for extra spaces: ` password` vs `password`
- Verify email format: `user@domain.com`
- Check if password contains special characters that need escaping

### Issue 6: Report shows ConstantGlobal.VALID_EMAIL instead of Excel email

**Solution:**
- Verify LoginDataHelper.initializeFromExcel() was called in @BeforeAll
- Check log: "✅ Login credentials loaded from Excel"
- If log shows error, fix Excel file structure
- Check if fallback to ConstantGlobal happened (see logs)

---

## 🔐 Security Considerations

### Do's ✅
- Keep `data/LoginData.xlsx` in `.gitignore`
- Use encrypted or masked passwords in version control
- Limit file access to authorized team members
- Use strong, unique passwords for production accounts
- Rotate credentials regularly

### Don'ts ❌
- Never commit Excel file with real passwords to git
- Don't share file over unsecured channels
- Don't hardcode passwords in test code anymore
- Don't use same password across environments

### Best Practice
```gitignore
# .gitignore
data/LoginData.xlsx
data/*.xlsx
!data/LoginData.xlsx.example
```

Create template file: `data/LoginData.xlsx.example`
```
ENVIRONMENT | EMAIL | PASSWORD
dev | example-dev@test.com | Example@123
staging | example-qa@test.com | Example@123
prod | example-prod@test.com | Example@123
```

---

## 📚 Related Files

- `src/main/java/helpers/ExcelHelper.java` - Reads Excel file
- `src/main/java/helpers/LoginDataHelper.java` - Manages credentials in memory
- `src/test/java/hooks/CucumberHooks.java` - Initialization hook
- `QUICK_START_GUIDE.md` - Quick reference guide
- `IMPLEMENTATION_GUIDE.md` - Detailed implementation documentation

---

## 💬 Example: Adding New Environment

### Current Setup
```
dev | dev@test.com | Dev@123
staging | qa@test.com | QA@123
```

### Add Production Environment

**Step 1:** Open `data/LoginData.xlsx`

**Step 2:** Add new row
```
ENVIRONMENT | EMAIL | PASSWORD
dev | dev@test.com | Dev@123
staging | qa@test.com | QA@123
prod | prod@test.com | Prod@123  ← NEW ROW
```

**Step 3:** Save file

**Step 4:** Add Maven profile to `pom.xml` (if not exists)
```xml
<profile>
    <id>prod</id>
    <properties>
        <env>prod</env>
    </properties>
</profile>
```

**Step 5:** Run test
```bash
mvn clean test -Denv=prod
```

---

## 🎓 Learning Path

1. **Start:** Read `QUICK_START_GUIDE.md`
2. **Setup:** Create `data/LoginData.xlsx` with 1 environment
3. **Run:** Execute test with `mvn clean test`
4. **Verify:** Check report shows Excel data
5. **Expand:** Add more environments
6. **Master:** Read `IMPLEMENTATION_GUIDE.md` for deep dive

---

## 📞 Troubleshooting Checklist

Before asking for help, check:

- [ ] File `data/LoginData.xlsx` exists
- [ ] Sheet `LoginData` exists in file
- [ ] Row 1 has headers: ENVIRONMENT, EMAIL, PASSWORD
- [ ] Row 2+ has data for environment you're testing
- [ ] Project compiled: `mvn clean compile`
- [ ] Correct environment passed: `mvn clean test -Denv=dev`
- [ ] Excel file not corrupted (open and view in Excel)
- [ ] No extra spaces or special characters in environment name
- [ ] Log shows: "✅ Login credentials found for environment: dev"

---

**Last Updated:** April 4, 2026  
**Status:** ✅ Complete & Ready to Use

