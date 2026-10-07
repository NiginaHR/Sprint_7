package tests;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrder {

    private OrderClient orderClient;
    private int trackId;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
        Response orderResp = orderClient.create(new Order(
                "Лиззи", "+79003200000", "Дербишир, Пемберли", List.of("Black"), "Для миссис Дарси"));

        trackId = orderResp.jsonPath().getInt("track");
    }

    @Test
    @Description("Успешное получение заказа по треку")
    public void getOrderSuccessfully() {
        orderClient.getOrderByTrack(trackId)
                .then()
                .statusCode(200)
                .body("order", notNullValue());
    }

    @Test
    @Description("Получение заказа без трека возвращает ошибку")
    public void getOrderWithoutTrack() {
        orderClient.getOrderByTrackWithoutParam()
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для поиска"));
    }

    @Test
    @Description("Получение заказа с несуществующим треком")
    public void getOrderWithWrongTrack() {
        orderClient.getOrderByTrack(999999999)
                .then()
                .statusCode(404)
                .body("message", is("Заказ не найден"));
    }
}