import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
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
    @Description("Провека что можно создать курьера с валидными значениями и одинаковым логином")
    @TmsLink("ТС8")

    public void checkCreate() {
        String json = String.format(
                "{\"login\": \"%s\", \"password\": \"%s\",  \"firstName\": \"%s\"}",login, password, firstName
        );
        Response response =
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", is(true)).log().all()
                .and()
                .statusCode(201);




        Response responseWithError =
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        responseWithError.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).log().all()
                .statusCode(409);



    }
    @AfterAll
    static void tear() {
        System.out.println("@AfterAll executed");
    }
}


