import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasSize;

public class LimitOrder {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения 10 заказов")
    @TmsLink("ТС78")
    public void LimitOrders() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .queryParam("limit", 10)
                        .queryParam("page", 0)
                        .get("/api/v1/orders");
                         response.then()
                        .assertThat()
                        .statusCode(SC_OK);
                         response.then().assertThat()
                        .body("orders", hasSize(10));

    }
}
