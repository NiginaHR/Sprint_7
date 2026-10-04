package edu.praktikum.sprint7.clients;

import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient {

    private static final String API_V1_ORDERS = "/api/v1/orders";

    public OrderClient() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step
    public Response create(Order order) {
        return given()
                .contentType(JSON)
                .and()
                .body(order)
                .when()
                .post(API_V1_ORDERS);
    }

    @Step
    public Response getOrders() {
        return given()
                .contentType(JSON)
                .and()
                .when()
                .get(API_V1_ORDERS);
    }

    @Step
    public Response acceptOrder(int orderId, int courierId) {
        return given()
                .queryParam("courierId", courierId)
                .when()
                .put("/api/v1/orders/accept/" + orderId);
    }
    }
