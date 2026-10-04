package edu.praktikum.sprint7.clients;

import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CourierClient {

    private static final String API_V1_COURIER = "/api/v1/courier";
    private static final String API_V1_COURIER_LOGIN = "/api/v1/courier/login";

    public CourierClient() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Создание курьера")
    public Response create(Courier courier) {
        return given()
                .contentType(JSON)
                .body(courier)
                .when()
                .post(API_V1_COURIER);
    }

    @Step("Авторизация курьера")
    public Response login(CourierCreds creds) {
        return given()
                .contentType(JSON)
                .body(creds)
                .when()
                .post(API_V1_COURIER_LOGIN);
    }

    @Step("Удаление курьера по id")
    public Response delete(String id) {
        return given()
                .contentType(JSON)
                .when()
                .delete(API_V1_COURIER + "/" + id);
    }
}