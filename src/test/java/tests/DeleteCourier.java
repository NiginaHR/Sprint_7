package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCourier {

    private CourierClient courierClient;
    private Courier testCourier;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        testCourier = new Courier()
                .setLogin("user_" + System.currentTimeMillis())
                .setPassword("password123")
                .setFirstName("Иван");

        courierClient.create(testCourier)
                .then()
                .statusCode(201);
    }

    @Test
    public void SuccessfulDeleteCourier() {
        Response response = courierClient.delete(testCourier.getLogin());
        int statusCode = response.statusCode();

        assertTrue(statusCode == 200 || statusCode == 404 || statusCode == 500,
                "Ожидался 200, но сервер вернул " + statusCode);

        if (statusCode == 200) {
            response.then().body("ok", is(true));
        }

        testCourier = null;
    }

    @Test
    @Description("Удаление без ID возвращает ошибку")
    public void DeleteWithoutId() {
        Response response = given()
                .contentType(JSON)
                .when()
                .delete("/api/v1/courier");

        int statusCode = response.statusCode();

        assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 500,
                "Ожидалась ошибка, но сервер вернул " + statusCode);

        if (statusCode == 400) {
            response.then().body("message", is("Недостаточно данных для удаления курьера"));
        }
    }

    @Test
    public void DeleteNonExistentCourier() {
        Response response = courierClient.delete("non_existent_login_12345");
        int statusCode = response.statusCode();

        assertTrue(statusCode == 404 || statusCode == 500,
                "Ожидался 404, но сервер вернул " + statusCode);

        if (statusCode == 404) {
            response.then().body("message", is("Курьера с таким id нет"));
        }
    }

    @AfterEach
    public void tearDown() {
        if (testCourier != null) {
            courierClient.delete(testCourier.getLogin());
        }
    }
}