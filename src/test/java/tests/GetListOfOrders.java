package tests;

import edu.praktikum.sprint7.clients.OrderClient;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetListOfOrders {

    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();


    }

    @Test
    @Description("Проверка получения списка заказов")
    public void shouldGetOrdersList() {
        orderClient.getOrders()
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}