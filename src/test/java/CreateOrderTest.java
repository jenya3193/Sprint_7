import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrder {
    private String color;

    @Parameterized.Parameters
    public static Object[] data() {

        return new Object[] {"\"GREY\"","\"BLACK\"", "\"GREY\",\"BLACK\"",""};
    }

    public CreateOrder(String color) {
        this.color = color;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Провека что можно успешно создать заказ, с передачей двух цветов, одного и без цвета ")
    @TmsLink("ТС78")
    public void createNewOrder(){
        String json = String.format(
                "{\"firstName\": \"Chigo\",\"lastName\": \"Uriko\",\"address\": \"Random street, 124\",\"metroStation\": \"Centre town\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"7\", \"deliveryDate\": \"2023-04-26\", \"comment\": \"fast pleshure\", \"color\": [%s]}",color);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
                         response.then()
                        .assertThat()
                                 .statusCode(SC_CREATED)
                                 .body("track", notNullValue());
    }
    @AfterAll
    static void tear(){
        System.out.println("@AfterAll executed");
    }
}
