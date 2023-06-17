import client.*;
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
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;



public class AuthWithoutLoginTest {

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
    @Description("Провека авторизации без логина")
    @TmsLink("ТС6")
    public void createNewCourierAndCheckAuto(){
        Courier courier = new Courier("", "123123123", "dfgd");

        courierLogin = courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @AfterAll
    static void tear(){
        System.out.println("@AfterAll executed");
    }
}