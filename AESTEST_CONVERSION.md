# AesTest.java - JUnit Test Conversion Guide

## Original Test File Location
- **From:** `src/main/java/com/hooni/encrypt/AesTest.java` (old JUnit 3 style)
- **To:** `src/test/java/com/hooni/encrypt/AesTest.java` (new JUnit 4 style)

## Conversion Summary

### Original (JUnit 3 - extends TestCase)
```java
public class AesTest extends HooniTestCase {
    protected void setUp() throws Exception { ... }
    protected void tearDown() throws Exception { ... }
    public void testEncrypt() { ... }
    public void testDecript() { ... }
}
```

### Modern (JUnit 4 - Annotations)
```java
public class AesTest {
    @Before
    public void setUp() { ... }
    
    @After
    public void tearDown() { ... }
    
    @Test
    public void testEncrypt() { ... }
    
    @Test
    public void testDecrypt() { ... }
}
```

## Key Changes

| Aspect | Old (JUnit 3) | New (JUnit 4) |
|--------|---------------|---------------|
| **Inheritance** | extends TestCase | No inheritance needed |
| **Setup** | protected void setUp() | @Before public void setUp() |
| **Teardown** | protected void tearDown() | @After public void tearDown() |
| **Test Methods** | public void test*() | @Test public void testName() |
| **Assertions** | JUnit 3 assertions | JUnit 4 assertions |
| **Configuration** | setUp/tearDown hooks | @Before/@After annotations |

## Improvements Made

### 1. **Constants Extracted**
```java
// Before
private String _password = "music&&songHU123";
private static String[] _testStrs = { ... };

// After
private static final String PASSWORD = "music&&songHU123";
private static final String[] TEST_STRINGS = { ... };
```

### 2. **Naming Conventions**
```java
// Before
String encrypted_text = ...
String decrypt_text = ...
public void testDecript() { }  // typo: Decript

// After
String encryptedText = ...
String decryptedText = ...
public void testDecrypt() { }  // fixed: Decrypt
```

### 3. **Better Assertions with Messages**
```java
// Before
assertTrue(encrypted_text.length()%4==0);
assertEquals(testStr,decrypt_text);

// After
assertTrue("Encrypted text length should be divisible by 4 (Base64 format)",
    encryptedText.length() % 4 == 0);
assertEquals("Decrypted text should match original for: " + testStr,
    testStr, decryptedText);
```

### 4. **New Test Cases Added**
- `testEncryptEmptyString()` - Edge case: empty string
- `testEncryptUnicodeString()` - Unicode/emoji support
- `testDifferentPasswords()` - Different passwords = different output
- `testDecryptionWithWrongPassword()` - Security check
- `testEncryptedTextIsValidBase64()` - Format validation

## Converted Test Code

