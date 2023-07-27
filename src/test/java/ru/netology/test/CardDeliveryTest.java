package ru.netology.test;

import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.Test;
import ru.netology.dataclass.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.dataclass.DataGenerator.generateDate;
import static ru.netology.dataclass.DataGenerator.generateSecondDate;

public class CardDeliveryTest {

    String name = DataGenerator.generateName();
    String phone = DataGenerator.generatePhone();
    String city = DataGenerator.generateCity();



    @Test
    void shouldArrangeDeliveryOfCard() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue(city);
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(generateDate(4));
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue(name);
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue(phone);
        $("[data-test-id=\"agreement\"]").click();
        $(".button__content").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(4)), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(generateSecondDate(5));
        $(".button__content").click();
        $(byText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateSecondDate(5)), Duration.ofSeconds(15)).shouldBe(visible);

    }
}
