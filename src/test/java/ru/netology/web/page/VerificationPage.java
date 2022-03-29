package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[type='button']");
    private SelenideElement validationMessageEmptyCode = $(".input__sub");
    private SelenideElement errorMessageInvalidCode = $("[data-test-id='error-notification'] .notification__content");


    public VerificationPage() {
        codeField.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void getErrorMessageInvalidCode() {
        errorMessageInvalidCode.shouldBe(Condition.visible, Duration.ofSeconds(10)).shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void getMessageEmptyCode() {
        validationMessageEmptyCode.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public DashboardPage validCode(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidCode(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        getErrorMessageInvalidCode();
    }

    public void emptyCode() {
        verifyButton.click();
        getMessageEmptyCode();
    }
}
