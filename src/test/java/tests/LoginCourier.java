package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class LoginCourier {

    private CourierClient courierClient;
    private Courier testCourier;
    private CourierCreds courierCreds;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();

        testCourier = new Courier()
                .setLogin("user_" + System.currentTimeMillis())
                .setPassword("Qaz123")
                .setFirstName("Саша");

        courierClient.create(testCourier).then().statusCode(201);
        courierCreds = new CourierCreds()
                .setLogin(testCourier.getLogin())
                .setPassword(testCourier.getPassword());
        Response loginResponse = courierClient.login(courierCreds);
        testCourier.setId(loginResponse.jsonPath().getInt("id"));
    }

    @Test
    @Description("Успешная авторизация курьера")
    public void authorization() {
        Response response = courierClient.login(courierCreds);
        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @AfterEach
    public void tearDown() {
        if (testCourier != null && testCourier.getId() != 0) {
            courierClient.delete(String.valueOf(testCourier.getId()));
        }
    }
}