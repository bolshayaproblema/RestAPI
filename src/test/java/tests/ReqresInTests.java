package tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTests {

@Test
    void successFulLoginTest(){
    String requestBody = "{\n" +
            "    \"email\": \"eve.holt@reqres.in\",\n" +
            "    \"password\": \"cityslicka\"\n" +
            "}"; // Bad Practice
    given()
            .log().uri()
            .log().body()
            .contentType(JSON)
            .body(requestBody)
            .when()
            .post("https://reqres.in/api/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("token", is("QpwL5tke4Pnpja7X4"));
}
@Test
    void negativeLogin400Test(){
    String requestBody = "{\n" +
            "    \"email\": \"eve.holt@reqres.in\",\n" +
            "    \"password\": \"cityslicka\"\n" +
            "}"; // Bad Practice
    given()
            .log().uri()
            .log().body()
            .body(requestBody)
            .when()
            .post("https://reqres.in/api/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(400)
            .body("error", is ("Missing email or username"));
}
@Test
    void negativeLoginTest(){
    given()
            .log().uri()
            .log().body()
            .when()
            .post("https://reqres.in/api/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(415);
}
 @Test
 @DisplayName("Not found user")
    void tests404(){
    given()
            .log().uri()
            .log().body()
            .when()
            .get("https://reqres.in/api/users/23")
            .then()
            .log().status()
            .log().body()
            .statusCode(404);
}
@Test
 @DisplayName("No Content Success status")
    void noContentTest(){
    given()
            .log().uri()
            .log().body()
            .when()
            .delete("https://reqres.in/api/users/2")
            .then()
            .log().status()
            .log().body()
            .statusCode(204);
}@Test
 @DisplayName("find a user by id")
    void negativeFoundUsersTest(){
    given()
            .log().uri()
            .log().body()
            .when()
            .get("https://reqres.in/api/users?page=2")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data.id", hasItems(3, 8, 9));
}
}

