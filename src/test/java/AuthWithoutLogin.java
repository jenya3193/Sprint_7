import client.RandomFirstNameCourier;
import client.RandomLoginCourier;
import client.RandomPasswordCourier;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class CheckAuthWithoutLogin {
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
    @DisplayName("Авторизация курьера")
    @Description("Провека авторизации без логина")
    @TmsLink("ТС6")
    public void createNewCourierAndCheckAuto(){
        String json = String.format(
                "{\"login\": \"\", \"password\": \"\",  \"firstName\": \"%s\"}",login, password, firstName);


        Response response =
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));



    }
}