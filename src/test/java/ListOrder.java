import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ListOrder {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка без передачи параметров")
    @TmsLink("ТС78")
    public void CheckOrderListWithoutParameters(){

                given()
                        .header("Content-type", "application/json")
                        .get("/api/v1/orders")
                        .then()
                        .assertThat()
                        .statusCode(SC_OK)
                        .body(notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка ошибки на несуществующий ID")
    @TmsLink("ТС78")
    public void CheckOrderWithError(){


        given()
                .header("Content-type", "application/json")
                .queryParam("courierId", "0")
                .get("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьер с идентификатором 0 не найден"));

    }


}
