import client.*;
import io.qameta.allure.TmsLink;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;



    public class AuthCourierTest {
        private CourierClient courierClient;
        private ValidatableResponse courierLogin;
        private int courierId;

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

        @After
        public void clearData() {
            courierClient.delete(courierId);
        }

        @Test
        @DisplayName("Авторизация курьера")
        @Description("Провека что можно можно авторизоваться с валидными значениями")
        @TmsLink("ТС6")
        public void CheckAuthCourier() {
            Courier courier = CourierGenerator.getRandom();
            courierClient.create(courier)
                    .assertThat()
                    .statusCode(SC_CREATED);

            courierLogin = courierClient.login(CourierCredentials.from(courier))
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("id", notNullValue());
        }

        @AfterAll
        static void tear(){
            System.out.println("@AfterAll executed");
        }

    }

