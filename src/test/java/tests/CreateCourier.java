package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.generators.CourierGenerator;
import edu.praktikum.sprint7.models.Courier;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.given;

public class CreateCourier {

    private CourierClient courierClient;
    private Courier testCourier;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        testCourier = CourierGenerator.randomCourier();
    }

    @Test
    @Description

    public void RegistrationCourier() {

        Response response = given()
                .header("Content-Type", "application/json")
                .body(testCourier)
                .when()
                .post("/api/v1/courier");

        response.then()
                .statusCode(201)
                .body("ok", is(true));


    }

    @Test

    public void RegistrationCourierDuplicate() {

        courierClient.create(testCourier);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(testCourier)
                .when()
                .post("/api/v1/courier");

        response.then()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));


    }

    @Test

    public void UnsuccessfulRegistrationCourier() {

        Courier badCourier = new Courier()
                .setPassword("Qaz123")
                .setFirstName("Саша");


        Response response = given()
                .header("Content-Type", "application/json")
                .body(badCourier)
                .when()
                .post("/api/v1/courier");

        response.then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));


    }

    @AfterEach
    public void tearDown() {
        if (testCourier != null) {
            courierClient.delete(testCourier.getLogin());


        }
    }
}