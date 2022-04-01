package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbUtils;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTests {

    @AfterAll
    static void cleanData() {
        DbUtils.cleanDatabase();
    }

    @Test
    void shouldAuthorizeSuccessfully_1stUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidAuthInfo_1stUser();
        var verificationPage = loginPage.validAuthInfo(authInfo);
        var verifyInfo = DbUtils.getValidVerificationCode();
        var dashboardPage = verificationPage.validCode(verifyInfo);
        dashboardPage.login();
    }

    @Test
    void shouldAuthorizeSuccessfully_2ndUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidAuthInfo_2ndUser();
        var verificationPage = loginPage.validAuthInfo(authInfo);
        var verifyInfo = DbUtils.getValidVerificationCode();
        var dashboardPage = verificationPage.validCode(verifyInfo);
        dashboardPage.login();
    }

    @Test
    void shouldNotValidateWithBothEmptyFields() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.emptyAuthInfo();
    }

    @Test
    void shouldNotValidateWithEmptyPassword_1stUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidLoginAndEmptyPassword_1stUser();
        loginPage.validLoginAndEmptyPassword(authInfo);
    }

    @Test
    void shouldNotValidateWithEmptyPassword_2ndUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidLoginAndEmptyPassword_2ndUser();
        loginPage.validLoginAndEmptyPassword(authInfo);
    }

    @Test
    void shouldNotAuthorizeWithInvalidPassword_1stUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidLoginAndInvalidPassword_1stUser();
        loginPage.invalidAuthInfo(authInfo);
    }

    @Test
    void shouldNotAuthorizeWithInvalidPassword_2ndUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidLoginAndInvalidPassword_2ndUser();
        loginPage.invalidAuthInfo(authInfo);
    }

    @Test
    void shouldNotAuthorizeWithInvalidLogin_1stUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getInvalidLoginAndValidPassword_1stUser();
        loginPage.invalidAuthInfo(authInfo);
    }

    @Test
    void shouldNotAuthorizeWithInvalidLogin_2ndUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getInvalidLoginAndValidPassword_2ndUser();
        loginPage.invalidAuthInfo(authInfo);
    }

    @Test
    void shouldNotLoginWithEmptyCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidAuthInfo_1stUser();
        var verificationPage = loginPage.validAuthInfo(authInfo);
        verificationPage.emptyCode();
    }

    @Test
    void shouldNotLoginWithInvalidCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidAuthInfo_2ndUser();
        var verificationPage = loginPage.validAuthInfo(authInfo);
        var verifyInfo = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidCode(verifyInfo);
    }

    @Test
    void shouldBlockAfterThreeAttemptsWithInvalidPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getValidLoginAndInvalidPassword_1stUser();
        loginPage.blockSystem(authInfo);
    }
}
