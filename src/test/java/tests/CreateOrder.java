package tests;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrder {

    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    @Description("Проверка создания заказа")
    public void testCreateOrder(String firstName, String phone, String address, List<String> color, String comment) {

        Order testOrder = new Order(firstName, phone, address, color, comment);

        orderClient.create(testOrder)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    static Stream<Arguments> orderDataProvider() {
        return Stream.of(
                Arguments.of("Лиззи", "+79003200000", "Дербишир, Пемберли", List.of("Black"), "Для миссис Дарси"),
                Arguments.of("Джейн", "+79003200000", "Ноттингем, Незерфилд", List.of("Grey"), "Для миссис Бингли"),
                Arguments.of("Китти", "+79003203300", "Хардфотшир, Лонгборн", List.of("Grey", "Black"), "Для мисс Беннет"),
                Arguments.of("Мэри", "+79003204400", "Лондон", null, "Без комментариев")
        );

}}