import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import orders.Orders;
import orders.OrdersClient;
import orders.RandomOrders;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;



import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final Orders orders;

    OrdersClient orderClient = new OrdersClient();

    public CreateOrderTest(Orders orders) {
        this.orders = orders;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDataForColor() {
        return new Object[][]{
                {new RandomOrders(new String[]{"BLACK"})},
                {new RandomOrders(new String[]{"GREY"})},
                {new RandomOrders(new String[]{"BLACK", "GREY"})},
                {new RandomOrders(null)}, // передали тестовые данные
        };
    }

    @Test
    @DisplayName("Проверяем код и тело ответа в случае валидного запроса")
    @Description("Ожидаемый результат: код 201, заказ создан, тело запроса содержит track")
    @TmsLink("C5")

    public void checkCreateValidOrder() {
        orderClient.createOrder(orders)
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }

}
