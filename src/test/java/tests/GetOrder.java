package tests;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
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
        orderResp.then().statusCode(201);

        trackId = orderResp.jsonPath().getInt("track");
    }

    @Test
    public void GetOrderSuccessfully() {
        given()
                .queryParam("t", trackId)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(200)
                .body("order", notNullValue());
    }

    @Test
    public void GetOrderWithoutTrack() {
        given()
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для поиска"));
    }

    @Test
    public void GetOrderWithWrongTrack() {
        given()
                .queryParam("t", 999999999)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(404)
                .body("message", is("Заказ не найден"));
    }
}