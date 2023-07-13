package tests.demowebshop;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class LoginTests {

    String login = "yu@yandex.ru",
            password = "yu@yandex.ru1";

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demowebshop.tricentis.com/";
        RestAssured.baseURI = "https://demowebshop.tricentis.com/";
    }

    @Test
    void loginWithUITest() {
        step("Open login page", () ->
                open("/login"));
        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password).pressEnter();
        });
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    void loginWithApiTest() {
        step("Get authorization cookie by Api and set it to browser", () -> {
            String autCookieKey = "NOPCOMMERCE.AUTH";
            String authCookeValue = given()
                    .contentType("application/x-www-form-urlencoded") // - headers
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract()
                    .cookie(autCookieKey);

            open("https://demowebshop.tricentis.com/Content/jquery-ui-themes/smoothness/images/ui-bg_flat_75_ffffff_40x100.png");
            Cookie authCookie = new Cookie(autCookieKey, authCookeValue);
            getWebDriver().manage().addCookie(authCookie);
        });

        step("Open main page", () ->
                open(""));

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }
}
