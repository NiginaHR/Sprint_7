package tests;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.generators.CourierGenerator;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class CreateCourier {

    private CourierClient courierClient;
    private Courier testCourier;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        testCourier = CourierGenerator.randomCourier();

        courierClient.create(testCourier).then().statusCode(201);

        CourierCreds creds = new CourierCreds()
                .setLogin(testCourier.getLogin())
                .setPassword(testCourier.getPassword());

        Response loginResponse = courierClient.login(creds);
        testCourier.setId(loginResponse.jsonPath().getInt("id"));
    }

    @Test
    @Description("Успешная регистрация курьера")
    public void registrationCourier() {
        Courier newCourier = CourierGenerator.randomCourier();
        courierClient.create(newCourier).then().statusCode(201);

        Response loginResp = courierClient.login(new CourierCreds()
                .setLogin(newCourier.getLogin())
                .setPassword(newCourier.getPassword()));
        newCourier.setId(loginResp.jsonPath().getInt("id"));
        testCourier = newCourier;
    }

    @Test
    @Description("Регистрация курьера с уже занятым логином")
    public void registrationCourierDuplicate() {
        Response response = courierClient.create(testCourier);

        response.then()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Регистрация курьера без обязательных полей")
    public void unsuccessfulRegistrationCourier() {
        Courier badCourier = new Courier()
                .setPassword("Qaz123")
                .setFirstName("Саша");

        courierClient.create(badCourier).then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @AfterEach
    public void tearDown() {
        if (testCourier != null && testCourier.getId() != 0) {
            courierClient.delete(String.valueOf(testCourier.getId()));
        }
    }
}