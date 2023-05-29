import client.Courier;
import client.CourierClient;
import client.CourierCredentials;
import io.qameta.allure.TmsLink;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class AuthNonexistentLoginPassTest {

    private CourierClient courierClient;
    private ValidatableResponse courierLogin;


    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Провека что нельзя авторизоваться с несуществующим пользователем")
    @TmsLink("ТС6")
    public void createNewCourierAndCheckResponse() {
        Courier courier = new Courier("Аппавв", "123123123", "");

        courierLogin = courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @AfterAll
    static void tear(){
        System.out.println("@AfterAll executed");
    }
}
