# 📋 Excel Template & Examples

## 🎯 Simple Copy-Paste Templates

### Template 1: Minimal Setup (Dev Only)

Use this untuk development dan testing lokal:

```
ENVIRONMENT | EMAIL | PASSWORD
dev | developer@test.com | Dev@12345
```

**When to use:** Local development, initial testing

---

### Template 2: Standard Setup (3 Environments)

Use this untuk testing di semua environment:

```
ENVIRONMENT | EMAIL | PASSWORD
dev | dev@eventhub.com | Dev@12345
staging | qa@eventhub.com | QA@12345
prod | prod@eventhub.com | Prod@12345
```

**When to use:** Continuous integration, CI/CD pipeline

---

### Template 3: Extended Setup (Multiple Users)

Use ini untuk test berbagai user types:

```
ENVIRONMENT | EMAIL | PASSWORD
dev | admin-dev@eventhub.com | AdminDev@12345
dev | user-dev@eventhub.com | UserDev@12345
staging | admin-qa@eventhub.com | AdminQA@12345
staging | user-qa@eventhub.com | UserQA@12345
prod | admin-prod@eventhub.com | AdminProd@12345
prod | user-prod@eventhub.com | UserProd@12345
```

**Note:** System akan ambil user pertama untuk environment tersebut
**When to use:** Multiple user scenario testing

---

## 🛠️ Step-by-Step Setup

### Method 1: Using Excel Desktop Application

#### Step 1: Create/Open File
```
1. Open Microsoft Excel or LibreOffice Calc
2. Create new spreadsheet
3. Save as: LoginData.xlsx
4. Place in: data/ folder at project root
```

#### Step 2: Add Headers
```
Row 1:
  Column A: ENVIRONMENT
  Column B: EMAIL
  Column C: PASSWORD
```

#### Step 3: Add Data
```
Row 2:
  Column A: dev
  Column B: dev@test.com
  Column C: Dev@123

Row 3:
  Column A: staging
  Column B: qa@test.com
  Column C: QA@123

Row 4:
  Column A: prod
  Column B: prod@test.com
  Column C: Prod@123
```

#### Step 4: Format (Optional but Recommended)
```
1. Select all cells with data (A1:C4)
2. Right-click → Format Cells
3. Set borders and alignment
4. Save file
```

---

### Method 2: Using LibreOffice (Cross-Platform)

```
1. File → New → Spreadsheet
2. Type headers in first row
3. Type data in rows 2-4
4. File → Save As
5. Choose: ODS or Excel format (.xlsx)
6. Name: LoginData.xlsx
7. Location: data/ folder
```

---

### Method 3: Using Google Sheets → Download

```
1. Open Google Sheets
2. Create new sheet
3. Add headers and data
4. File → Download → Microsoft Excel (.xlsx)
5. Place LoginData.xlsx in data/ folder
```

---

## 📊 Real-World Examples

### Example 1: EventHub Application

```
ENVIRONMENT | EMAIL | PASSWORD
dev | qa.automation@eventhub.com | QA123!@#EventHub
staging | staging@eventhub.com | Stage123!@#EventHub
prod | production@eventhub.com | Prod123!@#EventHub
```

### Example 2: Banking Application

```
ENVIRONMENT | EMAIL | PASSWORD
dev | testuser.dev@bank.com | BankDev@2024
qa | testuser.qa@bank.com | BankQA@2024
staging | testuser.staging@bank.com | BankStaging@2024
prod | testuser.prod@bank.com | BankProd@2024
```

### Example 3: Multi-Role Testing

```
ENVIRONMENT | EMAIL | PASSWORD
dev | admin@app.com | AdminDev@2024
dev | user@app.com | UserDev@2024
dev | guest@app.com | GuestDev@2024
qa | admin@app.com | AdminQA@2024
qa | user@app.com | UserQA@2024
qa | guest@app.com | GuestQA@2024
```

### Example 4: SaaS Application

```
ENVIRONMENT | EMAIL | PASSWORD
dev | admin@company-dev.com | AdminPass@Dev2024
dev | editor@company-dev.com | EditorPass@Dev2024
staging | admin@company-staging.com | AdminPass@Staging2024
staging | editor@company-staging.com | EditorPass@Staging2024
prod | admin@company-prod.com | AdminPass@Prod2024
prod | editor@company-prod.com | EditorPass@Prod2024
```

---

## 📝 Important Notes

### Format Requirements

✅ **Column Names (Row 1):**
- Must be exactly: `ENVIRONMENT`, `EMAIL`, `PASSWORD`
- Case-insensitive (but must exist)
- Cannot add extra spaces: `ENVIRONMENT` not ` ENVIRONMENT`

✅ **Environment Values:**
- Lowercase: `dev`, `staging`, `prod`
- No spaces
- Must match `-Denv=` parameter in command

✅ **Email Format:**
- Valid email: `user@domain.com`
- Can contain: letters, numbers, dots, hyphens
- Example: `qa-user_123@test-domain.co.uk`

✅ **Password:**
- Can contain: any character
- Recommended: alphanumeric + special chars
- Example: `Pass@12345` or `P@ssw0rd!`
- Leave empty only if not required

---

## 🔄 File Format Compatibility

### Excel File Formats Supported

| Format | Extension | Status |
|--------|-----------|--------|
| Excel 2007+ | .xlsx | ✅ Recommended |
| Excel 97-2003 | .xls | ⚠️ May work |
| OpenDocument | .ods | ✅ Works |
| CSV | .csv | ❌ Not supported |

