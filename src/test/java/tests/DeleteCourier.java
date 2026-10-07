package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class DeleteCourier {

    private CourierClient courierClient;
    private Courier testCourier;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        testCourier = new Courier()
                .setLogin("user_" + System.currentTimeMillis())
                .setPassword("Qaz123")
                .setFirstName("Саша");

        courierClient.create(testCourier).then().statusCode(201);

        CourierCreds creds = new CourierCreds()
                .setLogin(testCourier.getLogin())
                .setPassword(testCourier.getPassword());

        Response loginResponse = courierClient.login(creds);
        loginResponse.then().statusCode(200);

        testCourier.setId(loginResponse.jsonPath().getInt("id"));
    }

    @Test
    @Description("Успешное удаление курьера")
    public void successfulDeleteCourier() {
        courierClient.delete(String.valueOf(testCourier.getId()))
                .then()
                .statusCode(200)
                .body("ok", is(true));

        testCourier = null;
    }

    @Test
    @Description("Удаление без ID возвращает ошибку")
    public void deleteWithoutId() {
        courierClient.deleteWithoutId()
                .then()
                .statusCode(404);
    }

    @Test
    @Description("Удаление несуществующего курьера")
    public void deleteNonExistentCourier() {
        courierClient.delete("999999")
                .then()
                .statusCode(404)
                .body("message", is("Курьера с таким id нет."));
    }

    @AfterEach
    public void tearDown() {
        if (testCourier != null && testCourier.getId() != 0) {
            courierClient.delete(String.valueOf(testCourier.getId()));
        }
    }
}