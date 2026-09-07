import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTests {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.browserVersion = "151.0";
        Configuration.timeout = 15000;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-dev-shm-usage", "--no-sandbox");
        chromeOptions.setCapability("se:cdpEnabled", false);
        chromeOptions.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", false
        ));
        Configuration.browserCapabilities = chromeOptions;
        Configuration.remote = "https://user1:1234@selenoid.qa.guru/wd/hub";
    }

    @Test
    void successfulRegistrationTest() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("document.querySelectorAll('#fixedban, footer, iframe').forEach(function(el){el.remove();});");

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
        $("#state").scrollIntoView(true);
        $("#react-select-3-input").setValue("NCR").pressEnter();
        $("#react-select-4-input").setValue("Delhi").pressEnter();
        executeJavaScript("arguments[0].click();", $("#submit"));

        $(".modal-dialog").should(appear);
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(text("Alex"), text("Egorov"),
                text("alex@egorov.com"), text("1234567890"));
    }
}
