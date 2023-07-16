package helpers;

import io.qameta.allure.Step;
import tests.demowebshop.TestBase;

import static io.restassured.RestAssured.given;

public class AuthApi extends TestBase {
    public static String authCookieKey = "NOPCOMMERCE.AUTH";

    @Step("Получение cookies авторизации")
    public static String getAuthCookie(String login, String password) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().cookie(authCookieKey);
    }
}