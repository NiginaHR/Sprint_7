package edu.praktikum.sprint7.clients;

import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public Response create(Courier courier) {
        return given()
                .contentType(JSON)
                .body(courier)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    @Step("Авторизация курьера")
    public Response login(CourierCreds creds) {
        return given()
                .contentType(JSON)
                .body(creds)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    @Step("Удаление курьера по id")
    public Response delete(String id) {
        return given()
                .contentType(JSON)
                .body("{\"id\": \"" + id + "\"}")
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + id);
    }

    @Step("Удаление курьера без ID")
    public Response deleteWithoutId() {
        return given()
                .contentType(JSON)
                .when()
                .delete(BASE_URL + "/api/v1/courier");
    }
}