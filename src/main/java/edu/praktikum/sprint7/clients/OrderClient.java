package edu.praktikum.sprint7.clients;

import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .contentType(JSON)
                .body(order)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .when()
                .get(BASE_URL + "/api/v1/orders");
    }

    @Step("Получение заказа по треку")
    public Response getOrderByTrack(int track) {
        return given()
                .queryParam("t", track)
                .when()
                .get(BASE_URL + "/api/v1/orders/track");
    }

    @Step("Получение заказа без параметра")
    public Response getOrderByTrackWithoutParam() {
        return given()
                .when()
                .get(BASE_URL + "/api/v1/orders/track");
    }
}