```java
package com.hooni.encrypt;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class AesTest {

    private static final String PASSWORD = "music&&songHU123";
    private static final String[] TEST_STRINGS = {
        "simple",
        "little more words",
        "test with numbers 1 2 3 23333333",
        "long text, at least more the 100 words, " +
        "it is fun to see this happpening, " +
        "I want to put it up as soon as possible.",
        "with specail chars: $100 is not much, & it is !@@@@#$"
    };

    private AesIO aesIO;

    @Before
    public void setUp() {
        aesIO = new AesIO();
    }

    @After
    public void tearDown() {
        aesIO = null;
    }

    @Test
    public void testEncrypt() throws Exception {
        for (String testStr : TEST_STRINGS) {
            String encryptedText = aesIO.encrypt(testStr, PASSWORD, 256);
            
            assertTrue("Encrypted text length should be divisible by 4 (Base64 format)",
                encryptedText.length() % 4 == 0);
            
            String decryptedText = aesIO.decrypt(encryptedText, PASSWORD, 256);
            
            assertEquals("Decrypted text should match original for: " + testStr,
                testStr, decryptedText);
        }
    }

    @Test
    public void testDecrypt() throws Exception {
        String encryptedFromBrowser = 
            "lQBYoPiqpE59kkH7XGXEgnbBMNGjqXM8cObjsEa6Gm5VjgTYyCUdav8=";
        String expectedText = "must something get to move to sky";
        String password = "xin001";
        
        String decryptedText = aesIO.decrypt(encryptedFromBrowser, password, 256);
        
        assertEquals("Should correctly decrypt the known encrypted string from browser",
            expectedText, decryptedText);
    }

    @Test
    public void testEncryptEmptyString() throws Exception {
        String emptyString = "";
        String encryptedText = aesIO.encrypt(emptyString, PASSWORD, 256);
        String decryptedText = aesIO.decrypt(encryptedText, PASSWORD, 256);
        
        assertEquals("Empty string should encrypt and decrypt correctly",
            emptyString, decryptedText);
    }

    @Test
    public void testEncryptUnicodeString() throws Exception {
        String unicodeString = "Hello 世界 🌍 مرحبا мир";
        String encryptedText = aesIO.encrypt(unicodeString, PASSWORD, 256);
        String decryptedText = aesIO.decrypt(encryptedText, PASSWORD, 256);
        
        assertEquals("Unicode string should encrypt and decrypt correctly",
            unicodeString, decryptedText);
    }

    @Test
    public void testDifferentPasswords() throws Exception {
        String testString = "secret message";
        String password1 = "password1";
        String password2 = "password2";
        
        String encrypted1 = aesIO.encrypt(testString, password1, 256);
        String encrypted2 = aesIO.encrypt(testString, password2, 256);
        
        assertNotEquals("Different passwords should produce different encrypted results",
            encrypted1, encrypted2);
    }

    @Test
    public void testDecryptionWithWrongPassword() throws Exception {
        String testString = "secret";
        String correctPassword = "correct";
        String wrongPassword = "wrong";
        
        String encrypted = aesIO.encrypt(testString, correctPassword, 256);
        String decrypted = aesIO.decrypt(encrypted, wrongPassword, 256);
        
        assertNotEquals("Decryption with wrong password should not produce original text",
            testString, decrypted);
    }

    @Test
    public void testEncryptedTextIsValidBase64() throws Exception {
        for (String testStr : TEST_STRINGS) {
            String encrypted = aesIO.encrypt(testStr, PASSWORD, 256);
            
            assertTrue("All encrypted text should have length divisible by 4 for Base64 encoding",
                encrypted.length() % 4 == 0);
            
            assertTrue("Encrypted text should contain only Base64 characters",
                encrypted.matches("^[A-Za-z0-9+/]*={0,2}$"));
        }
    }
}
```

## How to Move to Test Directory

### Step 1: Create Test Directory Structure
```bash
mkdir -p src/test/java/com/hooni/encrypt
```

### Step 2: Move File
```bash
# Windows
move src\main\java\com\hooni\encrypt\AesTest.java ^
      src\test\java\com\hooni\encrypt\AesTest.java

# Linux/Mac
mv src/main/java/com/hooni/encrypt/AesTest.java \
   src/test/java/com/hooni/encrypt/AesTest.java
```

### Step 3: Update pom.xml (if needed)
The following dependency is already added for JUnit 4 support:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Step 4: Run Tests
```bash
mvn test -Dtest=AesTest
# or run all tests
mvn test
```

## Running the Tests

### Run Specific Test Class
```bash
mvn test -Dtest=AesTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=AesTest#testEncrypt
```

### Run All Tests
```bash
mvn test
```

### Run with Coverage (if maven-jacoco-plugin is configured)
```bash
mvn test jacoco:report
```

## Test Methods Breakdown

| Method | Purpose | Input | Validation |
|--------|---------|-------|-----------|
| `testEncrypt()` | Main encrypt/decrypt cycle | 5 test strings | Encrypted length divisible by 4, decrypt equals original |
| `testDecrypt()` | Known ciphertext decryption | Browser-generated encrypted string | Matches expected plaintext |
| `testEncryptEmptyString()` | Edge case: empty input | Empty string "" | Handles gracefully |
| `testEncryptUnicodeString()` | International characters | Unicode/emoji mix | Preserves all characters |
| `testDifferentPasswords()` | Password uniqueness | Same plaintext, different passwords | Different ciphertexts |
| `testDecryptionWithWrongPassword()` | Security validation | Correct text + wrong password | Doesn't decrypt to original |
| `testEncryptedTextIsValidBase64()` | Format validation | All test strings | All meet Base64 spec |

## Migration Benefits

✅ **Modern Format** - JUnit 4 is industry standard  
✅ **Better Readability** - Annotations over inheritance  
✅ **Improved Messages** - Assertion messages for debugging  
✅ **More Coverage** - 7 tests vs 2 original  
✅ **Edge Cases** - Unicode, empty strings, wrong password  
✅ **No Inheritance** - Cleaner code structure  
✅ **Convention Over Config** - Automatic test detection  

## Next Steps

1. Move AesTest.java to `src/test/java/com/hooni/encrypt/`
2. Run: `mvn clean test`
3. Verify all 7 tests pass ✅
4. Consider similar conversions for other legacy test files
