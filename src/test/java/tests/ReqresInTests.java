package tests;



import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.LoginSpecs.*;

public class ReqresInTests extends TestBase {

    @Test
    @DisplayName("authorization test")
    void successFulLoginTest() {
        LoginBodyLombokModel requestBody = new LoginBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = step("make request", () ->
                given()
                        .spec(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response200Spec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken()));
    }

    @Test
    @DisplayName("negative authorization test")
    void negativeLogin400Test() {
        LoginBodyLombokModel requestBody = new LoginBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");
        LoginResponseLombokModel loginResponse = step("make request", () ->
                given()
                        .spec(negativeRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response400Spec)
                        .extract().as(LoginResponseLombokModel.class));
        step("Check response", () ->
                assertEquals("Missing email or username", loginResponse.getError()));

    }

    @Test
    @DisplayName("Unsupported Media Type")
    void negativeLoginTest() {
        LoginUnSuccessfulModel response = step("Make a request", () -> given(requestSpec)
                .when()
                .post("/login")
                .then()
                .spec(response400Spec)
                .extract().as(LoginUnSuccessfulModel.class));
        step("Check response", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    @DisplayName("Not found user")
    void tests404() {
        step("Make a request", () -> given(negativeRequestSpec)
                .when()
                .get("/users/23")
                .then()
                .spec(response404Spec));
    }

    @Test
    @DisplayName("No Content Success status")
    void noContentTest() {
        step("Make a request", () -> given(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(response204Spec));
    }

    @Test
    @DisplayName("existing ids")
    void foundUsersTest() {
        Integer[] userId = {7, 8, 9, 10, 11, 12};

        ListUsersResponse response = step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(responseId200Spec)
                        .extract().as(ListUsersResponse.class));

        HashSet<Integer> idsFromResponse = new HashSet<>();
        LinkedList<User> usersFromResponse = response.getData();
        //for each user in list loop
        for (User user : usersFromResponse) {
            idsFromResponse.add(user.getId()); //get id
        }

        //2. check each element of array contains in hashset

        step("Check response", () -> {
            for (int id : userId) {
                assertTrue(idsFromResponse.contains(id));
            }
            assertEquals(idsFromResponse.size(), userId.length);
        });
    }

}