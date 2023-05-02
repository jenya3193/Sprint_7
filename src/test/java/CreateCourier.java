

import client.RandomFirstNameCourier;
import client.RandomLoginCourier;
import client.RandomPasswordCourier;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.DisplayName;


public class CheckCreateCourier {
    String login;
    String password;
    String firstName;



    @Before
    public void setUp() {
        this.login = RandomLoginCourier.generateLogin();
        this.password = RandomPasswordCourier.generatePassword();
        this.firstName = RandomFirstNameCourier.generateFirstName();
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Провека что можно создать курьера с валидными значениями и нелья создать одинаковым логином")
    @TmsLink("ТС8")

    public void checkCreate() {
        String json = String.format(
                "{\"login\": \"%s\", \"password\": \"%s\",  \"firstName\": \"%s\"}",login, password, firstName
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
                         response.then().assertThat()
                                 .statusCode(SC_CREATED)
                         .body("ok", is(true));




        Response responseWithError =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
                         responseWithError.then()
                        .assertThat()
                        .statusCode(SC_CONFLICT)
                        .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));



    }

}


