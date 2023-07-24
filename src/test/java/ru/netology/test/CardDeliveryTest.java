package ru.netology.test;

import com.codeborne.selenide.SelenideElement;

import com.google.j2objc.annotations.LoopTranslation;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.dataclass.DataGenerator;
//import ru.netology.delivery.data.DataGenerator;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    String name = DataGenerator.generateName();
    String phone = DataGenerator.generatePhone();
    String city = DataGenerator.generateCity();

    @Test
    void shouldArrangeDeliveryOfCard() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue(city);
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(DataGenerator.generateDate(4));
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue(name);
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue(phone);
        $("[data-test-id=\"agreement\"]").click();
        $(".button__content").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(DataGenerator.generateDate(5));
        $(".button__content").click();
        $(byText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));

    }
}