**Recommendation:** Use `.xlsx` format for best compatibility

---

## 🚀 Quick Integration Steps

### 1-Minute Setup

```bash
# 1. Copy this template
ENVIRONMENT | EMAIL | PASSWORD
dev | dev@test.com | Test@123

# 2. Create Excel file with this data
# File: data/LoginData.xlsx
# Sheet: LoginData

# 3. Run test
mvn clean test

# 4. Check log
# Should see: ✅ Login credentials found for environment: dev
```

---

## ⚠️ Common Mistakes to Avoid

### ❌ Mistake 1: Wrong Column Header
```
WRONG:  Environment | Email | Pass
RIGHT:  ENVIRONMENT | EMAIL | PASSWORD
```

### ❌ Mistake 2: Uppercase Environment
```
WRONG:  DEV | user@test.com | Pass@123
RIGHT:  dev | user@test.com | Pass@123
```

### ❌ Mistake 3: File in Wrong Location
```
WRONG:  cucumber_hybrid_framework/LoginData.xlsx
RIGHT:  cucumber_hybrid_framework/data/LoginData.xlsx
```

### ❌ Mistake 4: Wrong Sheet Name
```
WRONG:  Sheet: Login or Credentials
RIGHT:  Sheet: LoginData
```

### ❌ Mistake 5: Extra Spaces
```
WRONG:  " dev " | " user@test.com " | " Pass@123 "
RIGHT:  "dev" | "user@test.com" | "Pass@123"
```

---

## 🔍 Verification Checklist

Use ini untuk verify Excel setup:

```
File Location: ✓
  □ File exists at: data/LoginData.xlsx
  □ File is readable
  □ File is .xlsx or .ods format

Sheet Structure: ✓
  □ Sheet name is: LoginData
  □ Row 1 has headers: ENVIRONMENT, EMAIL, PASSWORD
  □ Row 2+ has data

Column Data: ✓
  □ ENVIRONMENT column contains: dev, staging, prod (lowercase)
  □ EMAIL column contains valid emails
  □ PASSWORD column contains passwords
  □ No empty cells except optional ones

Formatting: ✓
  □ No extra spaces in column names
  □ No extra spaces in data values
  □ No hidden rows or columns
  □ All data properly typed (not formulas)

Permissions: ✓
  □ File is readable
  □ File is not write-protected
  □ No virus/malware alerts
```

---

## 📱 Mobile Testing Setup

If you also need mobile credentials:

```
ENVIRONMENT | EMAIL | PASSWORD | PLATFORM
dev | mobile-dev@test.com | MobDev@123 | iOS
dev | mobile-dev@test.com | MobDev@123 | Android
staging | mobile-qa@test.com | MobQA@123 | iOS
staging | mobile-qa@test.com | MobQA@123 | Android
prod | mobile-prod@test.com | MobProd@123 | iOS
prod | mobile-prod@test.com | MobProd@123 | Android
```

**Note:** Currently only reads 3 columns. For 4 columns, need to extend ExcelHelper.

---

## 🆘 Troubleshooting Setup

### Issue: "File not found"
```
Solution: Verify file at data/LoginData.xlsx
Command: ls data/LoginData.xlsx
```

### Issue: "Sheet not found"
```
Solution: Verify sheet name is exactly "LoginData"
Check: Open file and see sheet tabs at bottom
```

### Issue: "Required columns not found"
```
Solution: Verify headers in Row 1:
- A1: ENVIRONMENT
- B1: EMAIL
- C1: PASSWORD
```

### Issue: "No credentials found"
```
Solution: Verify data rows:
- Row 2+: dev, email, password
- No spaces in environment name
```

---

## 💾 Backup & Recovery

### Backup Excel File
```bash
# Before making changes
cp data/LoginData.xlsx data/LoginData.xlsx.backup

# After testing
cp data/LoginData.xlsx data/LoginData_v2.xlsx
```

### Recover from Backup
```bash
# If file corrupted
cp data/LoginData.xlsx.backup data/LoginData.xlsx
```

---

## 🎓 Learning Resources

### For Excel Beginners
1. Create new Excel file
2. Type headers in first row
3. Type data in rows below
4. Save as LoginData.xlsx

### For Power Users
- Use data validation for ENVIRONMENT column
- Use data validation dropdown for EMAIL/PASSWORD
- Use conditional formatting to highlight rows
- Use Excel macros to manage multiple versions

---

## 📞 Support

### If Setup Fails
1. Check file exists: `data/LoginData.xlsx`
2. Check sheet name: `LoginData`
3. Check headers: `ENVIRONMENT, EMAIL, PASSWORD`
4. Check data rows: `dev, email, password`
5. Delete file and recreate from template
6. Run: `mvn clean compile`
7. Run: `mvn clean test`
8. Check logs for errors

---

**Last Updated:** April 4, 2026
**Status:** ✅ Ready to Use

---

## 📋 Ready-to-Copy Data

### Copy-Paste Template 1 (Dev Only)
```
dev	dev@test.com	Dev@123
```

### Copy-Paste Template 2 (All Environments)
```
dev	dev@test.com	Dev@123
staging	qa@test.com	QA@123
prod	prod@test.com	Prod@123
```

### Copy-Paste Template 3 (Multiple Users)
```
dev	admin@test.com	AdminDev@123
dev	user@test.com	UserDev@123
staging	admin@test.com	AdminQA@123
staging	user@test.com	UserQA@123
prod	admin@test.com	AdminProd@123
prod	user@test.com	UserProd@123
```

*Paste these into Excel with headers already in Row 1*

