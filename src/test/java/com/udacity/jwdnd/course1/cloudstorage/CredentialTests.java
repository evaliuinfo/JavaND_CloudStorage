package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests extends CloudStorageApplicationTests {

    public static final String BEATLES_URL = "https://www.thebeatles.com";
    public static final String BEATLES_USERNAME = "thebeatles";
    public static final String BEATLES_PASSWORD = "password";
    public static final String JOHN_URL = "http://www.johnlennon.com/";
    public static final String JOHN_USERNAME = "johnlennon";
    public static final String JOHN_PASSWORD = "band";

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }

    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        homePage.saveCredentialChanges();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();
    }

    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) {
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUserName());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    @Test
    public void testCredentialCreation() {
        HomePage homePage = signupAndLogin();
        createAndVerifyCredential(BEATLES_URL, BEATLES_USERNAME, BEATLES_PASSWORD, homePage);
        homePage.deleteCredential();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.logout();
    }

    @Test
    public void testCredentialModification() {
        HomePage homePage = signupAndLogin();
        createAndVerifyCredential(BEATLES_URL, BEATLES_USERNAME, BEATLES_PASSWORD, homePage);
        Credential origCredential  = homePage.getFirstCredential();
        String firstEncryptedPassword = origCredential.getPassword();
        homePage.editCredential();

        setCredentialFields(JOHN_URL, JOHN_USERNAME, JOHN_PASSWORD, homePage);
        homePage.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.navToCredentialsTab();
        Credential modifiedCredential = homePage.getFirstCredential();
        Assertions.assertEquals(JOHN_URL, modifiedCredential.getUrl());
        Assertions.assertEquals(JOHN_USERNAME, modifiedCredential.getUserName());
        Assertions.assertNotEquals(JOHN_PASSWORD, modifiedCredential.getPassword());
        Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredential.getPassword());
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.logout();
    }

    @Test
    public void testDeletion() {
        HomePage homePage = signupAndLogin();
        createCredential(BEATLES_URL, BEATLES_USERNAME, BEATLES_PASSWORD, homePage);
        Assertions.assertFalse(homePage.noCredentials(driver));
        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.navToCredentialsTab();
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }
}
