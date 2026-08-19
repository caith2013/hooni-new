package com.hooni.encrypt;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit 4 tests for AES encryption/decryption functionality.
 */
public class AesTest {

    private static final String PASSWORD = "music&&songHU123";
    private static final String[] TEST_STRINGS = {
        "simple",
        "little more words",
        "test with numbers 1 2 3 23333333",
        "long text, at least more the 100 words, it is fun to see this happpening, I want to put it up as soon as possible.",
        "with specail chars: $100 is not much, & it is !@@@@#$"
    };

    private AesIO aesIO;

    @Before
    public void setUp() throws Exception {
        aesIO = new AesIO();
    }

    @After
    public void tearDown() throws Exception {
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
        String encryptedFromBrowser = "lQBYoPiqpE59kkH7XGXEgnbBMNGjqXM8cObjsEa6Gm5VjgTYyCUdav8=";
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
