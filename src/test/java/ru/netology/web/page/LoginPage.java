package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");
    private SelenideElement validationMessageEmptyLogin = $$(".input__sub").first();
    private SelenideElement validationMessageEmptyPassword = $$(".input__sub").last();
    private SelenideElement errorMessageInvalidAuthInfo = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement errorMessageCloseButton = $(".icon_name_close");
    private SelenideElement errorMessageBlockedSystem = $("[data-test-id='block-notification'] .notification__content");


    public void getMessageEmptyLogin() {
        validationMessageEmptyLogin.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void getMessageEmptyPassword() {
        validationMessageEmptyPassword.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void getErrorMessageInvalidAuthInfo() {
        errorMessageInvalidAuthInfo.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void getErrorMessageBlockedSystem() {
        errorMessageBlockedSystem.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль 3 раза подряд." + " " +
                "Система заблокирована."));
    }

    public VerificationPage validAuthInfo(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void emptyAuthInfo() {
        loginButton.click();
        getMessageEmptyLogin();
        getMessageEmptyPassword();
    }

    public void validLoginAndEmptyPassword(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        getMessageEmptyPassword();
    }

    public void invalidAuthInfo(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        getErrorMessageInvalidAuthInfo();
    }

    public void blockSystem(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        getErrorMessageInvalidAuthInfo();
        errorMessageCloseButton.click();
        passwordField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        passwordField.setValue(info.getPassword());
        loginButton.click();
        getErrorMessageInvalidAuthInfo();
        errorMessageCloseButton.click();
        passwordField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        passwordField.setValue(info.getPassword());
        loginButton.click();
        getErrorMessageBlockedSystem();
    }
}
