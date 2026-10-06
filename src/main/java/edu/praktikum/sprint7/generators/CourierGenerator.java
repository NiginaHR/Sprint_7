package edu.praktikum.sprint7.generators;

import edu.praktikum.sprint7.models.Courier;
import net.datafaker.Faker;

public class CourierGenerator {

    private static final Faker faker = new Faker();

    public static Courier randomCourier() {
        return new Courier()
                .setLogin(faker.name().username())
                .setPassword(faker.internet().password())
                .setFirstName(faker.name().firstName());
    }
}