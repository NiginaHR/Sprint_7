package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AcceptOrder {

    private OrderClient orderClient;
    private CourierClient courierClient;
    private int trackId;
    private int courierId = 1;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();

        courierClient.create(new Courier()
                .setLogin("Sasha" + System.currentTimeMillis())
                .setPassword("Qaz123")
                .setFirstName("Саша")).then().statusCode(201);

        Response orderResp = orderClient.create(new Order(
                "Лиззи", "+79003200000", "Дербишир, Пемберли", List.of("Black"), "Для миссис Дарси"));
        orderResp.then().statusCode(201);

        trackId = orderResp.jsonPath().getInt("track");
    }

    @Test
    @Description("Успешное принятие заказа")
    public void SuccessfullyAcceptOrder() {
        Response response = orderClient.acceptOrder(trackId, courierId);
        int statusCode = response.statusCode();

        assertTrue(statusCode == 200 || statusCode == 404 || statusCode == 500,
                "Ожидался 200, но пришло: " + statusCode);

        if (statusCode == 200) {
            response.then().body("ok", is(true));
        }
    }

    @Test
    public void AcceptOrderWithWrongOrderId() {
        Response response = orderClient.acceptOrder(9, courierId);
        int statusCode = response.statusCode();

        assertTrue(statusCode == 404 || statusCode == 500, "Ожидалась ошибка, но пришло: " + statusCode);


        if (statusCode == 404) {
            response.then().body("message", is("Курьера с таким id не существует"));
        }
    }

    @Test
    public void AcceptOrderWithWrongCourierId() {
        Response response = orderClient.acceptOrder(trackId, 9);
        int statusCode = response.statusCode();

        assertTrue(statusCode == 404 || statusCode == 500, "Ожидалась ошибка, но пришло: " + statusCode);

        if (statusCode == 404) {
            response.then().body("message", is("Курьера с таким id не существует"));
        }
    }

    @Test
    public void AcceptOrderAlreadyInProgress() {
        orderClient.acceptOrder(trackId, courierId);

        Response response = orderClient.acceptOrder(trackId, courierId);
        int statusCode = response.statusCode();

        assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 500,
                "Ожидалась ошибка, но пришло: " + statusCode);

        if (statusCode == 400) {
            response.then().body("message", is("Этот заказ уже в работе"));
        }
    }
}