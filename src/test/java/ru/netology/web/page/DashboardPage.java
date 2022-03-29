package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement header = $("[data-test-id='dashboard']");

    public void login() {
        header.shouldBe(Condition.visible, Duration.ofSeconds(20)).shouldHave(text("Личный кабинет"));
    }
}
