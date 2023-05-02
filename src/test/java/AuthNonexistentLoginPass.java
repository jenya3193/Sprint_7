import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;

public class CheckAuthNonexistentLoginPass {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Провека что можно можно авторизоваться с несуществующим пользователем")
    @TmsLink("ТС6")
    public void createNewCourierAndCheckResponse(){
        String json = "{\"login\": \"new4664ester\", \"password\": \"7825119\"}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
                         response.then().assertThat()
                        .statusCode(SC_NOT_FOUND)
                        .body("message", equalTo("Учетная запись не найдена"));
    }
}
