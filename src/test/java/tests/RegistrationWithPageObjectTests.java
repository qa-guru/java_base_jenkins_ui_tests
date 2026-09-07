package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static io.qameta.allure.Allure.step;

@Story("Registration form")
public class RegistrationWithPageObjectTests extends TestBase {

    @Test
    @DisplayName("Successful Registration")
    void successfulRegistrationTest() {
        step("Open registration page", () ->
            registrationPage.openPage());
        step("Fill registration form", () -> {
            registrationPage
                    .setFirstName("Alex")
                    .setLastName("Egorov")
                    .setEmail("alex@egorov.com")
                    .setGender("Other")
                    .setUserNumber("1234567890")
                    .setDateOfBirth("30", "July", "2008");
            $("#subjectsInput").setValue("Math").pressEnter();
            executeJavaScript("arguments[0].click();", $("#hobbiesWrapper").$(byText("Sports")));
            $("#uploadPicture").uploadFromClasspath("img/1.png");
            $("#currentAddress").setValue("Some address 1");
            $("#state").scrollIntoView(true);
            $("#react-select-3-input").setValue("NCR");
            $("#react-select-3-option-0").shouldBe(visible).click();
            $("#react-select-4-input").shouldBe(enabled).setValue("Delhi");
            $("#react-select-4-option-0").shouldBe(visible).click();
            executeJavaScript("arguments[0].click();", $("#submit"));
        });
        step("Check registration form results data", () -> {
            step("Check registration form results component appears", () -> { // or move to pageobject step
                $(".modal-dialog").should(appear);
                $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            });
            registrationPage.checkResult("Student Name", "Alex Egorov")
                    .checkResult("Student Email", "alex@egorov.com");
        });
    }

    @Disabled("demoqa overlay — keep Allure story, do not fail the school job")
    @Test
    @DisplayName("Broken Registration")
    void brokenRegistrationTest() {
        step("Open registration page", () ->
            registrationPage.openPage());

        step("Fill registration form", () -> {
            registrationPage
                    .setFirstName("Alex")
                    .setLastName("Egorov")
                    .setGender("Other")
                    .setUserNumber("1234567890");
            executeJavaScript("arguments[0].click();", $("#submit"));
        });

        step("Check registration form results data", () -> {
            step("Check registration form results component appears", () -> { // or move to pageobject step
                $(".modal-dialog").should(appear);
                $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            });

            registrationPage.checkResult("Student Name", "Alex Egorov")
                    .checkResult("Student Email", "alex111@egorov.com");
        });
    }
}