package tests.demowebshop;

import helpers.AuthApi;
import org.junit.jupiter.api.Test;

import static helpers.AuthApi.authCookieKey;
import static io.restassured.RestAssured.given;

public class EditProfileTest extends TestBase{
    @Test
    void editUserProfileTest() {
        String valueId = "426236",
                valueFirstName = "Roma",
                valueLastName = "Win",
                valueEmail = "roma.win@yandex.ru",
                valueCompany = "Amazon",
                valueCountryId = "204",
                valueStateProvinceId = "0",
                valueCity = "Moscow",
                valueAddress1 = "Lenina",
                valueAddress2 = "Mira",
                valueZipPostalCode = "652514",
                valuePhoneNumber = "853902",
                valueFaxNumber = "";

        String  authCookieValue = AuthApi.getAuthCookie(login,password);

        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie(authCookieKey, authCookieValue)
                .formParam("Address.Id", valueId)
                .formParam("Address.FirstName", valueFirstName)
                .formParam("Address.LastName", valueLastName)
                .formParam("Address.Email", valueEmail)
                .formParam("Address.Company", valueCompany)
                .formParam("Address.CountryId", valueCountryId)
                .formParam("Address.StateProvinceId", valueStateProvinceId)
                .formParam("Address.City", valueCity)
                .formParam("Address.Address1", valueAddress1)
                .formParam("Address.Address2", valueAddress2)
                .formParam("Address.ZipPostalCode", valueZipPostalCode)
                .formParam("Address.PhoneNumber", valuePhoneNumber)
                .formParam("Address.FaxNumber", valueFaxNumber)
                .when()
                .post("/customer/addressedit/3121228")
                .then()
                .log().all()
                .assertThat()
                .statusCode(302);

    }
}
