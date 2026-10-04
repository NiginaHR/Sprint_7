package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
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
                .setPassword("password123")
                .setFirstName("Иван");

        courierClient.create(testCourier);

        courierCreds = CourierCreds.credsFromCourier(testCourier);
    }
    @Test
    @Description

    public void Authorization() {

        Response response = given()
                .header("Content-Type", "application/json")
                .body(courierCreds)
                .when()
                .post("/api/v1/courier/login");

        response.then()
                .statusCode(200)
                .body("id",notNullValue());


    }
    @AfterEach
    public void tearDown() {
        if (testCourier != null) {
            courierClient.delete(testCourier.getLogin());
        }
    }}