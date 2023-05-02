import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CheckCreateCourierBadAnswer {


    @Before
    public void setUp() {


        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Создание курьера")
    @Description("Провека что можно создать курьера без логина")
    @TmsLink("ТС7")
    public void CheckCreateCourierWithoutLogin() {
        String json = "{\"password\": \"782119\", \"firstName\": \"Александр\"}";
        Response response =
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).log().all()
                .and()
                .statusCode(400);
    }


    @Test
    @DisplayName("Авторизация курьера")
    @Description("Провека что можно создать курьера без пароля")
    @TmsLink("ТС8")
    public void CheckCreateCourierWithoutPassword() {
        String json = "{\"login\": \"new777t7ester\", \"firstName\": \"Александр\"}";
        Response response =
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).log().all()
                .and()
                .statusCode(400);
    }
}






