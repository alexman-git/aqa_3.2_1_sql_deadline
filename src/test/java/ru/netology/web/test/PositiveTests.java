package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbUtils;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class PositiveTests {

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
}
