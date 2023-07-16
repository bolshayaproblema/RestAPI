package tests.demowebshop;

import helpers.AuthApi;
import org.junit.jupiter.api.Test;

import static helpers.AuthApi.authCookieKey;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CartTests extends TestBase{
    @Test
    void addCartAsAuthorizedTest(){
        String  authCookieValue = AuthApi.getAuthCookie(login,password) ;


        String data = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=58" +
                "&addtocart_72.EnteredQuantity=2";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8") // - headers
                .body(data)
                .cookie(authCookieKey, authCookieValue)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }
    @Test
    void addToCartAsAnonymTest(){

        String data = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=58" +
                "&addtocart_72.EnteredQuantity=2";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8") // - headers
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(2)"));
    }
}
