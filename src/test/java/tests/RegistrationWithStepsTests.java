package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class RegistrationWithStepsTests extends TestBase {
    @Test
    void successfulRegistrationTest() {
        step("Open form", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
            executeJavaScript("document.querySelectorAll('#fixedban, footer, iframe').forEach(function(el){el.remove();});");
        });
        step("Fill form", () -> {
            $("#firstName").setValue("Alex");
            $("#lastName").setValue("Egorov");
            $("#userEmail").setValue("alex@egorov.com");
            executeJavaScript("arguments[0].click();", $("#genterWrapper").$(byText("Other")));
            $("#userNumber").setValue("1234567890");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("July");
            $(".react-datepicker__year-select").selectOption("2008");
            $(".react-datepicker__day--030:not(.react-datepicker__day--outside-month)").click();
            $("#subjectsInput").setValue("Math").pressEnter();
            executeJavaScript("arguments[0].click();", $("#hobbiesWrapper").$(byText("Sports")));
            $("#uploadPicture").uploadFromClasspath("img/1.png");
            $("#currentAddress").setValue("Some address 1");
            executeJavaScript("arguments[0].click();", $("#state"));
            executeJavaScript("arguments[0].click();", $("#stateCity-wrapper").$(byText("NCR")));
            executeJavaScript("arguments[0].click();", $("#city"));
            executeJavaScript("arguments[0].click();", $("#stateCity-wrapper").$(byText("Delhi")));
            executeJavaScript("arguments[0].click();", $("#submit"));
        });
        step("Verify results", () -> {
            $(".modal-dialog").should(appear);
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text("Alex"), text("Egorov"),
                    text("alex@egorov.com"), text("1234567890"));
        });
    }
}
